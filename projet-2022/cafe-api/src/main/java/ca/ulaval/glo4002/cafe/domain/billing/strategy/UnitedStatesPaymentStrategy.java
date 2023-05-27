package ca.ulaval.glo4002.cafe.domain.billing.strategy;

import java.math.BigDecimal;
import java.util.List;
import ca.ulaval.glo4002.cafe.domain.billing.Bill;
import ca.ulaval.glo4002.cafe.domain.billing.TaxationStrategy;
import ca.ulaval.glo4002.cafe.domain.billing.enums.StateEnum;
import ca.ulaval.glo4002.cafe.domain.product.Product;

public class UnitedStatesPaymentStrategy implements TaxationStrategy {

    private static final BigDecimal DIVISOR = new BigDecimal(100);
    private final StateEnum stateEnum;
    private final BigDecimal GROUP_TIP_RATE;

    private TemplateGenerateBill templateGenerateBill = new TemplateGenerateBill();

    public UnitedStatesPaymentStrategy(StateEnum stateEnum, float groupTipRate) {
        this.stateEnum = stateEnum;
        this.GROUP_TIP_RATE = BigDecimal.valueOf(groupTipRate);
    }

    @Override
    public Bill generateBill(List<Product> listOfOrder, boolean isClientInGroup) {
        Bill bill = this.templateGenerateBill.generateBill(listOfOrder, isClientInGroup, stateEnum.tax,
                GROUP_TIP_RATE, DIVISOR );

        return bill;

    }
}
