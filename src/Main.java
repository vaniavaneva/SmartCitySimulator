import city.*;
import devices.*;
import observers.*;
import concurrent.CityThreadPool;
import factory.*;
import jobs.*;
import strategies.air.PeakDetectionStrategy;
import strategies.traffic.AdaptiveTrafficStrategy;
import utils.CityStats;

public class Main {
    public static void main(String[] args) {
        // Създаване на града
        City city = new City("София");

        // Добавяне на зони
        CityZone downtown = new CityZone("Център");
        CityZone suburbs = new CityZone("Люлин");

        // Създаване на устройства
        TrafficLight tl1 = (TrafficLight) DeviceFactory.createDevice(
                DeviceType.TRAFFIC_LIGHT, "TL-001"
        );
        AirSensor as1 = (AirSensor) DeviceFactory.createDevice(
                DeviceType.AIR_SENSOR, "AS-001"
        );
        StreetLight sl1 = (StreetLight) DeviceFactory.createDevice(
                DeviceType.STREET_LIGHT, "SL-001"
        );

        downtown.addDevice(tl1);
        downtown.addDevice(as1);
        downtown.addDevice(sl1);

        city.addZone(downtown);
        city.addZone(suburbs);

        // Добавяне на observers
        Dashboard dashboard = new Dashboard();
        AlertSystem alerts = new AlertSystem();
        DataLogger logger = new DataLogger("city_log.txt");

        city.addEventListener(dashboard);
        city.addEventListener(alerts);
        city.addEventListener(logger);

        // Конфигуриране на стратегии
        tl1.setStrategy(new AdaptiveTrafficStrategy());
        as1.setStrategy(new PeakDetectionStrategy());

        // Стартиране на симулацията
        CityController controller = CityController.getInstance();
        controller.startSimulation(city);

        // Планиране на задачи
        CityThreadPool pool = new CityThreadPool(4);

        pool.scheduleRepeating(new MeasureAirQualityJob(city), 5_000);
        pool.scheduleRepeating(new OptimizeTrafficJob(city), 30_000);
        pool.scheduleRepeating(new DimStreetLightsJob(city), 60_000);

        // Симулация работи 5 минути
        try {
            Thread.sleep(300_000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Статистики
        CityStats.printReport(city);

        // Спиране
        pool.shutdown();
    }
}