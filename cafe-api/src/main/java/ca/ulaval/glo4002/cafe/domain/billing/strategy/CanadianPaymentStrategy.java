package ca.ulaval.glo4002.cafe.domain.billing.strategy;

import java.math.BigDecimal;
import java.util.List;
import ca.ulaval.glo4002.cafe.domain.billing.Bill;
import ca.ulaval.glo4002.cafe.domain.billing.TaxationStrategy;
import ca.ulaval.glo4002.cafe.domain.billing.enums.ProvinceEnum;
import ca.ulaval.glo4002.cafe.domain.product.Product;

public class CanadianPaymentStrategy implements TaxationStrategy {
    private static final BigDecimal FEDERAL_TAX = new BigDecimal("0.05");
    private static final BigDecimal DIVISOR = new BigDecimal(100);
    private final BigDecimal GROUP_TIP_RATE;
    private final ProvinceEnum provinceEnum;
    private TemplateGenerateBill templateGenerateBill;

    public CanadianPaymentStrategy(ProvinceEnum provinceEnum, float groupTipRate) {
        this.provinceEnum = provinceEnum;
        this.GROUP_TIP_RATE = BigDecimal.valueOf(groupTipRate);
        this.templateGenerateBill = new TemplateGenerateBill();
    }
    @Override
    public Bill generateBill(List<Product> listOfOrder, boolean isClientInGroup) {
        Bill bill = this.templateGenerateBill.generateBill(listOfOrder, isClientInGroup, provinceEnum.tax.add(FEDERAL_TAX),
                this.GROUP_TIP_RATE, DIVISOR);
        return bill;
    }
}
