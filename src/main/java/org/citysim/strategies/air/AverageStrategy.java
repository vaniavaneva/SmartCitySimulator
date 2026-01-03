package org.citysim.strategies.air;

import java.util.List;
import java.util.Objects;

public class AverageStrategy implements AirAnalysisStrategy{
    @Override
    public double analyzeQuality(List<Double> measurements) {
        if(Objects.isNull(measurements) || measurements.isEmpty()) return 0;

        double weightedSum = 0;
        double totalWeight = 0;
        int n = measurements.size();

        for(int i = 0; i < n; i++) {
            double value = measurements.get(i);
            if(value > 0){
                int weight = i + 1;
                weightedSum += value * weight;
                totalWeight += weight;
            }
        }
        return weightedSum / totalWeight;
    }
}
