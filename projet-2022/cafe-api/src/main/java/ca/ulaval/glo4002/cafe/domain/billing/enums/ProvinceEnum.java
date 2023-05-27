package ca.ulaval.glo4002.cafe.domain.billing.enums;

import java.math.BigDecimal;

public enum ProvinceEnum {

    AB(new BigDecimal("0.00")),
    BC(new BigDecimal("0.07")),
    MB(new BigDecimal("0.07")),
    NB(new BigDecimal("0.10")),
    NL(new BigDecimal("0.10")),
    NT(new BigDecimal("0.00")),
    NS(new BigDecimal("0.10")),
    NU(new BigDecimal("0.00")),
    ON(new BigDecimal("0.08")),
    PE(new BigDecimal("0.10")),
    QC(new BigDecimal("0.09975")),
    SK(new BigDecimal("0.06")),
    YT(new BigDecimal("0.00"));

    public final BigDecimal tax;
    ProvinceEnum(BigDecimal tax) {
        this.tax = tax;
    }
}
