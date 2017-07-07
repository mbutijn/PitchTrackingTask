import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by martin on 2-7-2017.
 */
public class Simulator {

    private Indicators indicators;
    private ForcingFunction forcingFunction;
    private ControlSignal controlSignal;
    private AircraftSymbol aircraftSymbol;
    private Timer timer;
    protected BaselineCalculation baseline;

    private static final int SAMPLE_FREQUENCY = 40, SIMULATION_TIME = 9;
    private double[] input, disturbance, error;
    private TextField textField;
    private double ft, theta;
    protected int index = 0;

    public static void main (String[] arg) {
        Simulator simulator = new Simulator();
        JFrame frame = new JFrame("Pitch tracking task");
        frame.setSize(400, 500);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        simulator.make(frame, simulator);

    }

    private void make(JFrame frame, Simulator simulator){
        indicators = new Indicators();
        frame.getContentPane().add(BorderLayout.CENTER, indicators);

        textField = new TextField();
        textField.setEditable(false);
        controlSignal = new ControlSignal();
        aircraftSymbol = new AircraftSymbol();

        Restart restart = new Restart();
        restart.setButton(frame, simulator);

        frame.getContentPane().add(BorderLayout.NORTH, textField);

        forcingFunction = new ForcingFunction(SIMULATION_TIME, SAMPLE_FREQUENCY);
        initializeSignals();
        baseline = new BaselineCalculation(forcingFunction.sampleFrequency);

        timer = new Timer(1000 / SAMPLE_FREQUENCY, actionListener);
        timer.setRepeats(true);
        timer.start();
    }

    private ActionListener actionListener = new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            if (index < forcingFunction.numberSamples) {
                ft = input[index];
                double fd = disturbance[index];
                textField.setText("time = " + String.format("%.2f", ((double) (index)/ (double) (forcingFunction.sampleFrequency))) + " [s]");

                baseline.performCalculation(controlSignal.getControlSignal() + fd);
                theta = baseline.y;

                error[index] = ft - theta;
                indicators.repaint();
                index++;
            } else {
                Statistics.data = error;
                Statistics.size = error.length;
                System.out.println("Your score is: " + Statistics.getVariance());
                timer.stop();
                theta = 0;
            }
        }
    };

    private void initializeSignals(){
        input = forcingFunction.makeTargetFunction();
        disturbance = forcingFunction.makeDisturbanceFunction();
        error = new double[input.length];
    }

    protected Timer getTimer() {
        return timer;
    }

    private class Indicators extends JPanel {
        @Override
        public void paintComponent(Graphics graphics){
            super.paintComponent(graphics);
            Graphics2D graphics2d = (Graphics2D) graphics;
            graphics2d.setColor(Color.black);
            int DisplayType = 0; // (0 = Compensatory, 1 = Pursuit, 2 = Preview)
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
