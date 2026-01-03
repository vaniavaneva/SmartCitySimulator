package org.citysim.devices;

import org.citysim.events.CityEventType;
import org.citysim.factory.DeviceType;
import org.citysim.strategies.traffic.TrafficStrategy;

import java.util.Objects;

public class TrafficLight extends CityDevice{
    private volatile String state = "RED";
    private TrafficStrategy strategy;

    public TrafficLight(String id) {
        super(id,1, DeviceType.TRAFFIC_LIGHT);
    }

    public void setStrategy(TrafficStrategy strategy){
        this.strategy = Objects.requireNonNull(strategy, "Strategy cannot be null");
    }

    @Override
    public void performAction() {
        if(strategy==null){
            getCity().notifyListeners(this, CityEventType.ALERT, "No strategy set.");
            return;
        }

        switch (state){
            case "RED" -> state = "GREEN";
            case "GREEN" -> state = "YELLOW";
            case "YELLOW" -> state = "RED";
        }

        int newInterval = strategy.computeGreenTime(state);
        if(newInterval != this.intervalSeconds) {
            this.intervalSeconds = newInterval;

            reschedule();
        }
        getCity().notifyListeners(this, CityEventType.TRAFFIC_LIGHT_CHANGE,
                "Light changed to: " + state + " (next change in " + newInterval + "s)");
    }

    private void reschedule() {
        if(future != null) {
            future.cancel(false);
        }
        schedule(getCity().getThreadPool());
    }
}
