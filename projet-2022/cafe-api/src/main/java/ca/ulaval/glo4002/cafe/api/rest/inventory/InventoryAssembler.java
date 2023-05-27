package ca.ulaval.glo4002.cafe.api.rest.inventory;

import ca.ulaval.glo4002.cafe.api.rest.inventory.dto.InventoryDTO;
import ca.ulaval.glo4002.cafe.domain.cafe.inventory.Inventory;

public class InventoryAssembler {
    public static InventoryDTO toInventoryDTO(Inventory inventory) {
        return new InventoryDTO(inventory.getResourceStock().chocolateCount(),
            inventory.getResourceStock().espressoCount(),
            inventory.getResourceStock().milkCount(),
            inventory.getResourceStock().waterCount());
    }
}