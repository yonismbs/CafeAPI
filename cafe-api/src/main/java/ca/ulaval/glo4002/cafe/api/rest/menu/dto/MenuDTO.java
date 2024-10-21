package ca.ulaval.glo4002.cafe.api.rest.menu.dto;

import ca.ulaval.glo4002.cafe.api.rest.inventory.dto.InventoryDTO;
import ca.ulaval.glo4002.cafe.api.rest.layout.dto.LayoutDTO;
import ca.ulaval.glo4002.cafe.domain.cafe.Cafe;
import ca.ulaval.glo4002.cafe.domain.product.Ingredients;
import ca.ulaval.glo4002.cafe.domain.product.Product;
import ca.ulaval.glo4002.cafe.domain.product.ProductInfo;

import java.math.BigDecimal;

public record MenuDTO(String name, InventoryDTO ingredients, BigDecimal cost) {

}
