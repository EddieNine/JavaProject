package Chat;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
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
        // Criando componentes
        Label label = new Label("Mensagem:");
        TextField textField = new TextField();
        Button button = new Button("Enviar");
        TextArea chatArea = new TextArea();

        // Configurando o layout
        VBox root = new VBox(10, chatArea, label, textField, button);
        root.setPadding(new Insets(20));

        // Criando uma cena e aplicando o CSS
        Scene scene = new Scene(root, 400, 300);
        scene.getStylesheets().add(getClass().getResource("/chat/css/styles.css").toExternalForm());

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
