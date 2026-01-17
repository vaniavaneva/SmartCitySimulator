package org.citysim.devices;

import org.citysim.events.CityEventType;
import org.citysim.factory.DeviceType;

public class StreetLight extends CityDevice implements LightSensor{
    private int hour = 0;

    public StreetLight(String id){
        super(id, 10, DeviceType.STREET_LIGHT);
    }

    @Override
    public void performAction(){
        hour = (hour + 1) % 24;

        boolean on = isDark(hour);

        getCity().notifyListeners(this, CityEventType.STREET_LIGHT_CHANGE,
                "Hour: " + hour + " | Light " + (on ? "ON" : "OFF"));
    }

    @Override
    public boolean isDark(int hour) {
        return hour >= 20 || hour < 6;
    }
}
