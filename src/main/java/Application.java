import com.shop.lib.Injector;
import com.shop.model.Car;
import com.shop.model.Driver;
import com.shop.model.Manufacturer;
import com.shop.service.CarService;
import com.shop.service.DriverService;
import com.shop.service.ManufacturerService;

public class Application {
    private static final Injector injector = Injector.getInstance("com.shop");

    public static void main(String[] args) {
        ManufacturerService manufacturerService = (ManufacturerService)
                injector.getInstance(ManufacturerService.class);

        Manufacturer firstManufacturer = new Manufacturer("Porsche", "Germany");
        Manufacturer secondManufacturer = new Manufacturer("Chevrolet", "USA");
        Manufacturer thirdManufacturer = new Manufacturer("Audi", "Germany");

        manufacturerService.create(firstManufacturer);
        manufacturerService.create(secondManufacturer);
        manufacturerService.create(thirdManufacturer);

        System.out.println(manufacturerService.getAll());

        System.out.println(manufacturerService.get(2L));

        Manufacturer manufacturerUpdated = new Manufacturer("Toyota", "Japan");
        manufacturerUpdated.setId(1L);
        manufacturerService.update(manufacturerUpdated);

        System.out.println(manufacturerService.getAll());

        DriverService driverService = (DriverService) injector.getInstance(DriverService.class);

        Driver firstDriver = new Driver("Steve", "USA1943");
        Driver secondDriver = new Driver("Tony", "USA2011");
        Driver thirdDriver = new Driver("Bruce", "USA2012");
        Driver fourthDriver = new Driver("Vision", "USA2015");

        driverService.create(firstDriver);
        driverService.create(secondDriver);
        driverService.create(thirdDriver);
        driverService.create(fourthDriver);

        System.out.println(driverService.getAll());
        System.out.println(driverService.get(1L));

        Driver driverUpdated = new Driver("Steve", "USA1945");
        driverUpdated.setId(1L);
        driverService.update(driverUpdated);

        System.out.println(driverService.getAll());

        CarService carService = (CarService) injector.getInstance(CarService.class);
        Car firstCar = new Car("Panamera", manufacturerService.get(3L));
        Car secondCar = new Car("Camaro", manufacturerService.get(2L));
        Car thirdCar = new Car("A3", manufacturerService.get(3L));

        carService.create(firstCar);
        carService.create(secondCar);
        carService.create(thirdCar);

        System.out.println(carService.getAll());
        System.out.println(carService.get(3L));

        Car carUpdated = new Car("Corola", manufacturerService.get(1L));
        carUpdated.setId(1L);
        carService.update(carUpdated);

        System.out.println(carService.getAll());

        carService.addDriverToCar(driverService.get(1L), carService.get(1L));
        carService.addDriverToCar(driverService.get(2L), carService.get(1L));
        carService.addDriverToCar(driverService.get(3L), carService.get(1L));
        carService.addDriverToCar(driverService.get(4L), carService.get(1L));
        carService.addDriverToCar(driverService.get(1L), carService.get(2L));
        carService.addDriverToCar(driverService.get(1L), carService.get(3L));
        carService.addDriverToCar(driverService.get(2L), carService.get(3L));
        carService.addDriverToCar(driverService.get(3L), carService.get(3L));

        System.out.println(carService.getAll());

        carService.removeDriverFromCar(driverService.get(3L), carService.get(3L));
        System.out.println(carService.getAll());

        carService.getAllByDriver(1L);
        System.out.println(carService.getAll());

        manufacturerService.delete(1L);
        carService.delete(1L);
        driverService.delete(1L);

        System.out.println(manufacturerService.getAll());
        System.out.println(carService.getAll());
        System.out.println(driverService.getAll());

    }
}
