package ca.ulaval.glo4002.cafe.unit.domain.cafe;

import ca.ulaval.glo4002.cafe.domain.billing.TaxationStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import ca.ulaval.glo4002.cafe.domain.cafe.Cafe;
import ca.ulaval.glo4002.cafe.domain.cafe.CafeConfig;
import ca.ulaval.glo4002.cafe.domain.cafe.CafeFactory;
import ca.ulaval.glo4002.cafe.domain.cafe.layout.Layout;
import ca.ulaval.glo4002.cafe.domain.cafe.reservation.strategy.ReservationStrategy;


public class CafeFactoryTest {

    private static final int DEFAULT_CUBE_SIZE = 6;
    private static final String FRANCHISE_NAME = "Les Quatres-Fee";
    private static final String COUNTRY_NAME = "None";
    private static final String STATE_NAME = "";
    private static final String PROVINCE_NAME = "";
    private Layout layout;
    private CafeFactory cafeFactory;
    private ReservationStrategy reservationStrategy;
    private TaxationStrategy taxationStrategy;
    private CafeConfig cafeConfig;

    @BeforeEach
    void setup() {
        layout = mock(Layout.class);
        reservationStrategy = mock(ReservationStrategy.class);
        taxationStrategy = mock(TaxationStrategy.class);
        cafeConfig = new CafeConfig(FRANCHISE_NAME, reservationStrategy, DEFAULT_CUBE_SIZE,
                taxationStrategy, COUNTRY_NAME, STATE_NAME, PROVINCE_NAME);

        cafeFactory = new CafeFactory();
    }

    @Test
    void givenAFranchiseConfig_whenGetFranchise_thenCreatedFranchiseHasMatchingReservationStrategy() {
        Cafe cafe = cafeFactory.getCafe(cafeConfig, layout);

        assertEquals(cafeConfig.reservationStrategy(), cafe.getReservationStrategy());
    }

    @Test
    void givenAFranchiseConfig_whenGetFranchise_thenCreatedFranchiseHasMatchingName() {
        Cafe cafe = cafeFactory.getCafe(cafeConfig, layout);

        assertEquals(cafeConfig.name(), cafe.getName());
    }

}
