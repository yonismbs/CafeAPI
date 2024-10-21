package ca.ulaval.glo4002.cafe.api.rest.menu;

import ca.ulaval.glo4002.cafe.api.rest.menu.dto.MenuDTO;
import ca.ulaval.glo4002.cafe.domain.product.Ingredients;
import ca.ulaval.glo4002.cafe.domain.product.Product;
import ca.ulaval.glo4002.cafe.domain.product.ProductInfo;

public class MenuAssembler {

    public static Product fromMenuDTOToProduct(MenuDTO menuDTO) {
        return new Product(menuDTO.name(), new ProductInfo(menuDTO.cost(), new Ingredients(menuDTO.ingredients().Chocolate(), menuDTO.ingredients().Espresso(), menuDTO.ingredients().Milk(), menuDTO.ingredients().Water() )));
    }
}
