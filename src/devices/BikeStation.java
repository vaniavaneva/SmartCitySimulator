package devices;

public class BikeStation extends CityDevice {
    private int bikesAvailable;
    private int capacity;

    public BikeStation(String id) { super(id, null); }
    public void addBike() { }
    public void removeBike() { }
    public int getBikesAvailable() { return 0; }
    @Override
    public void performAction() { }
}

