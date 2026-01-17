package org.citysim.strategies.traffic;

import org.citysim.devices.TrafficLightState;

import java.util.Objects;

public class FixedCycleStrategy implements TrafficStrategy{
    @Override
    public int computeGreenTime(TrafficLightState state){
        if(state == TrafficLightState.YELLOW){
            return 1;
        } else {
            return 10;
        }
    }
}
