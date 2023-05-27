package ca.ulaval.glo4002.cafe.api.rest.group;

import java.util.List;
import ca.ulaval.glo4002.cafe.api.rest.group.dto.GroupDTO;
import ca.ulaval.glo4002.cafe.domain.group.Group;

public class GroupAssembler {

    private static GroupDTO toGroupDTO(Group group) {
        return new GroupDTO(group.getName().stringRepresentation(), group.getMaxSize());
    }

    public static List<GroupDTO> toGroupDTOs(List<Group> groups) {
        return groups.stream().map(GroupAssembler::toGroupDTO).toList();
    }

}
