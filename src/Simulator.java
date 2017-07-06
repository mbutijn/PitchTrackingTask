import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by martin on 2-7-2017.
 */
public class Simulator {

    Indicators indicators;
    ForcingFunction forcingFunction;
    ControlSignal controlSignal;
    BaselineCalculation baseline;
    static final int FREQUENCY = 40;
    double ft, theta;
    int count = 0;
    double[] input;
    TextField textField;

    public static void main (String[] arg) {
        Simulator simulator = new Simulator();
        JFrame frame = new JFrame("Pitch tracking task");
        frame.setSize(400, 500);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        simulator.make(frame);

    }

    private void make(JFrame frame){
        indicators = new Indicators();
        frame.getContentPane().add(BorderLayout.CENTER, indicators);

        textField = new TextField();
        textField.setEditable(false);

        controlSignal = new ControlSignal();

        frame.getContentPane().add(BorderLayout.NORTH, textField);
        forcingFunction = new ForcingFunction(9, FREQUENCY);
        input = forcingFunction.makefunction();

        baseline = new BaselineCalculation(forcingFunction.sampleFrequency);

        Timer timer = new Timer(1000/FREQUENCY, run);
        timer.setRepeats(true);
        timer.start();
    }

    ActionListener run = new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            if (count < forcingFunction.numberSamples) {
                ft = input[count];
                textField.setText("time = " + String.format("%.2f", ((double) (count)/ (double) (forcingFunction.sampleFrequency))) + " [s]");

                baseline.performCalculation(controlSignal.getControlSignal());
                theta = baseline.y;

                indicators.repaint();
            }
            count++;
        }
    };

    class Indicators extends JPanel {
        @Override
        public void paintComponent(Graphics graphics){
            super.paintComponent(graphics);
            Graphics2D graphics2d = (Graphics2D) graphics;
            graphics2d.setColor(Color.black);
            graphics2d.drawLine(40, (int)(200 - 50 * ft), 350, (int)(200 - 50 * ft));

            graphics2d.setColor(Color.red);
            graphics2d.drawLine(40, (int) (200 - theta), 350, (int) (200 - theta));
        }
    }
}
