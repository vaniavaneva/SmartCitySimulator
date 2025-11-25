package devices.states;

import devices.AirSensor;
import devices.CityDevice;
import devices.TrafficLight;

public class OperationalState implements DeviceState{
    @Override
    public void handle(CityDevice device) {
        switch(device.getType()){
            case AIR_SENSOR -> {
                AirSensor sensor = (AirSensor) device;
                sensor.measure();}
            case TRAFFIC_LIGHT -> {
                TrafficLight light = (TrafficLight) device;
                light.switchColor();}
        }
    }
}
