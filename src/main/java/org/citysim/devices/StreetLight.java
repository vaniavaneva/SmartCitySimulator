package org.citysim.devices;

import org.citysim.events.CityEventType;
import org.citysim.factory.DeviceType;

public class StreetLight extends CityDevice{
    private int hour = 0;

    public StreetLight(String id){
        super(id, 3, DeviceType.STREET_LIGHT);
    }

    @Override
    public void performAction(){
        hour = (hour + 1) % 24;

        boolean on = hour >= 20 || hour < 6;

        getCity().notifyListeners(this, CityEventType.STREET_LIGHT_CHANGE,
                "Hour: " + hour + " | Light " + (on ? "ON" : "OFF"));
    }
}
