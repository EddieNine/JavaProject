package Projetos.Calculadora;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculadora extends JFrame implements ActionListener {

    private  JTextField textField;
    private double num1, num2, result;
    private char operator;

    public Calculadora() {
        setTitle("Calculadora");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Configuração da cor de fundo
        getContentPane().setBackground(Color.DARK_GRAY);

        // Painel para a tela da calculadora
        textField = new JTextField();
        textField.setFont(new Font("Arial", Font.BOLD, 50));
        textField.setEditable(false);
        textField.setHorizontalAlignment(SwingConstants.RIGHT);
        textField.setBackground(Color.LIGHT_GRAY);
        textField.setForeground(Color.BLACK);
        add(textField, BorderLayout.NORTH);

        // Painel para os botôes
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 4, 10, 10));
        panel.setBackground(Color.LIGHT_GRAY);

        String[] buttonLabels = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", ".", "=", "+"
        };

        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.setFont(new Font("Arial", Font.BOLD, 24));
            button.setForeground(Color.WHITE); // Cor do texto
            button.setBackground(new Color(45, 45, 45)); // Cor de fundo dos botões
            button.setFocusPainted(false);
            button.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Borda dos botões

            final Color originalColor = button.getBackground();

            // Adicionando animação ao hover (passar o mouse sobre o botão)
            button.addMouseListener(new java.awt.event.MouseAdapter() {
                Timer hoverTimer;

                @Override
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    hoverTimer = new Timer(10, new ActionListener() {
                        float[] hsbVals = Color.RGBtoHSB(originalColor.getRed(), originalColor.getGreen(), originalColor.getBlue(), null);
                        float brightness = hsbVals[2];
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            brightness += 0.05;
                            if (brightness > 1.0) brightness = 1.0f;
                            button.setBackground(Color.getHSBColor(hsbVals[0], hsbVals[1], brightness));
                            if (brightness == 1.0f) hoverTimer.stop();
                        }
                    });
                    hoverTimer.start();
                }

                @Override
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    if (hoverTimer != null) hoverTimer.stop();
                    button.setBackground(originalColor);
                }
            });

            // Adicionando animação ao clique do botão
            button.addActionListener(e -> {
                // playClickSound(); // Toca o som de clique
                Color clickColor = new Color(100, 100, 100);
                button.setBackground(clickColor);
                Timer clickTimer = new Timer(150, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        button.setBackground(originalColor);
                    }
                });
                clickTimer.setRepeats(false);
                clickTimer.start();
            });

            button.addActionListener(this);
            panel.add(button);
        }

        add(panel, BorderLayout.CENTER);
    }

    /* Método para tocar o som de clique
    private void playClickSound() {
        try {
            File soundFile = new File("src/Projetos/Calculadora/mouseclick.wav");
            InputStream audioSrc = getClass().getResourceAsStream("/Projetos/Calculadora/mouseclick.wav");
            InputStream bufferedIn = new BufferedInputStream(audioSrc);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(bufferedIn);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    } */

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.charAt(0) >= '0' && command.charAt(0) <= '9' || command.equals(".")) {
            textField.setText(textField.getText() + command);
        } else if (command.charAt(0) == '=') {
            num2 = Double.parseDouble(textField.getText());

            switch (operator) {
                case '+':
                    result = num1 + num2;
                    break;
                case '-':
                    result = num1 - num2;
                    break;
                case '*':
                    result = num1 * num2;
                    break;
                case '/':
                    if (num2 == 0) {
                        JOptionPane.showMessageDialog(this, "Não é possível dividir por 0", "Erro", JOptionPane.ERROR_MESSAGE);
                        return;
                    } else {
                        result = num1 / num2;
                    }
                    break;
            }

            textField.setText(String.valueOf(result));
            num1 = result;
        } else {
            if (!textField.getText().isEmpty()) {
                num1 = Double.parseDouble(textField.getText());
                operator = command.charAt(0);
                textField.setText("");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Calculadora calc = new Calculadora();
            calc.setVisible(true);
        });
    }
}
