import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by martin on 26-11-2017.
 */
public class DisplayTypeSelector {

    private JRadioButton radioButton;
    private String name;
    private boolean checked;
    private JPanel panel;
    public static ButtonGroup displayGroup = new ButtonGroup();
    public int index;

    public DisplayTypeSelector(String dynamics_name, boolean initChecked, JPanel jpanel, int i){
        panel = jpanel;
        name = dynamics_name;
        checked = initChecked;
        index = i;
    }

    public void MakeButton(DisplayTypeSelector displayTypeSelector){
        radioButton = new JRadioButton(name, checked);
        panel.add(radioButton, BorderLayout.SOUTH);
        displayTypeSelector.radioButton.addActionListener(new Selector());
        displayGroup.add(displayTypeSelector.radioButton);
    }

    private class Selector implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JRadioButton btn = (JRadioButton) e.getSource();
            System.out.println(btn.getText());
            Simulator.displayChoice = index; // (0 = Compensatory, 1 = Pursuit, 2 = Preview)
        }
    }

}
