package ca.ulaval.glo4002.cafe.unit.service;

import ca.ulaval.glo4002.cafe.application.service.ProductService;
import ca.ulaval.glo4002.cafe.domain.billing.TaxationStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import ca.ulaval.glo4002.cafe.application.service.CafeService;
import ca.ulaval.glo4002.cafe.domain.cafe.Cafe;
import ca.ulaval.glo4002.cafe.domain.cafe.CafeConfig;
import ca.ulaval.glo4002.cafe.domain.cafe.CafeFactory;
import ca.ulaval.glo4002.cafe.domain.cafe.CafeId;
import ca.ulaval.glo4002.cafe.domain.cafe.CafeRepository;
import ca.ulaval.glo4002.cafe.domain.cafe.exception.InsufficientSeatsException;
import ca.ulaval.glo4002.cafe.domain.cafe.layout.Layout;
import ca.ulaval.glo4002.cafe.domain.cafe.layout.LayoutFactory;
import ca.ulaval.glo4002.cafe.domain.cafe.reservation.strategy.ReservationStrategy;
import ca.ulaval.glo4002.cafe.domain.client.ClientId;
import ca.ulaval.glo4002.cafe.domain.group.GroupName;
import ca.ulaval.glo4002.cafe.domain.group.exception.NoGroupSeatsException;

class CafeServiceTest {

    private static final int FRANCHISE_CUBE_SIZE = 4;
    private static final int SEAT_COUNT = 4;
    private static final GroupName GROUP_NAME = new GroupName("Bulletproof Boy Scouts");
    private static final String CAFE_COUNTRY = "CA";
    private static final String CAFE_PROVINCE = "";
    private static final String CAFE_STATE = "";
    private static final String FRANCHISE_NAME = "Les 4-Fées";
    private static final CafeId FRANCHISE_ID = CafeId.DEFAULT_CAFE_ID;
    private static final int SEAT_NUMBER_RETURN = 1;
    private static final ClientId CLIENT_ID = new ClientId("11111");
    private CafeRepository cafeRepository;
    private CafeFactory cafeFactory;
    private LayoutFactory layoutFactory;
    private CafeService cafeService;
    private ProductService productService;
    private Cafe cafe;

    @BeforeEach
    void setup() {
        cafeRepository = mock(CafeRepository.class);
        cafeFactory = mock(CafeFactory.class);
        layoutFactory = mock(LayoutFactory.class);
        productService = mock(ProductService.class);
        cafeService = new CafeService(cafeRepository, cafeFactory, layoutFactory, productService);
        cafe = mock(Cafe.class);
    }

    @Test
    void givenALayoutID_whenTryingToFindTheLayout_thenShouldReturnTheLayout() {
        when(cafeRepository.find(FRANCHISE_ID)).thenReturn(cafe);
        assertEquals(cafe, cafeService.find(FRANCHISE_ID));
    }

    @Test
    void givenAClientID_whenAssigningClientIdToSeat_thenReturnTheSeatNumber() throws InsufficientSeatsException, NoGroupSeatsException {
        when(cafeRepository.find(any(CafeId.class))).thenReturn(cafe);
        when(cafe.assignClientIdToSeat(CLIENT_ID)).thenReturn(SEAT_NUMBER_RETURN);

        assertEquals(SEAT_NUMBER_RETURN, cafeService.assignClientIdToSeat(CLIENT_ID, GroupName.VOID));
    }

    @Test
    void givenClientWithGroup_whenAssigningClientIdToSeat_thenAssignClientIdToSeatFromGroupIsCalled()
            throws InsufficientSeatsException, NoGroupSeatsException {
        when(cafeRepository.find(any(CafeId.class))).thenReturn(cafe);

        cafeService.assignClientIdToSeat(CLIENT_ID, GROUP_NAME);

        verify(cafe).assignClientIdToSeatFromGroup(CLIENT_ID, GROUP_NAME);
    }

    @Test
    void givenFranchiseConfig_whenRegenerateWithNewConfig_thenFranchiseFactoryIsCalledWithConfig() {
        ReservationStrategy reservationStrategy = mock(ReservationStrategy.class);
        TaxationStrategy taxationStrategy = mock(TaxationStrategy.class);
        CafeConfig cafeConfig = new CafeConfig(FRANCHISE_NAME, reservationStrategy,
                FRANCHISE_CUBE_SIZE, taxationStrategy, CAFE_COUNTRY, CAFE_PROVINCE, CAFE_STATE);
        Layout layout = mock(Layout.class);
        when(layoutFactory.getLayout(FRANCHISE_CUBE_SIZE)).thenReturn(layout);
        when(cafe.getId()).thenReturn(FRANCHISE_ID);
        when(cafeFactory.getCafe(cafeConfig, layout)).thenReturn(cafe);

        cafeService.regenerateWithNewConfig(cafeConfig);

        verify(cafeFactory).getCafe(cafeConfig, layout);
    }
    @Test
    void whenReserveSeats_thenFranchiseCallsReserveSeats() throws InsufficientSeatsException {
        when(cafeRepository.find(FRANCHISE_ID)).thenReturn(cafe);

        cafeService.reserveSeats(SEAT_COUNT, GROUP_NAME);

        verify(cafe).reserveSeats(SEAT_COUNT, GROUP_NAME);
    }
}
