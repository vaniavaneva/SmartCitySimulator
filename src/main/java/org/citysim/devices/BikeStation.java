package org.citysim.devices;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import org.citysim.util.ConfigLoader;
import org.citysim.events.CityEventType;
import org.citysim.factory.DeviceType;

public class BikeStation extends CityDevice{
    private int bikesAvailable;
    private int capacity;
    private int chargers;
    private List<ScheduledFuture<?>> chargingSlots = new ArrayList<>();

    private static final double RENT_PROBABILITY = ConfigLoader.getDouble("bike.rent.probability");
    private static final double RETURN_PROBABILITY = ConfigLoader.getDouble("bike.rent.probability");
    private static final int MIN_CHARGE_TIME_SEC = ConfigLoader.getInt("min.charge");
    private static final int MAX_CHARGE_TIME_SEC = ConfigLoader.getInt("max.charge");

    public BikeStation(String id) {
        super(id, 7, DeviceType.BIKE_STATION);
    }

    public void setBikesAvailable(int bikes){
        if(bikes > capacity) throw new IllegalArgumentException("Available bikes should be <= capacity");
        bikesAvailable = bikes;
    }

    public void setCapacity(int capacity){
        if(capacity < bikesAvailable) throw new IllegalArgumentException("Capacity should be >= bikes available");
        this.capacity = capacity;
    }

    public void setChargers(int chargers){
        if(chargers <= 0) throw new IllegalArgumentException("Chargers can't be <= 0");
        this.chargers = chargers;
    }

    public synchronized int getBikesAvailable() {
        return bikesAvailable;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getChargers(){
        return chargers;
    }

    @Override
    public synchronized void performAction() {
        double chance = Math.random(); //0-1

        if(chance < RENT_PROBABILITY){ //40% rent
            if(bikesAvailable > 0){
                bikesAvailable--;
                getCity().notifyListeners(this, CityEventType.BIKE_RENTED,
                        "Bike rented | Available: " + bikesAvailable);
            } else {
                getCity().notifyListeners(this, CityEventType.ALERT, "No bikes available");
            }
        } else if(chance < RENT_PROBABILITY + RETURN_PROBABILITY){ //40% return
            if(bikesAvailable < capacity){
                bikesAvailable++;
                getCity().notifyListeners(this, CityEventType.BIKE_RETURNED,
                        "Bike returned | Available: " + bikesAvailable);
            } else {
                getCity().notifyListeners(this, CityEventType.ALERT,
                        "Station full, cannot return bike");
            }
        } else { //20% electric bike charging
            if(chargers > 0){
                startCharging();
            } else{
                getCity().notifyListeners(this, CityEventType.ALERT,
                        "No chargers available");
            }
        }

        if(bikesAvailable <= 1){
            getCity().notifyListeners(this, CityEventType.ALERT,
                    "Bike levels low (" + bikesAvailable + ")");
        }
        if(chargers <= 1){
            getCity().notifyListeners(this, CityEventType.ALERT,
                    "Charger levels low (" + chargers + ")");
        }
    }

    private void startCharging(){
        chargers--;
        int chargeTime = (int)(Math.random() * (MAX_CHARGE_TIME_SEC - MIN_CHARGE_TIME_SEC + 1) + MIN_CHARGE_TIME_SEC); //20-30

        getCity().notifyListeners(this, CityEventType.BIKE_CHARGING,
                "Electric bike charging | ETC: " + chargeTime + "s");

        ScheduledFuture<?> future = getCity().getThreadPool().getScheduler().schedule(() -> {
            chargers++;
            getCity().notifyListeners(this, CityEventType.STATUS,
                    "Charging complete. Chargers available: " + chargers);
        }, chargeTime, TimeUnit.SECONDS);
        chargingSlots.add(future);
    }

    public void cancelAllCharging() {
        for(ScheduledFuture<?> future : chargingSlots) {
            if(!future.isDone()) {
                future.cancel(false);
            }
        }
        chargingSlots.clear();
    }
}
