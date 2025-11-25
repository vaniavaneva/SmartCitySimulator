package jobs;

import city.City;

public class DimStreetLightsJob implements CityJob {
    private City city;
    public DimStreetLightsJob(City city) { }
    @Override
    public void execute() { }
    @Override
    public String getDescription() { return null; }
}

