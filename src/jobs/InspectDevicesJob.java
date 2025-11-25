package jobs;

import city.City;

public class InspectDevicesJob implements CityJob {
    private City city;
    public InspectDevicesJob(City city) { }
    @Override
    public void execute() { }
    @Override
    public String getDescription() { return null; }
}

