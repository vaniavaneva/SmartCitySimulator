package devices.states;

import devices.CityDevice;

public class MaintenanceState implements DeviceState{
    @Override
    public void handle(CityDevice device){
        device.updateStatus("Device under maintenance");
    }
}
