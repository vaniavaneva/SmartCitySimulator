package observers;

import devices.CityDevice;

public interface CityEventListener {
    void onDeviceStatusChanged(CityDevice device, String status);
    void onAlert(CityDevice device, String message);
}
// AirSensor известява при лошо качество на въздуха
// TrafficLight известява при задръстване
