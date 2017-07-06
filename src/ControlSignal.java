import java.awt.*;

/**
 * Created by martin on 6-7-2017.
 */
public class ControlSignal {

    public double getControlSignal() {
        PointerInfo mouseInfo = MouseInfo.getPointerInfo();
        return mouseInfo.getLocation().getY() - 200;
    }

}
