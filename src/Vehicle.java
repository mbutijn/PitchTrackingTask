
public class Vehicle extends Dynamics{

    public double y, ydot, ydotdot, udot, oldu;
    private static final double M_q = 3, M_delta_e = -1.5, K_s = -1;
	
    Vehicle (int frequency) {
        samplePeriod = (double) 1/frequency;
    }
    
    protected void performCalculation(double u){
    	udot = differentiate(oldu, u);
        oldu = u; // Store the old pilot's control signal
    	ydotdot = -M_q*ydot + M_delta_e*M_q*K_s*u;
    	ydot = integrate(ydot, ydotdot);
    	y = integrate(y, ydot);
    }
    
    protected void reset(){
        y = 0;
        ydot = 0;
        ydotdot = 0;
        udot = 0;
        oldu = 0;
    }
	
}
