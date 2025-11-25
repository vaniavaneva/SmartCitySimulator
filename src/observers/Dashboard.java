package observers;

import devices.CityDevice;

public class Dashboard  implements CityEventListener{
    @Override
    public void onDeviceStatusChanged(CityDevice device, String status) { }
    @Override
    public void onAlert(CityDevice device, String message) { }

}
