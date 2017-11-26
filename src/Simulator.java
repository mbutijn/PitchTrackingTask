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
    public BaselineDynamics cessnaPitch, highBandwith, lowBandwith;

    private static final int SAMPLE_FREQUENCY = 100, SIMULATION_TIME = 90;
    private double[] input, error;
    private TextField textField;
    private double ft;
    public double theta;
    protected static final int screenHeight = 700;
    protected int index = 0;
    public static int baselineChoice = 0, displayChoice = 0;

    public static void main (String[] arg) {
        JFrame frame = new JFrame("Pitch tracking task");
        Simulator simulator = new Simulator();
        simulator.make(frame, simulator);

        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(500, screenHeight);
        frame.setResizable(false);

    }

    private void make(JFrame frame, Simulator simulator){
        indicators = new Indicators();
        frame.getContentPane().add(BorderLayout.CENTER, indicators);

        textField = new TextField();
        textField.setEditable(false);
        controlSignal = new ControlSignal(frame);
        aircraftSymbol = new AircraftSymbol();
        forcingFunction = new ForcingFunction(SIMULATION_TIME, SAMPLE_FREQUENCY);
        initializeSignals();

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6,1));
        Restart restart = new Restart();
        JButton restartButton = restart.makeButton(simulator);

        panel.add(restartButton);

        frame.getContentPane().add(BorderLayout.NORTH, textField);
        frame.getContentPane().add(BorderLayout.EAST, panel);

        cessnaPitch = new CessnaPitch(forcingFunction.sampleFrequency);
        highBandwith = new MatamorosPitch(forcingFunction.sampleFrequency, 3);
        lowBandwith = new MatamorosPitch(forcingFunction.sampleFrequency, 1.5);

        BaselineSelector cessnaPitch = new BaselineSelector("Cessna Pitch BaselineDynamics", true, panel, 0);
        BaselineSelector high = new BaselineSelector("High-Bandwith Baseline", false, panel, 1);
        BaselineSelector low = new BaselineSelector("Low-Bandwith Baseline", false, panel, 2);
        BaselineSelector[] baselineSelectors = new BaselineSelector[]{cessnaPitch, high, low};

        for (BaselineSelector bs : baselineSelectors) {
            bs.MakeButton(bs);
        }

        DisplayTypeSelector compensatory = new DisplayTypeSelector("Compensatory", true, panel, 0);
        DisplayTypeSelector pursuit = new DisplayTypeSelector("Pursuit", false, panel, 1);
        DisplayTypeSelector[] displaySelectors = new DisplayTypeSelector[]{compensatory, pursuit};

        for (DisplayTypeSelector ds : displaySelectors){
            ds.MakeButton(ds);
        }

        timer = new Timer(1000 / SAMPLE_FREQUENCY, actionListener);
        timer.setRepeats(true);

    }

    private ActionListener actionListener = new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            if (index < forcingFunction.numberSamples) {
                ft = input[index];
                textField.setText("time = " + String.format("%.2f", ((double) (index)/ (double) (forcingFunction.sampleFrequency))) + " [s]");

                textField.setText(textField.getText() + ";u = " + String.format("%.2f",controlSignal.getControlSignal()));
                if (baselineChoice == 0) {
                    CessnaPitch dynamics = (CessnaPitch) cessnaPitch;
                    dynamics.performCalculation(controlSignal.getControlSignal());
                    theta = dynamics.y;
                } else if (baselineChoice == 1) {
                    MatamorosPitch dynamics = (MatamorosPitch) highBandwith;
                    dynamics.performCalculation(controlSignal.getControlSignal());
                    theta = dynamics.y;
                } else if (baselineChoice == 2){
                    MatamorosPitch dynamics = (MatamorosPitch) lowBandwith;
                    dynamics.performCalculation(controlSignal.getControlSignal());
                    theta = dynamics.y;
                }

                error[index] = ft - theta;
                indicators.repaint();
                index++;
            } else { // done
                Statistics.data = error;
                Statistics.size = error.length;
                String score = String.format("%.2f", Statistics.getVariance());
                System.out.println("Your score is: " + score);
                textField.setText(textField.getText() + " Your score is: " + score);
                Restart.restartButton.setText("Hold current");
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

            if (displayChoice == 0) {
                // Draw the forcing function
                graphics2d.drawLine(20, (int) (0.5*screenHeight - 50 * (ft - theta)), 280, (int) (0.5*screenHeight - 50 * (ft - theta)));

                //Draw the aircraft Symbol
                aircraftSymbol.makeSymbol(graphics2d, 0);
            } else if (displayChoice == 1) {
                // Draw the forcing function
                graphics2d.drawLine(20, (int) (0.5*screenHeight - 50 * ft), 280, (int) (0.5*screenHeight - 50 * ft));

                //Draw the aircraft Symbol
                aircraftSymbol.makeSymbol(graphics2d, 50 * theta);
            }
        }
    }

}
