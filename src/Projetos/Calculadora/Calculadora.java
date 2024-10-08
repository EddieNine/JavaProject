package Projetos.Calculadora;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// Enum para operações matemáticas
enum Operation {
    ADD('+'), SUBTRACT('-'), MULTIPLY('x'), DIVIDE('÷');

    private final char symbol;

    Operation(char symbol) {
        this.symbol = symbol;
    }

    public char getSymbol() {
        return symbol;
    }

    public static Operation valueOfSymbol(char symbol) {
        for (Operation operation : Operation.values()) {
            if (operation.getSymbol() == symbol) {
                return operation;
            }
        }
        throw new IllegalArgumentException("Operação inválida: " + symbol);
    }
}

public class Calculadora extends JFrame implements ActionListener {

    private JTextField textField;
    private boolean isDarkMode = true; // Para armazenar o estado atual do tema
    private JPanel panel;
    private Engine engine = new Engine(); // Motor de cálculo

    public Calculadora() {
        // Icone do panel
        ImageIcon icon = new ImageIcon("src/Projetos/Calculadora/calculator.png");
        setIconImage(icon.getImage());

        setTitle("Calculadora");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Alternador de tema (botão)
        JToggleButton themeToggle = new JToggleButton("Modo Escuro");
        themeToggle.setSelected(true); // Começa no modo escuro
        themeToggle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isDarkMode = !isDarkMode;
                updateTheme();
                themeToggle.setText(isDarkMode ? "Modo Escuro" : "Modo Claro");
            }
        });
        add(themeToggle, BorderLayout.SOUTH);

        // Painel para a tela da calculadora
        textField = new JTextField();
        textField.setFont(new Font("Arial", Font.BOLD, 50));
        textField.setEditable(false);
        textField.setHorizontalAlignment(SwingConstants.RIGHT);
        add(textField, BorderLayout.NORTH);

        // Painel para os botões
        panel = new JPanel();
        panel.setLayout(new GridLayout(5, 4, 10, 10));

        String[] buttonLabels = {
                "C", "←", "%", "÷",
                "7", "8", "9", "x",
                "4", "5", "6", "-",
                "1", "2", "3", "+",
                "0", ".", "", "="
        };

        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.setFont(new Font("Arial", Font.BOLD, 24));
            button.setFocusPainted(false);
            button.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            final Color[] originalColor = {button.getBackground()};

            // Configurando o efeito de hover de forma simplificada
            button.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent evt) {
                    originalColor[0] = button.getBackground(); // Salva a cor original
                    button.setBackground(originalColor[0].darker()); // Escurece a cor ao passar o mouse
                }

                @Override
                public void mouseExited(MouseEvent evt) {
                    button.setBackground(originalColor[0]); // Restaura a cor original ao sair o mouse
                }
            });

            // Adicionando animação ao clique do botão
            button.addActionListener(e -> {
                Color clickColor = new Color(100, 100, 100);
                button.setBackground(clickColor);
                Timer clickTimer = new Timer(150, new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        button.setBackground(originalColor[0]);
                    }
                });
                clickTimer.setRepeats(false);
                clickTimer.start();
            });

            button.addActionListener(this);
            panel.add(button);
        }

        add(panel, BorderLayout.CENTER);

        // Aplicar o tema inicial
        updateTheme();
    }

    private void updateTheme() {
        if (isDarkMode) {
            getContentPane().setBackground(Color.DARK_GRAY);
            textField.setBackground(Color.LIGHT_GRAY);
            textField.setForeground(Color.BLACK);
            panel.setBackground(Color.LIGHT_GRAY);
            for (Component component : panel.getComponents()) {
                if (component instanceof JButton button) {
                    button.setBackground(new Color(45, 45, 45));
                    button.setForeground(Color.WHITE);
                }
            }
        } else {
            getContentPane().setBackground(Color.WHITE);
            textField.setBackground(Color.WHITE);
            textField.setForeground(Color.BLACK);
            panel.setBackground(Color.WHITE);
            for (Component component : panel.getComponents()) {
                if (component instanceof JButton button) {
                    button.setBackground(new Color(220, 220, 220));
                    button.setForeground(Color.BLACK);
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.equals("C")) {
            engine.clear();
            textField.setText("");
        } else if (command.equals("←")) {
            engine.backspace(textField);
        } else if (command.equals("%")) {
            engine.percentage(textField);
        } else if (command.equals("=")) {
            engine.calculate(textField);
        } else {
            handleNumberOrOperator(command);
        }
    }

    private void handleNumberOrOperator(String command) {
        if (command.charAt(0) >= '0' && command.charAt(0) <= '9' || command.equals(".")) {
            textField.setText(textField.getText() + command);
        } else {
            if (!textField.getText().isEmpty()) {
                engine.setNum1(Double.parseDouble(textField.getText()));
                engine.setOperator(Operation.valueOfSymbol(command.charAt(0)));
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

class Engine {
    private double num1, num2;
    private Operation operator;

    public void setNum1(double num1) {
        this.num1 = num1;
    }

    public void setOperator(Operation operator) {
        this.operator = operator;
    }

    public void calculate(JTextField textField) {
        num2 = Double.parseDouble(textField.getText());

        double result = switch (operator) {
            case ADD -> num1 + num2;
            case SUBTRACT -> num1 - num2;
            case MULTIPLY -> num1 * num2;
            case DIVIDE -> {
                if (num2 == 0) {
                    JOptionPane.showMessageDialog(null, "Não é possível dividir por 0", "Erro", JOptionPane.ERROR_MESSAGE);
                    yield 0;
                } else {
                    yield num1 / num2;
                }
            }
        };

        textField.setText(String.valueOf(result));
        num1 = result;
    }

    public void clear() {
        num1 = num2 = 0;
        operator = null;
    }

    public void backspace(JTextField textField) {
        String currentText = textField.getText();
        if (!currentText.isEmpty()) {
            textField.setText(currentText.substring(0, currentText.length() - 1));
        }
    }

    public void percentage(JTextField textField) {
        if (!textField.getText().isEmpty()) {
            num2 = Double.parseDouble(textField.getText());
            num2 = num1 * num2 / 100;
            textField.setText(String.valueOf(num2));
        }
    }
}
