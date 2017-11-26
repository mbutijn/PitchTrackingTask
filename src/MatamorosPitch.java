public class MatamorosPitch extends BaselineDynamics {

    public double ydot, ydotdot, udot, oldu;
    private static final double M_DELTA_E = -1.5, K_S = -1;
    private final double M_Q;

    MatamorosPitch(int frequency, double m_q) {
        M_Q = m_q;
        samplePeriod = (double) 1/frequency;
    }
    
    protected void performCalculation(double u){
    	udot = differentiate(oldu, u);
        oldu = u; // Store the old pilot's control signal
    	ydotdot = -M_Q*ydot + M_DELTA_E *M_Q* K_S *u;
    	ydot = integrate(ydot, ydotdot);
    	y = integrate(y, ydot);
    }
    
    protected void reset(){
        y = 0;
        ydot = 0;
        ydotdot = 0;
        udot = 0;
        oldu = 0;
        System.out.println("Matamoros Baseline reset");
    }
	
}
