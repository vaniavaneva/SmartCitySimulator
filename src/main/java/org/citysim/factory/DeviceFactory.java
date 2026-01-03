package org.citysim.factory;

import org.citysim.devices.*;

public class DeviceFactory {
    public static CityDevice createDevice(DeviceType type, String id) {
    return switch(type){
        case TRAFFIC_LIGHT -> new TrafficLight(id);
        case AIR_SENSOR -> new AirSensor(id);
        case STREET_LIGHT -> new StreetLight(id);
        case BIKE_STATION -> new BikeStation(id);
    };
}
}
