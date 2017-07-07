import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by martin on 7-7-2017.
 */
public class Restart {

    private Simulator simulator;

    public void setButton(JFrame frame, Simulator simulator) {
        this.simulator = simulator;
        JPanel panel = new JPanel();
        JButton restartButton = new JButton("Restart");
        Restarter restarter = new Restarter();
        restartButton.addActionListener(restarter);

        panel.add(restartButton);
        frame.getContentPane().add(BorderLayout.EAST, panel);
    }

    public class Restarter implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            simulator.index = 0;
            simulator.baseline.reset();
            simulator.getTimer().start();
        }
    }

}
