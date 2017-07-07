/**
 * Created by martin on 7-7-2017.
 */
public class Statistics {

    static double[] data;
    static int size;

    static double getMean() {
        double sum = 0.0;
        for(double a : data)
            sum += a;
        return sum/size;
    }

    static double getVariance() {
        double mean = getMean();
        double temp = 0;
        for(double a :data)
            temp += (a-mean)*(a-mean);
        return temp/(size-1);
    }
}
