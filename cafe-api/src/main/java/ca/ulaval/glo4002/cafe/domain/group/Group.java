package ca.ulaval.glo4002.cafe.domain.group;

import java.util.ArrayList;
import ca.ulaval.glo4002.cafe.domain.client.Client;
import ca.ulaval.glo4002.cafe.domain.group.exception.NoGroupSeatsException;

public class Group {

    private final GroupName name;
    private final ArrayList<Client> visitedClients;
    private final int maxSize;

    public Group(int maxSize, GroupName name) {
        this.name = name;
        this.maxSize = maxSize;
        this.visitedClients = new ArrayList<>();
    }

    public void add(Client client) throws NoGroupSeatsException {
        if (visitedClients.size() >= maxSize) throw new NoGroupSeatsException();
        visitedClients.add(client);
    }

    public ArrayList<Client> getVisitedClients() {
        return visitedClients;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public GroupName getName() {
        return name;
    }
}
