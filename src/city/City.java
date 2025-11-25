package city;

import observers.CityEventListener;
import devices.CityDevice;
import observers.Dashboard;

import java.util.*;

public class City {
    private String name;
    private List<CityZone> zones;
    private List<CityEventListener> listeners;

    public City(String name){
        this.name = name;
    }
    public void addZone(CityZone zone) {
        zones.add(zone);
    }
    public void addEventListener(CityEventListener listener) {
        listeners.add(listener);
    }
    public List<CityZone> getZones() {
        return zones;
    }
    public String getName() {
        return name;
    }
}
