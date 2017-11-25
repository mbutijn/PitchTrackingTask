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
    protected CessnaPitch baseline2;
    protected Vehicle baseline;

    private static final int SAMPLE_FREQUENCY = 100, SIMULATION_TIME = 90;
    private double[] input, error;
    private TextField textField;
    private double ft, theta;
    public static final int screenHeight = 500;
    protected int index = 0;

    public static void main (String[] arg) {
        Simulator simulator = new Simulator();
        JFrame frame = new JFrame("Pitch tracking task");
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        simulator.make(frame, simulator);
        frame.setSize(500, screenHeight);
        frame.setResizable(false);

    }

    private void make(JFrame frame, Simulator simulator){
        indicators = new Indicators();
        frame.getContentPane().add(BorderLayout.CENTER, indicators);

        textField = new TextField();
        textField.setEditable(false);
        controlSignal = new ControlSignal();
        aircraftSymbol = new AircraftSymbol();
        forcingFunction = new ForcingFunction(SIMULATION_TIME, SAMPLE_FREQUENCY);
        initializeSignals();

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4,1));

        makeUI(frame, simulator, panel);

        baseline2 = new CessnaPitch(forcingFunction.sampleFrequency);
        baseline = new Vehicle(forcingFunction.sampleFrequency);

        timer = new Timer(1000 / SAMPLE_FREQUENCY, actionListener);
        timer.setRepeats(true);

    }

    private void makeUI(JFrame frame, Simulator simulator, JPanel panel) {
        Restart restart = new Restart();
        restart.setButton(frame, simulator, panel);

        BaselineSelector cessnaPitch = new BaselineSelector("Cessna Pitch Dynamics", true, panel);
        BaselineSelector high = new BaselineSelector("High-Bandwith Baseline", false, panel);
        BaselineSelector low = new BaselineSelector("Low-Bandwith Baseline", false, panel);

        BaselineSelector[] buttons = new BaselineSelector[]{cessnaPitch, high, low};
        cessnaPitch.MakeButtons(buttons, frame);

        frame.getContentPane().add(BorderLayout.NORTH, textField);
    }

    private ActionListener actionListener = new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            if (index < forcingFunction.numberSamples) {
                ft = input[index];
                textField.setText("time = " + String.format("%.2f", ((double) (index)/ (double) (forcingFunction.sampleFrequency))) + " [s]");

                baseline.performCalculation(controlSignal.getControlSignal());
                theta = baseline.y;

                error[index] = ft - theta;
                indicators.repaint();
                index++;
            } else { // done
                Statistics.data = error;
                Statistics.size = error.length;
                String score = String.format("%.2f", Statistics.getVariance());
                System.out.println("Your score is: " + score);
                textField.setText(textField.getText() + " Your score is: " + score);
                timer.stop();
                theta = 0;
            }
        }
    };

    private void initializeSignals(){
        input = forcingFunction.makeSignal(1, 3, 4);
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
            int DisplayType = 1; // (0 = Compensatory, 1 = Pursuit, 2 = Preview)
            if (DisplayType == 0) {
                // Draw the forcing function
                graphics2d.drawLine(20, (int) (0.5*screenHeight - 50 * (ft - theta)), 280, (int) (0.5*screenHeight - 50 * (ft - theta)));

                //Draw the aircraft Symbol
                aircraftSymbol.makeSymbol(graphics2d, 0);
            } else if (DisplayType == 1) {
                // Draw the forcing function
                graphics2d.drawLine(20, (int) (0.5*screenHeight - 50 * ft), 280, (int) (0.5*screenHeight - 50 * ft));

                //Draw the aircraft Symbol
                aircraftSymbol.makeSymbol(graphics2d, 50 * theta);
            }
        }
    }

}
