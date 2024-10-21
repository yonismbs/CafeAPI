package ca.ulaval.glo4002.cafe.api.rest.config;

import ca.ulaval.glo4002.cafe.api.rest.config.dto.ConfigDTO;
import ca.ulaval.glo4002.cafe.api.rest.exceptions.InvalidCountryException;
import ca.ulaval.glo4002.cafe.api.rest.exceptions.InvalidGroupReservationMethodException;
import ca.ulaval.glo4002.cafe.api.rest.exceptions.InvalidGroupTipRateException;
import ca.ulaval.glo4002.cafe.api.rest.exceptions.InvalidStateOrProvinceException;
import ca.ulaval.glo4002.cafe.domain.billing.TaxationStrategy;
import ca.ulaval.glo4002.cafe.domain.billing.enums.ProvinceEnum;
import ca.ulaval.glo4002.cafe.domain.billing.enums.StateEnum;
import ca.ulaval.glo4002.cafe.domain.billing.strategy.CanadianPaymentStrategy;
import ca.ulaval.glo4002.cafe.domain.billing.strategy.ChiliPaymentStrategy;
import ca.ulaval.glo4002.cafe.domain.billing.strategy.DefaultStrategy;
import ca.ulaval.glo4002.cafe.domain.billing.strategy.UnitedStatesPaymentStrategy;
import ca.ulaval.glo4002.cafe.domain.cafe.CafeConfig;
import ca.ulaval.glo4002.cafe.domain.cafe.reservation.strategy.DefaultReservationStrategy;
import ca.ulaval.glo4002.cafe.domain.cafe.reservation.strategy.FullCubesReservationStrategy;
import ca.ulaval.glo4002.cafe.domain.cafe.reservation.strategy.NoLonersReservationStrategy;
import ca.ulaval.glo4002.cafe.domain.cafe.reservation.strategy.ReservationStrategy;

public class ConfigDTOAssembler {

    private static final float MIN_TIP_RATE_FOR_GROUP = 0;
    private static final float MAX_TIP_RATE_FOR_GROUP = 100;

    public CafeConfig toFranchiseConfig(ConfigDTO configDTO) {
        validateGroupTipRate(configDTO.group_tip_rate());
        return new CafeConfig(configDTO.organization_name(),
                reservationMethodeToEnum(configDTO.group_reservation_method()), configDTO.cube_size(),
            generateStrategy(configDTO), configDTO.country(), configDTO.province(), configDTO.state());
    }

    private void validateGroupTipRate(float group_tip_rate) {
        if(group_tip_rate < MIN_TIP_RATE_FOR_GROUP || group_tip_rate > MAX_TIP_RATE_FOR_GROUP) {
            throw new InvalidGroupTipRateException();
        }
    }

    private ReservationStrategy reservationMethodeToEnum(String group_reservation_method) throws InvalidGroupReservationMethodException {
        return switch (group_reservation_method) {
            case "Default" -> new DefaultReservationStrategy();
            case "Full Cubes" -> new FullCubesReservationStrategy();
            case "No Loners" -> new NoLonersReservationStrategy();
            default -> throw new InvalidGroupReservationMethodException();
        };
    }

    private TaxationStrategy generateStrategy(ConfigDTO configDTO) {
        try {
            return switch (configDTO.country()) {
                case "CA" -> new CanadianPaymentStrategy(ProvinceEnum.valueOf(configDTO.province()), configDTO.group_tip_rate());
                case "US" -> new UnitedStatesPaymentStrategy(StateEnum.valueOf(configDTO.state()), configDTO.group_tip_rate());
                case "CL" -> new ChiliPaymentStrategy(configDTO.group_tip_rate());
                case "None" -> new DefaultStrategy(configDTO.group_tip_rate());
                default -> throw new InvalidCountryException();
            };
        } catch (IllegalArgumentException e) {
            throw new InvalidStateOrProvinceException();
        }
    }

}
