import javax.swing.*;
import java.awt.*;

/**
 * Created by martin on 6-7-2017.
 */
public class ControlSignal {

    private JFrame frame;

    public ControlSignal(JFrame f){
        frame = f;
    }

    public double getControlSignal() {

        double mousePositionY = MouseInfo.getPointerInfo().getLocation().getY()-frame.getY();

        if(mousePositionY < 0) {
            mousePositionY = 0;
        } else if (mousePositionY > Simulator.screenHeight){
            mousePositionY = Simulator.screenHeight;
        }

        return 0.02 * (mousePositionY - 0.5 * Simulator.screenHeight);
    }

}
