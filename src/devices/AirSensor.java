package devices;

import devices.states.OperationalState;
import factory.DeviceType;
import strategies.air.AirAnalysisStrategy;
import strategies.air.AverageStrategy;

import java.util.*;

public class AirSensor extends CityDevice {
    private double pm25;
    private AirAnalysisStrategy strategy;
    private List<Double> history = new ArrayList<>();

    public AirSensor(String id) {
        super(id, DeviceType.AIR_SENSOR);
        this.strategy = new AverageStrategy();
        this.state = new OperationalState();
    }

    public void measure() {
        pm25 = 20 + Math.random() * 60;
        history.add(pm25);
        double quality = strategy.analyzeQuality(history);
        if (quality > 55) {
            notifyListeners("ALERT: Poor air quality detected! PM2.5=" + pm25);
        }
        updateStatus("Measured: " + String.format("%.2f", pm25) + " μg/m3");
    }

    public void setStrategy(AirAnalysisStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public void performAction() {
        state.handle(this);
    }
}
