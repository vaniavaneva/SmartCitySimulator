package strategies.traffic;

public class AdaptiveTrafficStrategy implements TrafficStrategy{
    @Override
    public int calculateGreenLightDuration(int vehicleCount) {
        if (vehicleCount <= 5) {
            return 15000; //15s
        } else if (vehicleCount <= 20) {
            return 25000; //25s
        } else {
            return 40000; //40s
        }
    }

}
