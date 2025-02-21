import javax.swing.*;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calcii {
    private JTextField textField;
    Calcii() {
        JFrame frame = new JFrame("Calculator");
        frame.setSize(400, 500); 
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        textField = new JTextField();//text field
        textField.setHorizontalAlignment(JTextField.RIGHT); 
        textField.setFont(textField.getFont().deriveFont(30f)); 
        textField.setEditable(false); 
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 4, 2, 2));
        String[] buttonLabels = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", "C", "=", "+"
        };
        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.setFont(button.getFont().deriveFont(20f));
            button.addActionListener(new ButtonClickListener()); 
            panel.add(button);
        }
        frame.setLayout(new BorderLayout());
        frame.add(textField, BorderLayout.NORTH);
        frame.add(panel, BorderLayout.CENTER);
        
        frame.setVisible(true);
    }

    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String key = e.getActionCommand();

            if (key.equals("C")) {
                textField.setText("");
            }
            else if (key.equals("=")) {
                try {
                   String result = evaluateExpression(textField.getText());
                    textField.setText(result);
                } catch (Exception ex) {//when two operators are together
                    textField.setText("Error");
                }
            } 
            else {
                textField.setText(textField.getText() + key);
            }
        }
    }

    private String evaluateExpression(String expression) {
        try {
            expression = expression.replaceAll(" ", "");
            double result = 0;
            char operator = '+';
            StringBuilder numBuffer = new StringBuilder();
    
            for (int i = 0; i < expression.length(); i++) {
                char c = expression.charAt(i);
                if (Character.isDigit(c) || c == '.') {
                    numBuffer.append(c);
                }
                if (i == expression.length() - 1 || !Character.isDigit(c) && c != '.') {
                    double number = Double.parseDouble(numBuffer.toString());
    
                    switch (operator) {
                        case '+':
                            result += number;
                            break;
                        case '-':
                            result -= number;
                            break;
                        case '*':
                            result *= number;
                            break;
                        case '/':
                            if (number == 0) {
                                return "Error: Division by zero";
                            }
                            result /= number;
                            break;
                    }
                    operator = c;
                    numBuffer.setLength(0);
                }
            }
    
            return String.valueOf(result);
        } catch (Exception e) {
            return "Error";
        }
    }


    public static void main(String[] args) {
        new Calcii();
    }
}