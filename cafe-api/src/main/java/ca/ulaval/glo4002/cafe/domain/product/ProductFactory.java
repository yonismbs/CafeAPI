package ca.ulaval.glo4002.cafe.domain.product;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import ca.ulaval.glo4002.cafe.domain.product.exception.InvalidMenuOrderException;

public class ProductFactory {
    private HashMap<String, ProductInfo> hashMapProductInfo = populateList();

    private HashMap<String, ProductInfo> populateList() {
        HashMap<String, ProductInfo> hashMapProductInfo = new HashMap<>();
        hashMapProductInfo.put("Americano", new ProductInfo(new BigDecimal("2.25"), new Ingredients(0, 50, 0, 50)));
        hashMapProductInfo.put("Dark Roast", new ProductInfo(new BigDecimal("2.10"), new Ingredients(10, 40, 10, 40)));
        hashMapProductInfo.put("Cappuccino", new ProductInfo(new BigDecimal("3.29"),  new Ingredients(0, 50, 10, 40)));
        hashMapProductInfo.put("Espresso", new ProductInfo(new BigDecimal("2.95"),  new Ingredients(0, 60, 0, 0)));
        hashMapProductInfo.put("Flat White", new ProductInfo(new BigDecimal("3.75"),  new Ingredients(0, 50, 50, 0)));
        hashMapProductInfo.put("Latte", new ProductInfo(new BigDecimal("2.95"),  new Ingredients(0, 50, 50, 0)));
        hashMapProductInfo.put("Macchiato", new ProductInfo(new BigDecimal("4.75"),  new Ingredients(0, 80, 20, 0)));
        hashMapProductInfo.put("Mocha", new ProductInfo(new BigDecimal("4.15"),  new Ingredients(10, 50, 40, 0)));
        return hashMapProductInfo;
    }

    public HashMap<String, ProductInfo> getMap() {
        return this.hashMapProductInfo;
    }

    public List<Product> createAll(List<String> incomingProducts) throws InvalidMenuOrderException {
        List<Product> createdProducts = new ArrayList<>();
        for (String productName : incomingProducts) {
            createdProducts.add(create(productName));
        }
        return createdProducts;
    }

    public Product create(String name) throws InvalidMenuOrderException {
        if (!hashMapProductInfo.containsKey(name)) throw new InvalidMenuOrderException();
        return new Product(name, hashMapProductInfo.get(name));
    }

    public void addProduct(Product product) {
        this.hashMapProductInfo.put(product.name(), product.productInfo() );
    }

    public void resetMap() {
        this.hashMapProductInfo = populateList();
    }

}
