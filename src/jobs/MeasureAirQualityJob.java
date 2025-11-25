package jobs;

import city.City;

public class MeasureAirQualityJob implements CityJob {
    private City city;
    public MeasureAirQualityJob(City city){}
    @Override
    public void execute() { }
    @Override
    public String getDescription() { return null; }

}
