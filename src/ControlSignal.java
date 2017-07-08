import java.awt.*;

/**
 * Created by martin on 6-7-2017.
 */
public class ControlSignal {

    public double getControlSignal() {
        double mousePositionY = MouseInfo.getPointerInfo().getLocation().getY();
        double limit = 0.5 * Simulator.screenHeight;
        if (mousePositionY > limit){
            return limit * 0.02;
        } else if (mousePositionY < -limit) {
            return -limit * 0.02;
        } else {
            return (mousePositionY - limit) * 0.02;
        }
    }

}
