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
    private JRadioButton radioButton;
    public static ButtonGroup buttonGroup = new ButtonGroup();
    public int index;

    public BaselineSelector(String dynamics_name, boolean initChecked, JPanel jpanel, int i){
        panel = jpanel;
        name = dynamics_name;
        checked = initChecked;
        index = i;
    }

    public void MakeButton(BaselineSelector bs){
        radioButton = new JRadioButton(name, checked);
        panel.add(radioButton,BorderLayout.CENTER);
        bs.radioButton.addActionListener(new Selector());
        buttonGroup.add(bs.radioButton);
    }

    private class Selector implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JRadioButton btn = (JRadioButton) e.getSource();
            System.out.println(btn.getText());
            Simulator.baselineChoice = index;
        }

    }

}
