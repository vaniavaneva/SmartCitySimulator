package devices.states;

import devices.CityDevice;

public interface DeviceState {
    void handle(CityDevice device);
}
// Operational, Maintenance, Error, Emergency
