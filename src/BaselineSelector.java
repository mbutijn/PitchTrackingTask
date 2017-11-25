import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by martin on 25-11-2017.
 */
public class BaselineSelector {

    private Simulator simulator;
    private String name;
    private boolean checked;
    private JPanel panel;
    private JRadioButton cessnaPitchSelector;
    public ButtonGroup group = new ButtonGroup();

    public BaselineSelector(String dynamics_name, boolean initChecked, JPanel jpanel){
        panel = jpanel;
        name = dynamics_name;
        checked = initChecked;
    }

    public void MakeButtons(BaselineSelector[] buttons, JFrame frame) {
        for (BaselineSelector bs : buttons) {
            bs.setButton(frame, simulator);
            Selector selector = new Selector();
            bs.cessnaPitchSelector.addActionListener(selector);
            group.add(bs.cessnaPitchSelector);
        }
    }

    public void setButton(JFrame frame, Simulator simulator) {
        this.simulator = simulator;
        cessnaPitchSelector = new JRadioButton(name, checked);
        panel.add(cessnaPitchSelector);
        frame.getContentPane().add(BorderLayout.EAST, panel);
    }

    private class Selector implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JRadioButton btn = (JRadioButton) e.getSource();
            System.out.println(btn.getText());
        }
    }

}
