package devices;

import devices.states.OperationalState;
import factory.DeviceType;
import strategies.traffic.FixedCycleStrategy;
import strategies.traffic.TrafficStrategy;

public class TrafficLight extends CityDevice {
    private TrafficStrategy strategy;
    private String currentColor = "RED";
    private int vehicleCount;

    public TrafficLight(String id){
        super(id, DeviceType.TRAFFIC_LIGHT);
        this.strategy = new FixedCycleStrategy();
        this.vehicleCount = 0;
        this.state = new OperationalState();
    }

    public void setVehicleCount(int count) {
        this.vehicleCount = count;
    }

    public int getVehicleCount() {
        return vehicleCount;
    }

    public void switchColor(){
        switch(currentColor) {
            case "RED" -> currentColor = "GREEN";
            case "GREEN" -> currentColor = "YELLOW";
            case "YELLOW" -> currentColor = "RED";
        }
        updateStatus("Switched to: " + currentColor);
    }

    public void setStrategy(TrafficStrategy strategy){
        this.strategy = strategy;
    }

    public TrafficStrategy getStrategy() {
        return strategy;
    }

    public void emergencyMode() {
        currentColor = "RED";
        updateStatus("EMERGENCY MODE: All lights RED");
    }

    @Override
    public void performAction(){
        if (strategy != null) {
            int greenDuration = strategy.calculateGreenLightDuration(vehicleCount);
            updateStatus("Adaptive green duration: " + greenDuration + " ms");
        }
        state.handle(this);
    }
}
