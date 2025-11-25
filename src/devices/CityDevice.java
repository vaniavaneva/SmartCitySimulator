package devices;

import factory.DeviceType;
import observers.CityEventListener;
import devices.states.DeviceState;
import java.util.*;

public abstract class CityDevice {
    protected String id;
    protected DeviceType type;
    protected DeviceState state;
    protected List<CityEventListener> listeners;

    public CityDevice(String id, DeviceType type) {
        this.id = id;
        this.type = type;
    }

    public void updateStatus(String status) {}
    public void notifyListeners(String message) {}

    public void setState(DeviceState state) {
        this.state = state;
    }
    public DeviceState getState() {
        return state;
    }
    public String getId() {
        return id;
    }
    public DeviceType getType() {
        return type;
    }
    public abstract void performAction();
}
