package org.citysim.strategies.traffic;

import java.util.Objects;

public class FixedCycleStrategy implements TrafficStrategy{
    @Override
    public int computeGreenTime(String state){
        if(Objects.equals(state, "YELLOW")){
            return 1;
        } else {
            return 10;
        }
    }
}
