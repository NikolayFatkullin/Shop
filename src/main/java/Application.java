import com.shop.lib.Injector;
import com.shop.model.Manufacturer;
import com.shop.service.ManufacturerService;

public class Application {
    private static final Injector injector = Injector.getInstance("com.shop");

    public static void main(String[] args) {
        ManufacturerService manufacturerService = (ManufacturerService)
                injector.getInstance(ManufacturerService.class);
        Manufacturer firstManufacturer = new Manufacturer("Porsche", "Germany");
        Manufacturer secondManufacturer = new Manufacturer("Chevrolet", "USA");
        manufacturerService.create(firstManufacturer);
        manufacturerService.create(secondManufacturer);
        System.out.println(manufacturerService.getAll());
        System.out.println(manufacturerService.get(1L));
        Manufacturer manufacturerUpdated = new Manufacturer("Toyota", "Japan");
        manufacturerUpdated.setId(1L);
        manufacturerService.update(manufacturerUpdated);
        System.out.println(manufacturerService.getAll());
        manufacturerService.delete(1L);
        System.out.println(manufacturerService.getAll());
    }
}
