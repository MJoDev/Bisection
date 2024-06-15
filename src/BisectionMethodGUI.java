import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.mariuszgromada.math.mxparser.*;

public class BisectionMethodGUI {
    private JFrame frame;
    private JTextField functionField;
    private JTextField lowerBoundField;
    private JTextField upperBoundField;
    private JTextField iterationsField;
    private JTextArea resultArea;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BisectionMethodGUI().createAndShowGUI());
    }

    private void createAndShowGUI() {
        frame = new JFrame("Método de Bisección");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 350);
        frame.setLayout(null);

        JLabel functionLabel = new JLabel("Función:");
        functionLabel.setBounds(10, 10, 80, 25);
        frame.add(functionLabel);

        functionField = new JTextField();
        functionField.setBounds(100, 10, 280, 25);
        frame.add(functionField);

        JLabel lowerBoundLabel = new JLabel("Límite inferior:");
        lowerBoundLabel.setBounds(10, 45, 100, 25);
        frame.add(lowerBoundLabel);

        lowerBoundField = new JTextField();
        lowerBoundField.setBounds(120, 45, 80, 25);
        frame.add(lowerBoundField);

        JLabel upperBoundLabel = new JLabel("Límite superior:");
        upperBoundLabel.setBounds(10, 80, 100, 25);
        frame.add(upperBoundLabel);

        upperBoundField = new JTextField();
        upperBoundField.setBounds(120, 80, 80, 25);
        frame.add(upperBoundField);

        JLabel iterationsLabel = new JLabel("Iteraciones:");
        iterationsLabel.setBounds(10, 115, 100, 25);
        frame.add(iterationsLabel);

        iterationsField = new JTextField();
        iterationsField.setBounds(120, 115, 80, 25);
        frame.add(iterationsField);

        JButton calculateButton = new JButton("Calcular");
        calculateButton.setBounds(10, 150, 150, 25);
        frame.add(calculateButton);

        resultArea = new JTextArea();
        resultArea.setBounds(10, 185, 370, 100);
        resultArea.setEditable(false);
        frame.add(resultArea);

        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateRoot();
            }
        });

        frame.setVisible(true);
    }

    private void calculateRoot() {
        String functionExpr = functionField.getText();
        double lowerBound = Double.parseDouble(lowerBoundField.getText());
        double upperBound = Double.parseDouble(upperBoundField.getText());
        int maxIterations = Integer.parseInt(iterationsField.getText());

        double root = bisectionMethod(functionExpr, lowerBound, upperBound, 0.0001, maxIterations);
        resultArea.setText("La raíz aproximada es: " + root);
    }

    private double bisectionMethod(String functionExpr, double lower, double upper, double tol, int maxIter) {
        Function f = new Function("f(x) = " + functionExpr);
        double fl = f.calculate(lower);
        double fu = f.calculate(upper);

        if (fl * fu >= 0) {
            throw new IllegalArgumentException("La función debe cambiar de signo en el intervalo dado.");
        }

        double root = lower;
        for (int i = 0; i < maxIter; i++) {
            root = (lower + upper) / 2;
            double fr = f.calculate(root);

            if (Math.abs(fr) < tol) {
                break;
            }

            if (fl * fr < 0) {
                upper = root;
                fu = fr;
            } else {
                lower = root;
                fl = fr;
            }
        }
        return root;
    }
}

