import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by martin on 7-7-2017.
 */
public class Restart {

    private Simulator simulator;
    private JButton restartButton;

    protected JPanel setButton(JFrame frame, Simulator simulator, JPanel panel) {
        this.simulator = simulator;
        restartButton = new JButton("Start");
        restartButton.setSize(100,50);
        Restarter restarter = new Restarter();
        restartButton.addActionListener(restarter);

        panel.add(restartButton);
        frame.getContentPane().add(BorderLayout.EAST, panel);
        return panel;
    }

    private class Restarter implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            simulator.index = 0;
            simulator.baseline.reset();
            restartButton.setText("Restart");
            simulator.getTimer().start();
        }
    }

}
