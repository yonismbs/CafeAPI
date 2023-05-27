package ca.ulaval.glo4002.cafe.domain.group;

import java.util.List;
import ca.ulaval.glo4002.cafe.domain.group.exception.DuplicateGroupNameException;
import ca.ulaval.glo4002.cafe.domain.group.exception.InvalidGroupSizeException;

public class GroupFactory {

    private final List<GroupName> usedNames;

    public GroupFactory(List<GroupName> usedNames) {
        this.usedNames = usedNames;
    }

    public Group getGroup(GroupName name, int size)
            throws InvalidGroupSizeException, DuplicateGroupNameException {
        if (size < 2) throw new InvalidGroupSizeException();
        if (usedNames.contains(name)) throw new DuplicateGroupNameException();
        return new Group(size, name);
    }

    public void clearGroupNames() {
        usedNames.clear();
    }

    public void confirmGroupName(GroupName name) {
        usedNames.add(name);
    }
}
