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
    AircraftSymbol aircraftSymbol;
    Timer timer;
    static final int SAMPLE_FREQUENCY = 40, SIMULATION_TIME = 9;
    double ft, theta;
    int count = 0;
    double[] input, error;
    TextField textField;
    private final int DisplayType = 0; // (0 = Compensatory, 1 = Pursuit, 2 = Preview)

    public static void main (String[] arg) {
        Simulator simulator = new Simulator();
        JFrame frame = new JFrame("Pitch tracking task");
        frame.setSize(380, 500);
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
        aircraftSymbol = new AircraftSymbol();

        frame.getContentPane().add(BorderLayout.NORTH, textField);
        forcingFunction = new ForcingFunction(SIMULATION_TIME, SAMPLE_FREQUENCY);
        input = forcingFunction.makefunction();
        error = new double[input.length];

        baseline = new BaselineCalculation(forcingFunction.sampleFrequency);

        timer = new Timer(1000 / SAMPLE_FREQUENCY, run);
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

                error[count] = ft - theta;
                indicators.repaint();
                count++;
            } else {
                Statistics.data = error;
                Statistics.size = error.length;
                System.out.println("Your score is: " + Statistics.getVariance());
                timer.stop();
            }
        }

    };

    class Indicators extends JPanel {
        @Override
        public void paintComponent(Graphics graphics){
            super.paintComponent(graphics);
            Graphics2D graphics2d = (Graphics2D) graphics;
            graphics2d.setColor(Color.black);
            if (DisplayType == 0) {
                // Draw the forcing function
                graphics2d.drawLine(20, (int) (200 - 50 * (ft - theta)), 280, (int) (200 - 50 * (ft - theta)));

                //Draw the aircraft Symbol
                aircraftSymbol.makeSymbol(graphics2d, 0);
            } else if (DisplayType == 1) {
                // Draw the forcing function
                graphics2d.drawLine(20, (int) (200 - 50 * ft), 280, (int) (200 - 50 * ft));

                //Draw the aircraft Symbol
                aircraftSymbol.makeSymbol(graphics2d, 50 * theta);
            }
        }
    }

}
