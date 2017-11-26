import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by martin on 25-11-2017.
 */
public class BaselineSelector {

    private String name;
    private boolean checked;
    private JPanel panel;
    private JRadioButton cessnaPitchSelector;
    public static ButtonGroup group = new ButtonGroup();
    public int index;

    public BaselineSelector(String dynamics_name, boolean initChecked, JPanel jpanel, int i){
        panel = jpanel;
        name = dynamics_name;
        checked = initChecked;
        index = i;
    }

    public void MakeButton(BaselineSelector bs){
        cessnaPitchSelector = new JRadioButton(name, checked);
        panel.add(cessnaPitchSelector,BorderLayout.SOUTH);
        bs.cessnaPitchSelector.addActionListener(new Selector());
        group.add(bs.cessnaPitchSelector);
    }

    private class Selector implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JRadioButton btn = (JRadioButton) e.getSource();
            System.out.println(btn.getText());
            Simulator.baselineChoice = index;
            System.out.println(Simulator.baselineChoice);
        }

    }

}
