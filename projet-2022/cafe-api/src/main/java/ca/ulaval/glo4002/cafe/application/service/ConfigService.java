package ca.ulaval.glo4002.cafe.application.service;

import ca.ulaval.glo4002.cafe.domain.cafe.CafeConfig;

public class ConfigService {

    private final CheckInService checkInService;
    private final CafeService cafeService;

    public ConfigService(CheckInService checkInService, CafeService cafeService) {
        this.checkInService = checkInService;
        this.cafeService = cafeService;
    }

    public void newConfiguration(CafeConfig cafeConfig) {
        checkInService.close();
        cafeService.regenerateWithNewConfig(cafeConfig);
    }
}
