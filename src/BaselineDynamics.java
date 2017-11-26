public class BaselineDynamics {

	double samplePeriod;
	public double y;

	public double integrate(double output, double integrand){
		return output + integrand * samplePeriod;
	}
	
	public double differentiate(double previous, double current){
		return (current - previous) / samplePeriod;
	}

	protected void reset(){
	}

}
