package ca.ulaval.glo4002.cafe.domain.billing.enums;

import java.math.BigDecimal;

public enum StateEnum {
    AL(new BigDecimal("0.04")),
    AZ(new BigDecimal("0.056")),
    CA(new BigDecimal("0.0725")),
    FL(new BigDecimal("0.06")),
    ME(new BigDecimal("0.0550")),
    NY(new BigDecimal("0.04")),
    TX(new BigDecimal("0.0625"));

    public final BigDecimal tax;
    StateEnum(BigDecimal tax) {
        this.tax = tax;
    }
}
