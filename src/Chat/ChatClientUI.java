package Chat;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClientUI extends Application{
    private BufferedReader in;
    private PrintWriter out;
    private ListView<String> messages;
    private TextField inputField;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Configuração da interface gráfica
        messages = new ListView<>();
        inputField = new TextField();
        inputField.setPromptText("Digite sua mensagem aqui...");

        Button sendButton = new Button("Enviar");
        sendButton.setOnAction(event -> sendMessage());

        HBox inputBox = new HBox(10, inputField, sendButton);
        inputBox.setPadding(new Insets(10));

        BorderPane root = new BorderPane();
        root.setCenter(messages);
        root.setBottom(inputBox);

        Scene scene = new Scene(root, 400, 300);
        primaryStage.setTitle("Chat Cliente");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Conectar ao servidor
        new Thread(this::connectToServer).start();
    }

    private void connectToServer() {
        try (Socket socket = new Socket("localhost", 12345)) {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            String message;
            while ((message = in.readLine()) != null) {
                messages.getItems().add("Servidor: " + message);
            }
        } catch (Exception e) {
            messages.getItems().add("Erro: " + e.getMessage());
        }
    }

    private void sendMessage() {
        String message = inputField.getText();
        if (!message.isEmpty()) {
            out.println(message);
            messages.getItems().add("Você: " + message);
            inputField.clear();
        }
    }
}
