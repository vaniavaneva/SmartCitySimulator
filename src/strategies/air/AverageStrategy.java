package strategies.air;

import java.util.*;

public class AverageStrategy implements AirAnalysisStrategy {
    @Override
    public double analyzeQuality(List<Double> measurements) {
        if (measurements.isEmpty()) return 0;

        double sum = 0;
        for (double value : measurements) {
            sum += value;
        }
        return sum / measurements.size();
    }
}

