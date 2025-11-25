package jobs;

import city.City;

public class OptimizeTrafficJob implements CityJob {
    private City city;
    public OptimizeTrafficJob(City city) { }
    @Override
    public void execute() { }
    @Override
    public String getDescription() { return null; }
}
