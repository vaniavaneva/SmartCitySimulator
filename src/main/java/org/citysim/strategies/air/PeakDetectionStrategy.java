package org.citysim.strategies.air;

import java.util.Deque;

public class PeakDetectionStrategy implements AirAnalysisStrategy{
    @Override
    public double analyzeQuality(Deque<Double> measurements) {
        if(measurements.isEmpty()) return 0;
        double max = 0;

        for(double value : measurements){
            if(value > max) max = value;

        } return max;
    }
}
