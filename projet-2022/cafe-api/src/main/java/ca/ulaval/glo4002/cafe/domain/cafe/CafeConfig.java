package ca.ulaval.glo4002.cafe.domain.cafe;

import ca.ulaval.glo4002.cafe.domain.billing.TaxationStrategy;
import ca.ulaval.glo4002.cafe.domain.cafe.reservation.strategy.ReservationStrategy;

public record CafeConfig(String name, ReservationStrategy reservationStrategy, int cubeSize ,
                         TaxationStrategy taxationStrategy, String country, String province, String state) {
}
