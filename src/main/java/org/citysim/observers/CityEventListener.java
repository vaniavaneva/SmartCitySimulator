package org.citysim.observers;

import org.citysim.devices.CityDevice;
import org.citysim.events.CityEventType;

public interface CityEventListener {
    void onEvent(CityDevice device, CityEventType type, String message);
}
