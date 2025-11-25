package city;

import devices.CityDevice;
import java.util.*;

public class CityZone {
    private String name;
    private List<CityDevice> devices;

    public CityZone(String name) {
        this.name = name;
    }
    public void addDevice(CityDevice device) {
        devices.add(device);
    }
    public List<CityDevice> getDevices(){
        return devices;
    }
    public String getName() {
        return name;
    }
}
