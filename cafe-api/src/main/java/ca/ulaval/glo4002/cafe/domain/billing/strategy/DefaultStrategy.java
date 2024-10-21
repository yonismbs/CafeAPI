package ca.ulaval.glo4002.cafe.domain.billing.strategy;

import java.math.BigDecimal;
import java.util.List;
import ca.ulaval.glo4002.cafe.domain.billing.Bill;
import ca.ulaval.glo4002.cafe.domain.billing.TaxationStrategy;
import ca.ulaval.glo4002.cafe.domain.product.Product;

public class DefaultStrategy implements TaxationStrategy {

    private static final BigDecimal DIVISOR = new BigDecimal(100);
    private final BigDecimal GROUP_TIP_RATE;
    private TemplateGenerateBill templateGenerateBill = new TemplateGenerateBill();

    public DefaultStrategy(float groupTipRate) {
        this.GROUP_TIP_RATE = BigDecimal.valueOf(groupTipRate);
    }

    @Override
    public Bill generateBill(List<Product> listOfOrder, boolean isClientInGroup) {
        Bill bill = templateGenerateBill.generateBill(listOfOrder, isClientInGroup,
                BigDecimal.ZERO, GROUP_TIP_RATE, DIVISOR);

        return bill;
    }
}
