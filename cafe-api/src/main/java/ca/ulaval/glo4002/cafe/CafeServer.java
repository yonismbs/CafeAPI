package ca.ulaval.glo4002.cafe;

import ca.ulaval.glo4002.cafe.api.rest.menu.MenuResource;
import ca.ulaval.glo4002.cafe.application.service.*;
import ca.ulaval.glo4002.cafe.domain.billing.BillRepository;
import ca.ulaval.glo4002.cafe.domain.cafe.CafeFactory;
import ca.ulaval.glo4002.cafe.domain.cafe.layout.LayoutFactory;
import ca.ulaval.glo4002.cafe.domain.product.ProductFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

import ca.ulaval.glo4002.cafe.api.rest.CloseResource;
import ca.ulaval.glo4002.cafe.api.rest.checkIn.CheckInResource;
import ca.ulaval.glo4002.cafe.api.rest.client.ClientResource;
import ca.ulaval.glo4002.cafe.api.rest.config.ConfigResource;
import ca.ulaval.glo4002.cafe.api.rest.group.GroupResource;
import ca.ulaval.glo4002.cafe.api.rest.inventory.InventoryResource;
import ca.ulaval.glo4002.cafe.api.rest.layout.LayoutResource;
import ca.ulaval.glo4002.cafe.application.service.order.OrderService;
import ca.ulaval.glo4002.cafe.application.service.order.billing.BillService;
import ca.ulaval.glo4002.cafe.domain.billing.BillFactory;
import ca.ulaval.glo4002.cafe.domain.billing.strategy.DefaultStrategy;
import ca.ulaval.glo4002.cafe.domain.cafe.CafeConfig;
import ca.ulaval.glo4002.cafe.domain.cafe.reservation.strategy.DefaultReservationStrategy;
import ca.ulaval.glo4002.cafe.domain.client.ClientRepository;
import ca.ulaval.glo4002.cafe.infrastructure.local.LocalBillRepository;
import ca.ulaval.glo4002.cafe.infrastructure.local.LocalCafeRepository;
import ca.ulaval.glo4002.cafe.infrastructure.local.LocalClientRepository;
import ca.ulaval.glo4002.cafe.infrastructure.local.LocalGroupRepository;

public class CafeServer implements Runnable {
    private static final int PORT = 8181;
    private static final int GROUP_TIP_RATE = 0;
    private static final String DEFAULT_CAFE_NAME = "Les 4-Fées";
    private static final int DEFAULT_CUBE_SIZE = 4;
    private static final String DEFAULT_COUNTRY = "None";
    private static final String DEFAULT_PROVINCE = "";
    private static final String DEFAULT_STATE = "";

    private GroupResource groupResource;
    private LayoutResource layoutResource;
    private CheckInResource checkInResource;
    private CloseResource closeResource;
    private ClientResource clientResource;
    private ConfigResource configResource;
    private InventoryResource inventoryResource;
    private MenuResource menuResource;

    private Server server;

    public static void main(String[] args) {
        new CafeServer().run();
    }

    public void initializeEndpoints() {
        ClientRepository clientRepository = new LocalClientRepository();
        LocalBillRepository billingRepository = new LocalBillRepository();

        LocalCafeRepository cafeRepository = new LocalCafeRepository();
        ProductFactory productFactory = new ProductFactory();
        ProductService productService = new ProductService(productFactory);
        CafeService cafeService = new CafeService(cafeRepository, new CafeFactory(), new LayoutFactory(), productService);
        cafeService.regenerateWithNewConfig(new CafeConfig(DEFAULT_CAFE_NAME,
                new DefaultReservationStrategy(), DEFAULT_CUBE_SIZE, new DefaultStrategy(GROUP_TIP_RATE), DEFAULT_COUNTRY, DEFAULT_PROVINCE, DEFAULT_STATE));

        ClientService clientService = new ClientService(clientRepository, billingRepository);
        GroupService groupService = new GroupService(cafeService, new LocalGroupRepository());
        BillService billService = new BillService(clientRepository, new BillFactory(),
            cafeRepository, billingRepository);
        CheckInService checkInService = new CheckInService(cafeService, clientService, groupService, billService);
        ConfigService configService = new ConfigService(checkInService, cafeService);
        OrderService orderService = new OrderService(clientRepository, cafeService, productFactory);

        groupResource = new GroupResource(groupService);
        layoutResource = new LayoutResource(cafeService);
        checkInResource = new CheckInResource(checkInService);
        clientResource = new ClientResource(clientService, orderService);
        closeResource = new CloseResource(checkInService);
        configResource = new ConfigResource(configService);
        inventoryResource = new InventoryResource(cafeService);
        menuResource = new MenuResource(productService);
    }

    public void run() {
        initializeEndpoints();
        server = new Server(PORT);

        ServletContextHandler contextHandler = new ServletContextHandler(server, "/");
        ResourceConfig packageConfig = new ResourceConfig();
        registerResources(packageConfig);
        ServletContainer container = new ServletContainer(packageConfig);
        ServletHolder servletHolder = new ServletHolder(container);

        contextHandler.addServlet(servletHolder, "/*");

        try {
            server.start();
        } catch (Exception e) {
            stop();
        }

    }

    private void registerResources(ResourceConfig resourceConfig) {
        resourceConfig.packages("ca.ulaval.glo4002.cafe.api")
                .register(groupResource)
                .register(layoutResource)
                .register(checkInResource)
                .register(closeResource)
                .register(clientResource)
                .register(configResource)
                .register(inventoryResource)
                .register(menuResource);
    }

    public void stop() {
        try {
            server.stop();
        } catch (Exception e) {
            server.destroy();
        }
    }
}
