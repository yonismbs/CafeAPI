package ca.ulaval.glo4002.cafe.api.rest.config;

import ca.ulaval.glo4002.cafe.api.rest.config.dto.ConfigDTO;
import ca.ulaval.glo4002.cafe.api.rest.exceptions.InvalidRequestParameterException;
import ca.ulaval.glo4002.cafe.application.service.ConfigService;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/config")
@Produces(MediaType.APPLICATION_JSON)
public class ConfigResource {

    private final ConfigService configService;
    private final ConfigDTOAssembler configDTOAssembler;

    public ConfigResource(ConfigService configService, ConfigDTOAssembler configDTOAssembler) {
        this.configService = configService;
        this.configDTOAssembler = configDTOAssembler;
    }

    public ConfigResource(ConfigService configService) {
        this(configService, new ConfigDTOAssembler());
    }

    @POST
    public Response modifyConfig(ConfigDTO configDTO)  {
        if(configDTO.organization_name().isEmpty()) throw new InvalidRequestParameterException();
        configService.newConfiguration(configDTOAssembler.toFranchiseConfig(configDTO));
        return Response.ok().build();
    }
}
