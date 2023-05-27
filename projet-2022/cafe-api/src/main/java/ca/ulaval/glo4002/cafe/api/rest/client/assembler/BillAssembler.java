package ca.ulaval.glo4002.cafe.api.rest.client.assembler;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import ca.ulaval.glo4002.cafe.api.rest.client.dto.BillDTO;
import ca.ulaval.glo4002.cafe.domain.billing.Bill;
import ca.ulaval.glo4002.cafe.domain.product.Product;

public class BillAssembler {

    public BillDTO toBillDTO(Bill bill) {
        List<String> listOfProducts = new ArrayList<>();
        for (Product product: bill.listOfProduct()) {
            listOfProducts.add(product.name());
        }

        return new BillDTO(
            listOfProducts,
            bill.subtotal().setScale(2, RoundingMode.UP).floatValue(),
            bill.taxes().setScale(2, RoundingMode.UP).floatValue(),
            bill.tip().setScale(2, RoundingMode.UP).floatValue(),
            bill.total().setScale(2, RoundingMode.UP).floatValue()
        );
    }
}
