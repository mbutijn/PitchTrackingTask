public class Dynamics {

	double samplePeriod;
	
	public double integrate(double output, double integrand){
		return output + integrand * samplePeriod;
	}
	
	public double differentiate(double previous, double current){
		return (current - previous) / samplePeriod;
	}

}
