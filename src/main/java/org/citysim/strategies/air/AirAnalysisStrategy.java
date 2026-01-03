package org.citysim.strategies.air;

import java.util.List;

public interface AirAnalysisStrategy {
    double analyzeQuality(List<Double> measurements);
}
