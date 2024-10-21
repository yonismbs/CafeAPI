package ca.ulaval.glo4002.cafe.unit.api.rest.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import ca.ulaval.glo4002.cafe.api.rest.config.ConfigDTOAssembler;
import ca.ulaval.glo4002.cafe.api.rest.config.dto.ConfigDTO;
import ca.ulaval.glo4002.cafe.api.rest.exceptions.InvalidCountryException;
import ca.ulaval.glo4002.cafe.api.rest.exceptions.InvalidGroupReservationMethodException;
import ca.ulaval.glo4002.cafe.api.rest.exceptions.InvalidGroupTipRateException;
import ca.ulaval.glo4002.cafe.api.rest.exceptions.InvalidStateOrProvinceException;
import ca.ulaval.glo4002.cafe.domain.cafe.CafeConfig;
import ca.ulaval.glo4002.cafe.domain.cafe.reservation.strategy.DefaultReservationStrategy;
import ca.ulaval.glo4002.cafe.domain.cafe.reservation.strategy.FullCubesReservationStrategy;
import ca.ulaval.glo4002.cafe.domain.cafe.reservation.strategy.NoLonersReservationStrategy;

class ConfigDTOAssemblerTest {

    private static final String DEFAULT_RESERVATION = "Default";
    private static final String FULL_CUBES_RESERVATION = "Full Cubes";
    private static final String NO_LONERS_RESERVATION = "No Loners";
    private static final String INVALID_RESERVATION = "invalid";
    public static final String FRANCHISE_NAME = "Les 4-Fee";
    public static final int CUBE_SIZE = 4;
    private static final float GROUP_TIP_RATE = 5;
    private static final float WRONG_GROUP_TIP_RATE = 150;
    private static final String CAFE_COUNTRY_CANADA = "CA";
    private static final String WRONG_COUNTRY = "FR";
    private static final String CAFE_PROVINCE = "QC";
    private static final String WRONG_PROVINCE_CANADA = "XX";
    private static final String  CAFE_STATE = "";
    private ConfigDTOAssembler configDTOAssembler;

    @BeforeEach
    void setup() {
        configDTOAssembler = new ConfigDTOAssembler();
    }

    @Test
    void givenGroupReservationNameIsDefault_whenToFranchiseConfig_thenReturnedFranchiseConfigHasReservationStrategyDefault() {
        ConfigDTO configDTO = new ConfigDTO(DEFAULT_RESERVATION, FRANCHISE_NAME, CUBE_SIZE,
            CAFE_COUNTRY_CANADA,CAFE_PROVINCE, CAFE_STATE,
            GROUP_TIP_RATE);

        assertInstanceOf(DefaultReservationStrategy.class, configDTOAssembler.toFranchiseConfig(configDTO).reservationStrategy());
    }

    @Test
    void givenGroupReservationNameIsFullCubes_whenToFranchiseConfig_thenReturnedFranchiseConfigHasReservationFullCubes() {
        ConfigDTO configDTO = new ConfigDTO(FULL_CUBES_RESERVATION, FRANCHISE_NAME, CUBE_SIZE,
            CAFE_COUNTRY_CANADA,CAFE_PROVINCE, CAFE_STATE, GROUP_TIP_RATE);

        assertInstanceOf(FullCubesReservationStrategy.class, configDTOAssembler.toFranchiseConfig(configDTO).reservationStrategy());
    }

    @Test
    void givenGroupReservationNameIsNoLoners_whenToFranchiseConfig_thenReturnedFranchiseConfigHasReservationStrategyNoLoners() {
        ConfigDTO configDTO = new ConfigDTO(NO_LONERS_RESERVATION, FRANCHISE_NAME, CUBE_SIZE,
            CAFE_COUNTRY_CANADA,CAFE_PROVINCE, CAFE_STATE, GROUP_TIP_RATE);

        assertInstanceOf(NoLonersReservationStrategy.class, configDTOAssembler.toFranchiseConfig(configDTO).reservationStrategy());
    }

    @Test
    void givenInvalidGroupReservationName_whenToFranchiseConfig_thenInvalidGroupReservationMethodeExceptionIsThrown() {
        ConfigDTO configDTO = new ConfigDTO(INVALID_RESERVATION, FRANCHISE_NAME, CUBE_SIZE,
            CAFE_COUNTRY_CANADA,CAFE_PROVINCE, CAFE_STATE, GROUP_TIP_RATE);

        assertThrows(InvalidGroupReservationMethodException.class, () -> configDTOAssembler.toFranchiseConfig(configDTO));
    }

    @Test
    void whenToFranchiseConfig_thenFranchiseNameMatchesByValue(){
        ConfigDTO configDTO = new ConfigDTO(DEFAULT_RESERVATION, FRANCHISE_NAME, CUBE_SIZE,
            CAFE_COUNTRY_CANADA,CAFE_PROVINCE, CAFE_STATE, GROUP_TIP_RATE);
        CafeConfig cafeConfig = configDTOAssembler.toFranchiseConfig(configDTO);

        assertEquals(FRANCHISE_NAME, cafeConfig.name());
    }

    @Test
    void whenToFranchiseConfig_thenCubeSizeMatchesByValue() {
        ConfigDTO configDTO = new ConfigDTO(DEFAULT_RESERVATION, FRANCHISE_NAME, CUBE_SIZE,
            CAFE_COUNTRY_CANADA,CAFE_PROVINCE, CAFE_STATE, GROUP_TIP_RATE);
        CafeConfig cafeConfig = configDTOAssembler.toFranchiseConfig(configDTO);

        assertEquals(CUBE_SIZE, cafeConfig.cubeSize());
    }

    @Test
    void givenAConfigForCafe_whenGeneratePaymentStrategy_thenInvalidCountryException() {
        ConfigDTO configDTO = new ConfigDTO(DEFAULT_RESERVATION, FRANCHISE_NAME, CUBE_SIZE,
            WRONG_COUNTRY,CAFE_PROVINCE, CAFE_STATE, GROUP_TIP_RATE);

        assertThrows(InvalidCountryException.class, () -> {
            configDTOAssembler.toFranchiseConfig(configDTO);
        });
    }

    @Test
    void givenAConfigForCafe_whenWrongStateOrProvinceForCountry_thenInvalidStateOrProvinceException() {
        ConfigDTO configDTO = new ConfigDTO(DEFAULT_RESERVATION, FRANCHISE_NAME, CUBE_SIZE,
            CAFE_COUNTRY_CANADA, WRONG_PROVINCE_CANADA, CAFE_STATE, GROUP_TIP_RATE);

        assertThrows(InvalidStateOrProvinceException.class, () -> {
            configDTOAssembler.toFranchiseConfig(configDTO);
        });
    }

    @Test
    void givenAConfigForCafe_whenGroupTipRateIsMoreThanAn100_thenInvalidGroupTipRateException() {
        ConfigDTO configDTO = new ConfigDTO(DEFAULT_RESERVATION, FRANCHISE_NAME, CUBE_SIZE,
            CAFE_COUNTRY_CANADA, CAFE_PROVINCE, CAFE_STATE, WRONG_GROUP_TIP_RATE);

        assertThrows(InvalidGroupTipRateException.class, () -> {
            configDTOAssembler.toFranchiseConfig(configDTO);
        });
    }

}
