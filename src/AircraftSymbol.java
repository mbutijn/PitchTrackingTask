import java.awt.*;

/**
 * Created by martin on 6-7-2017.
 */
public class AircraftSymbol {

    public void makeSymbol(Graphics2D graphics2d, double theta) {
        double midPosition = 0.5 * Simulator.screenHeight;
        graphics2d.drawLine(20, (int) (midPosition - theta), 120, (int) (midPosition - theta));
        graphics2d.drawLine(180, (int) (midPosition - theta), 280, (int) (midPosition - theta));

        graphics2d.drawLine(120, (int) (midPosition - theta), 120, (int) (midPosition + 20 - theta));
        graphics2d.drawLine(180, (int) (midPosition - theta), 180, (int) (midPosition + 20 - theta));

        graphics2d.drawLine(140, (int) (midPosition - theta), 160, (int) (midPosition - theta));
    }

}
