package strategies.traffic;

public class FixedCycleStrategy implements TrafficStrategy{
    @Override
    public int calculateGreenLightDuration(int vehicleCount){
        return 30000; //30s
    }
}
