package org.citysim.strategies.traffic;

import org.citysim.devices.TrafficLightState;

public interface TrafficStrategy {
    int computeGreenTime(TrafficLightState state);
}
