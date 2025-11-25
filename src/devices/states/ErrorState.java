package devices.states;

import devices.CityDevice;

public class ErrorState implements DeviceState {
    @Override
    public void handle(CityDevice device) {
        device.updateStatus("ERROR: Device malfunction");
        device.notifyListeners("Device ERROR detected: " + device.getId());
    }
}

