package ca.ulaval.glo4002.cafe.domain.cafe.layout.cube.seat;

import ca.ulaval.glo4002.cafe.domain.client.ClientId;
import ca.ulaval.glo4002.cafe.domain.group.GroupName;

public class Seat {

    private SeatState state;
    private final int number;
    private GroupName groupName;
    private ClientId clientId;

    public Seat(int number) {
        this.state = SeatState.Available;
        this.groupName = GroupName.VOID;
        this.clientId = ClientId.VOID;
        this.number = number;
    }

    public int getNumber() {
        return this.number;
    }

    public ClientId getClientId() {
        return clientId;
    }

    public int assignClientId(ClientId clientId) {
        this.clientId = clientId;
        setState(SeatState.Occupied);
        return number;
    }

    public SeatState getState() {
        return this.state;
    }

    public void setState(SeatState state) {
        this.state = state;
    }

    public GroupName getGroupName() {
        return groupName;
    }

    public void setGroupName(GroupName groupName) {
        this.groupName = groupName;
    }

    public void reset() {
        this.clientId = ClientId.VOID;
        this.groupName = GroupName.VOID;
        this.state = SeatState.Available;
    }

    public boolean isAvailable() {
        return state == SeatState.Available;
    }

    public boolean isReservedForGroup(GroupName groupName) {
        return this.groupName.equals(groupName) && state == SeatState.Reserved;
    }

}
