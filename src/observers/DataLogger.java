package observers;

import devices.CityDevice;

public class DataLogger implements CityEventListener {
    private String filePath;
    public DataLogger(String path) { }
    @Override
    public void onDeviceStatusChanged(CityDevice device, String status) { }
    @Override
    public void onAlert(CityDevice device, String message) { }
}
