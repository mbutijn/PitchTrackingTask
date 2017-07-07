/**
 * Created by martin on 2-7-2017.
 */
public class ForcingFunction {

    public int numberSamples, sampleFrequency;
    private double[] time;
    private final double omega_m = getBaseFrequency();
    private final double[][] matrix = getMatrix();

    ForcingFunction(int duration, int sampleFrequency) {
        this.sampleFrequency = sampleFrequency;
        this.numberSamples = sampleFrequency * duration + 1;
        time = makeTimeVector(new double[numberSamples], new Linspace(0, duration, numberSamples));
    }

    private double[] makeTimeVector(double[] time, Linspace timeProperties) {
        time[0] = 0;
        for (int j = 0; j < numberSamples - 1; j++) {
            time[j + 1] = timeProperties.getNextFloat();
        }
        return time;
    }

    // Target target function
    public double[] makeTargetFunction() {
        double[] ft = new double[numberSamples];
        double[] frequency = new double[time.length];

        for (int i = 0; i < 10; i++) {
            for (int k = 0; k < time.length; k++) {
                frequency[k] = matrix[i][1] * omega_m * time[k];
                ft[k] = ft[k] + matrix[i][3] * Math.sin(frequency[k] + matrix[i][4]);
            }
        }
        return ft;
    }

    public double[] makeDisturbanceFunction() {
        double[] fd = new double[numberSamples];
        double[] frequency = new double[time.length];

        for (int i = 0; i < 10; i++) {
            for (int k = 0; k < time.length; k++) {
                frequency[k] = matrix[i][5] * omega_m * time[k];
                fd[k] = fd[k] + matrix[i][7] * Math.sin(frequency[k] + matrix[i][8]);
            }
        }
        return fd;
    }

    private double getBaseFrequency(){
        return 2*Math.PI / 81.92; // ~ 0.0767 rad/s
    }

    private double[][] getMatrix(){
        return new double[][]{{ 1,   6,  0.460, 1.397, 1.288,   5,  0.383, 0.601, -2.069},
                              { 2,  13,  0.997, 0.977, 6.089,  11,  0.844, 0.788,  2.065},
                              { 3,  27,  2.071, 0.441, 5.507,  23,  1.764, 0.48,  -2.612},
                              { 4,  41,  3.145, 0.237, 1.734,  37,  2.838, 0.313,  3.759},
                              { 5,  53,  4.065, 0.159, 2.019,  51,  3.912, 0.331,  4.739},
                              { 6,  73,  5.599, 0.099, 0.441,  71,  5.446, 0.411,  1.856},
                              { 7, 103,  7.900, 0.063, 5.175, 101,  7.747, 0.55,   1.376},
                              { 8, 139, 10.661, 0.046, 3.415, 137, 10.508, 0.753,  2.792},
                              { 9, 194, 14.880, 0.036, 1.066, 171, 13.116, 0.992,  3.288},
                              {10, 229, 17.564, 0.033, 3.479, 226, 17.334, 1.481,  3.381}};
    }
}
