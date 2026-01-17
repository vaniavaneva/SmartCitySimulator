package org.citysim.devices;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.citysim.util.ConfigLoader;
import org.citysim.events.CityEventType;
import org.citysim.factory.DeviceType;

public class BikeStation extends CityDevice{
    private final AtomicInteger bikesAvailable = new AtomicInteger(0);
    private int capacity;
    private final AtomicInteger chargers = new AtomicInteger(0);
    private final List<ScheduledFuture<?>> chargingSlots = new CopyOnWriteArrayList();

    private static final double RENT_PROBABILITY = ConfigLoader.getDouble("bike.rent.probability");
    private static final double RETURN_PROBABILITY = ConfigLoader.getDouble("bike.rent.probability");
    private static final int MIN_CHARGE_TIME_SEC = ConfigLoader.getInt("min.charge");
    private static final int MAX_CHARGE_TIME_SEC = ConfigLoader.getInt("max.charge");

    public BikeStation(String id) {
        super(id, 17, DeviceType.BIKE_STATION);
    }

    public void setBikesAvailable(int bikes){
        if(bikes > capacity) throw new IllegalArgumentException("Available bikes should be <= capacity");
        bikesAvailable.set(bikes);
    }

    public void setCapacity(int capacity){
        if(capacity < bikesAvailable.get()) throw new IllegalArgumentException("Capacity should be >= bikes available");
        this.capacity = capacity;
    }

    public void setChargers(int chargers){
        if(chargers <= 0) throw new IllegalArgumentException("Chargers can't be <= 0");
        this.chargers.set(chargers);
    }

    public synchronized int getBikesAvailable() {
        return bikesAvailable.get();
    }

    public int getCapacity() {
        return capacity;
    }

    public int getChargers(){
        return chargers.get();
    }

    @Override
    public synchronized void performAction() {
        double chance = Math.random(); //0-1

        if(chance < RENT_PROBABILITY){ //40% rent
            if(bikesAvailable.get() > 0){
                bikesAvailable.decrementAndGet();
                getCity().notifyListeners(this, CityEventType.BIKE_RENTED,
                        "Bike rented | Available: " + bikesAvailable);
            } else {
                getCity().notifyListeners(this, CityEventType.ALERT, "No bikes available");
            }
        } else if(chance < RENT_PROBABILITY + RETURN_PROBABILITY){ //40% return
            if(bikesAvailable.get() < capacity){
                bikesAvailable.incrementAndGet();
                getCity().notifyListeners(this, CityEventType.BIKE_RETURNED,
                        "Bike returned | Available: " + bikesAvailable);
            } else {
                getCity().notifyListeners(this, CityEventType.ALERT,
                        "Station full, cannot return bike");
            }
        } else { //20% electric bike charging
            if(chargers.get() > 0){
                startCharging();
            } else{
                getCity().notifyListeners(this, CityEventType.ALERT,
                        "No chargers available");
            }
        }

        if(bikesAvailable.get() <= 1){
            getCity().notifyListeners(this, CityEventType.ALERT,
                    "Bike levels low (" + bikesAvailable + ")");
        }
        if(chargers.get() <= 1){
            getCity().notifyListeners(this, CityEventType.ALERT,
                    "Charger levels low (" + chargers + ")");
        }
    }

    private void startCharging(){
        chargers.decrementAndGet();
        int chargeTime = (int)(Math.random() * (MAX_CHARGE_TIME_SEC - MIN_CHARGE_TIME_SEC + 1) + MIN_CHARGE_TIME_SEC); //20-30

        getCity().notifyListeners(this, CityEventType.BIKE_CHARGING,
                "Electric bike charging | ETC: " + chargeTime + "s");

        ScheduledFuture<?> future = getCity().getThreadPool().getScheduler().schedule(() -> {
            chargers.incrementAndGet();
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
