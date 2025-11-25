package devices;

import devices.states.OperationalState;
import factory.DeviceType;

public class StreetLight extends CityDevice{
    private int brightness;
    private boolean isOn;

    public StreetLight(String id) {
        super(id, DeviceType.STREET_LIGHT);
        this.state = new OperationalState();
    }

    public void setBrightness(int level){
        if (level < 0) level = 0;
        if (level > 100) level = 100;
        this.brightness = level;
    }

    public int getBrightness() {
        return brightness;
    }

    public void switchOn(){
        isOn = true;
        notifyListeners("StreetLight ON");
    }

    public void switchOff(){
        isOn = false;
        notifyListeners("StreetLight OFF");
    }

    public boolean isOn() {
        return isOn;
    }

    @Override public void performAction(){
        updateStatus("StreetLight: " + (isOn ? "ON" : "OFF")
                + ", brightness=" + brightness);
    }
}
