package ca.ulaval.glo4002.cafe.application.service;

import ca.ulaval.glo4002.cafe.domain.cafe.Cafe;
import ca.ulaval.glo4002.cafe.domain.cafe.CafeConfig;
import ca.ulaval.glo4002.cafe.domain.cafe.CafeFactory;
import ca.ulaval.glo4002.cafe.domain.cafe.CafeId;
import ca.ulaval.glo4002.cafe.domain.cafe.CafeRepository;
import ca.ulaval.glo4002.cafe.domain.cafe.exception.InsufficientSeatsException;
import ca.ulaval.glo4002.cafe.domain.cafe.inventory.Inventory;
import ca.ulaval.glo4002.cafe.domain.cafe.inventory.exception.InsufficientIngredientException;
import ca.ulaval.glo4002.cafe.domain.cafe.layout.LayoutFactory;
import ca.ulaval.glo4002.cafe.domain.client.ClientId;
import ca.ulaval.glo4002.cafe.domain.client.exception.InvalidClientIdException;
import ca.ulaval.glo4002.cafe.domain.group.GroupName;
import ca.ulaval.glo4002.cafe.domain.group.exception.NoGroupSeatsException;
import ca.ulaval.glo4002.cafe.domain.product.Ingredients;

public class CafeService {

    private final CafeRepository cafeRepository;
    private final CafeFactory cafeFactory;
    private final LayoutFactory layoutFactory;
    private final ProductService productService;

    public CafeService(CafeRepository cafeRepository, CafeFactory cafeFactory,
                       LayoutFactory layoutFactory, ProductService productService) {
        this.cafeRepository = cafeRepository;
        this.cafeFactory = cafeFactory;
        this.layoutFactory = layoutFactory;
        this.productService = productService;
    }

    public Cafe find(CafeId id) {
        return cafeRepository.find(id);
    }

    public Inventory getInventory() {
        return this.cafeRepository.find(CafeId.DEFAULT_CAFE_ID).getInventory();
    }

    public int assignClientIdToSeat(ClientId clientId, GroupName groupName) throws InsufficientSeatsException, NoGroupSeatsException {
        Cafe cafe = cafeRepository.find(CafeId.DEFAULT_CAFE_ID);
        if (!groupName.isAbsent()) {
            return cafe.assignClientIdToSeatFromGroup(clientId, groupName);
        }
        return cafe.assignClientIdToSeat(clientId);
    }

    public void reserveSeats(int seatCount, GroupName groupName) throws InsufficientSeatsException {
        cafeRepository.find(CafeId.DEFAULT_CAFE_ID).reserveSeats(seatCount, groupName);
    }

    public void resetSeatByClientId(ClientId clientId) throws InvalidClientIdException {
        Cafe cafe = cafeRepository.find(CafeId.DEFAULT_CAFE_ID);
        cafe.resetSeatByClientId(clientId);
        cafeRepository.save(cafe.getId(), cafe);
    }

    public void regenerateWithNewConfig(CafeConfig cafeConfig) {
        regenerateWithNewConfigRef(cafeConfig);
    }

    public void removeStock(Ingredients ingredientCost) throws InsufficientIngredientException {
        getInventory().removeStock(ingredientCost);
    }

    public void addStock(Ingredients ingredients) {
        getInventory().addStock(ingredients);
    }

    public void regenerateWithNewConfigRef(CafeConfig cafeConfig) {
        Cafe cafe = cafeFactory.getCafe(cafeConfig, layoutFactory.getLayout(cafeConfig.cubeSize()));
        cafeRepository.save(cafe.getId(), cafe);
    }

    public void disbandGroupIfEmpty(GroupName groupName) {
        Cafe cafe = cafeRepository.find(CafeId.DEFAULT_CAFE_ID);
        cafe.disbandGroupIfEmpty(groupName);
        cafeRepository.save(cafe.getId(), cafe);
    }

    public void close() {
        layoutFactory.reset();
        Cafe cafe = cafeRepository.find(CafeId.DEFAULT_CAFE_ID);
        cafe.resetAllSeats();
        cafe.getInventory().resetStock();
        productService.resetProducts();
        cafeRepository.save(cafe.getId(), cafe);
    }
}
