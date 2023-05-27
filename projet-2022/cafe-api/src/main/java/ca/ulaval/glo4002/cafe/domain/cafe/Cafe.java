package ca.ulaval.glo4002.cafe.domain.cafe;

import ca.ulaval.glo4002.cafe.domain.billing.TaxationStrategy;
import ca.ulaval.glo4002.cafe.domain.cafe.exception.InsufficientSeatsException;
import ca.ulaval.glo4002.cafe.domain.cafe.inventory.Inventory;
import ca.ulaval.glo4002.cafe.domain.cafe.layout.Layout;
import ca.ulaval.glo4002.cafe.domain.cafe.reservation.strategy.ReservationStrategy;
import ca.ulaval.glo4002.cafe.domain.client.ClientId;
import ca.ulaval.glo4002.cafe.domain.client.exception.InvalidClientIdException;
import ca.ulaval.glo4002.cafe.domain.group.GroupName;
import ca.ulaval.glo4002.cafe.domain.group.exception.NoGroupSeatsException;


public class Cafe {

    private final CafeId id;
    private final Layout layout;
    private final CafeConfig cafeConfig;
    private final Inventory inventory;

    public Cafe(CafeId id, Layout layout, CafeConfig cafeConfig) {
        this.id = id;
        this.layout = layout;
        this.cafeConfig = cafeConfig;
        this.inventory = new Inventory();
    }

    public String getName() {
        return this.cafeConfig.name();
    }

    public Layout getLayout() {
        return this.layout;
    }

    public CafeId getId() {
        return id;
    }

    public int assignClientIdToSeatFromGroup(ClientId clientId, GroupName groupName) throws NoGroupSeatsException {
        return layout.assignClientIdToSeatFromGroup(clientId, groupName);
    }

    public int assignClientIdToSeat(ClientId clientId) throws InsufficientSeatsException {
        return layout.assignClientIdToSeat(clientId);
    }

    public void reserveSeats(int seatCount, GroupName groupName) throws InsufficientSeatsException {
        layout.reserveSeats(seatCount, groupName, cafeConfig.reservationStrategy());
    }

    public void resetAllSeats() {
        layout.resetAllSeats();
    }

    public ReservationStrategy getReservationStrategy() {
        return cafeConfig.reservationStrategy();
    }

    public TaxationStrategy getBillStrategy() {
        return cafeConfig.taxationStrategy();
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    public void disbandGroupIfEmpty(GroupName groupName) {
        layout.disbandGroupIfEmpty(groupName);
    }

    public void resetSeatByClientId(ClientId clientId) throws InvalidClientIdException {
        layout.resetSeatByClientId(clientId);
    }
}
