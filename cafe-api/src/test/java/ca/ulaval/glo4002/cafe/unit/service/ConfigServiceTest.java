package ca.ulaval.glo4002.cafe.unit.service;

import ca.ulaval.glo4002.cafe.domain.billing.TaxationStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import ca.ulaval.glo4002.cafe.application.service.CafeService;
import ca.ulaval.glo4002.cafe.application.service.CheckInService;
import ca.ulaval.glo4002.cafe.application.service.ConfigService;
import ca.ulaval.glo4002.cafe.domain.cafe.CafeConfig;
import ca.ulaval.glo4002.cafe.domain.cafe.reservation.strategy.ReservationStrategy;

public class ConfigServiceTest {

    private static final String COUNTRY = "None";
    private static final String PROVINCE = "";
    private static final String STATE = "";
    private static final String FRANCHISE_NAME = "Franchise Name";
    private ConfigService configService;
    private CheckInService checkInService;
    private ReservationStrategy reservationStrategy;
    private CafeService cafeService;
    private CafeConfig cafeConfig;
    private TaxationStrategy taxationStrategy;

    @BeforeEach
    void setup() {
        checkInService = mock(CheckInService.class);
        cafeService = mock(CafeService.class);
        reservationStrategy = mock(ReservationStrategy.class);
        taxationStrategy = mock(TaxationStrategy.class);
        cafeConfig = new CafeConfig(FRANCHISE_NAME, reservationStrategy, 4, taxationStrategy,
            COUNTRY, PROVINCE, STATE);
        configService = new ConfigService(checkInService, cafeService);
    }

    @Test
    void whenNewConfiguration_thenCloseAll() {
        configService.newConfiguration(cafeConfig);

        verify(checkInService).close();
    }

    @Test
    void whenNewConfiguration_thenFranchiseWasRegenerated() {
        configService.newConfiguration(cafeConfig);

        verify(cafeService).regenerateWithNewConfig(cafeConfig);
    }
}
