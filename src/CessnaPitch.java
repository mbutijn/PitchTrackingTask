/**
 * Created by martin on 6-7-2017.
 */
public class CessnaPitch extends BaselineDynamics {

    private static final double Ka = 10.6189, T1 = 0.9906, T2 = 2.7565, T3 = 7.6122, Ks = 0.29, K = Ka * Ks;
    public double ydot, ydotdot, ydotdotdot, udot, oldu;

    CessnaPitch(int frequency) {
        samplePeriod = (double) 1/frequency;
    }

    protected void performCalculation(double u){
    	udot = differentiate(oldu, u);
    	oldu = u; // Store the old pilot's control signal
        ydotdotdot = -T2*ydotdot - T3*ydot + K*udot + K*T1*u;

        // Integrate
    	ydotdot = integrate(ydotdot, ydotdotdot);
    	ydot = integrate(ydot, ydotdot);
    	y = integrate(y, ydot);
    }

    protected void reset(){
        y = 0;
        ydot = 0;
        ydotdot = 0;
        ydotdotdot = 0;
        udot = 0;
        oldu = 0;
        System.out.println("Cessna pitch reset");
    }

}
