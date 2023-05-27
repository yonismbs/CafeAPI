package ca.ulaval.glo4002.cafe.domain.client;

import java.util.ArrayList;
import java.util.List;
import ca.ulaval.glo4002.cafe.domain.billing.order.Order;
import ca.ulaval.glo4002.cafe.domain.group.GroupName;
import ca.ulaval.glo4002.cafe.domain.product.Product;

public class Client {

    private final ClientId id;
    private String name;
    private GroupName groupName;
    private int seatNumber;
    private boolean isCheckedOut;
    private final List<Order> orders;

    public Client(ClientId id, String name) {
        this.id = id;
        this.name = name;
        this.groupName = GroupName.VOID;
        this.isCheckedOut = false;
        this.orders = new ArrayList<>();
    }

    public Client(ClientId id, String name, GroupName groupName) {
        this(id, name);
        this.groupName = groupName;
    }

    public List<Product> getListOfProducts() {
        List<Product> productList = new ArrayList<>();
        for (Order order: orders) {
            productList.addAll(order.getProductList());
        }
        return productList;
    }

    public ClientId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public GroupName getGroupName() {
        return groupName;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public boolean isCheckedOut() {
        return isCheckedOut;
    }

    public void setCheckedOut(boolean checkedOut) {
        isCheckedOut = checkedOut;
    }

    public void addOrder(Order order) {
        this.orders.add(order);
    }

    public List<Order> getOrders() {
        return this.orders;
    }

    public boolean isClientInGroup() {
        return !this.groupName.isAbsent();
    }
}
