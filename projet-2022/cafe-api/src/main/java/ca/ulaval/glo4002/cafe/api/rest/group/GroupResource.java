package ca.ulaval.glo4002.cafe.api.rest.group;

import java.util.List;
import ca.ulaval.glo4002.cafe.api.rest.exceptions.InvalidRequestParameterException;
import ca.ulaval.glo4002.cafe.api.rest.group.dto.GroupDTO;
import ca.ulaval.glo4002.cafe.application.service.GroupService;
import ca.ulaval.glo4002.cafe.domain.cafe.exception.InsufficientSeatsException;
import ca.ulaval.glo4002.cafe.domain.group.GroupName;
import ca.ulaval.glo4002.cafe.domain.group.exception.DuplicateGroupNameException;
import ca.ulaval.glo4002.cafe.domain.group.exception.InvalidGroupSizeException;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/reservations")
@Produces(MediaType.APPLICATION_JSON)
public class GroupResource {

    private final GroupService groupService;

    public GroupResource(GroupService groupService) {
        this.groupService = groupService;
    }

    @GET
    public List<GroupDTO> getAllGroups() {
        return GroupAssembler.toGroupDTOs(groupService.getGroups());
    }

    @POST
    public Response addGroup(GroupDTO groupDto) throws InsufficientSeatsException, InvalidGroupSizeException, DuplicateGroupNameException {
        if(groupDto.group_name().isEmpty()) throw new InvalidRequestParameterException();
        groupService.createGroup(new GroupName(groupDto.group_name()), groupDto.group_size());
        return Response.ok().build();
    }

}
