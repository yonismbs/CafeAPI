package ca.ulaval.glo4002.cafe.unit.api.rest.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

import ca.ulaval.glo4002.cafe.api.rest.config.ConfigDTOAssembler;
import ca.ulaval.glo4002.cafe.api.rest.config.ConfigResource;
import ca.ulaval.glo4002.cafe.api.rest.config.dto.ConfigDTO;
import ca.ulaval.glo4002.cafe.application.service.ConfigService;
import ca.ulaval.glo4002.cafe.domain.cafe.CafeConfig;

class ConfigResourceTest {

    private static final String DEFAULT_RESERVATION = "Default";
    private static final String FRANCHISE_NAME = "Les 4-Fee";
    private static final int CUBE_SIZE = 4;
    private static final String CAFE_COUNTRY = "CA";
    private static final String CAFE_PROVINCE = "";
    private static final String CAFE_STATE = "";
    private static final int GROUP_TIP_RATE = 5;
    private ConfigResource configResource;
    private ConfigService configService;
    private ConfigDTOAssembler configDTOAssembler;

    @BeforeEach
    void setup() {
        configService = mock(ConfigService.class);
        configDTOAssembler = mock(ConfigDTOAssembler.class);
        configResource = new ConfigResource(configService, configDTOAssembler);
    }

    @Test
    void givenValidGroupReservationMethod_whenModifyConfig_thenNoErrorIsThrown() {
        ConfigDTO configDTO = new ConfigDTO(DEFAULT_RESERVATION, FRANCHISE_NAME, CUBE_SIZE, CAFE_COUNTRY, CAFE_PROVINCE, CAFE_STATE,
                GROUP_TIP_RATE);

        assertDoesNotThrow(() -> configResource.modifyConfig(configDTO));
    }

    @Test
    void whenModifyConfig_thenConfigDTOAssemblerToFranchiseConfigIsCalled() {
        ConfigDTO configDTO = new ConfigDTO(DEFAULT_RESERVATION, FRANCHISE_NAME, CUBE_SIZE, CAFE_COUNTRY, CAFE_PROVINCE, CAFE_STATE, GROUP_TIP_RATE);

        configResource.modifyConfig(configDTO);

        verify(configDTOAssembler).toFranchiseConfig(configDTO);
    }

    @Test
    void whenModifyConfig_thenConfigServiceUpdates() {
        ConfigDTO configDTO = new ConfigDTO(DEFAULT_RESERVATION, FRANCHISE_NAME, CUBE_SIZE, CAFE_COUNTRY, CAFE_PROVINCE, CAFE_STATE, GROUP_TIP_RATE);
        CafeConfig cafeConfig = mock(CafeConfig.class);
        when(configDTOAssembler.toFranchiseConfig(configDTO)).thenReturn(cafeConfig);

        configResource.modifyConfig(configDTO);

        verify(configService).newConfiguration(cafeConfig);
    }
}
