import java.awt.*;

/**
 * Created by martin on 6-7-2017.
 */
public class AircraftSymbol {

    public void makeSymbol(Graphics2D graphics2d, double theta) {
        graphics2d.drawLine(20, (int) (200 - theta), 120, (int) (200 - theta));
        graphics2d.drawLine(180, (int) (200 - theta), 280, (int) (200 - theta));

        graphics2d.drawLine(120, (int) (200 - theta), 120, (int) (220 - theta));
        graphics2d.drawLine(180, (int) (200 - theta), 180, (int) (220 - theta));

        graphics2d.drawLine(140, (int) (200 - theta), 160, (int) (200 - theta));
    }

}
