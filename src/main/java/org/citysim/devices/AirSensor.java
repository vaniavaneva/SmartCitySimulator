package org.citysim.devices;

import org.citysim.events.CityEventType;
import org.citysim.factory.DeviceType;
import org.citysim.util.ConfigLoader;
import org.citysim.strategies.air.AirAnalysisStrategy;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AirSensor extends CityDevice{
    private double pm25;
    private AirAnalysisStrategy strategy;
    private List<Double> history = new ArrayList<>();
    private final int MAX_HISTORY = ConfigLoader.getInt("max.history");
    private static final double AIR_QUALITY_THRESHOLD = ConfigLoader.getDouble("air.quality.threshold");


    public AirSensor(String id){
        super(id, 5, DeviceType.AIR_SENSOR);
    }

    public void setStrategy(AirAnalysisStrategy strategy) {
        this.strategy = Objects.requireNonNull(strategy, "Strategy cannot be null");
    }

    @Override
    public void performAction(){
        pm25 = 20 + Math.random() * 60;
        history.add(pm25);

        if(history.size() > MAX_HISTORY){
            history.removeFirst();
        }

        double quality = strategy.analyzeQuality(history);
        if(quality > AIR_QUALITY_THRESHOLD){
            getCity().notifyListeners(this, CityEventType.ALERT,
                    "Poor air quality PM2.5=" + String.format("%.2f", pm25));
        } else {
            getCity().notifyListeners(this, CityEventType.STATUS,"Air OK: " + String.format("%.2f", pm25));
        }
    }
}
