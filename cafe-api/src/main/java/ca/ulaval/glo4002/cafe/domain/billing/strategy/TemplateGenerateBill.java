package ca.ulaval.glo4002.cafe.domain.billing.strategy;

import ca.ulaval.glo4002.cafe.domain.billing.Bill;
import ca.ulaval.glo4002.cafe.domain.product.Product;

import java.math.BigDecimal;
import java.util.List;

public class TemplateGenerateBill {

    public Bill generateBill(List<Product> listOfOrder, boolean isClientInGroup, BigDecimal mytaxe,
                             BigDecimal groupTipRate, BigDecimal divisor){
        BigDecimal subTotal = BigDecimal.ZERO;
        for (Product product : listOfOrder) {
            subTotal = subTotal.add(product.getPrice());
        }
        BigDecimal taxes = subTotal.multiply(mytaxe);
        BigDecimal tip;
        if (isClientInGroup) {
            tip = subTotal.multiply((groupTipRate).divide(divisor));
        } else {
            tip = BigDecimal.ZERO;
        }
        BigDecimal total = subTotal.add(taxes).add(tip);
        return new Bill(listOfOrder,
                subTotal,
                taxes,
                tip,
                total);
    }
}
