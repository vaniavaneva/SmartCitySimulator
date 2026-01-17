package org.citysim.strategies.air;

import java.util.Deque;

public interface AirAnalysisStrategy {
    double analyzeQuality(Deque<Double> measurements);
}
