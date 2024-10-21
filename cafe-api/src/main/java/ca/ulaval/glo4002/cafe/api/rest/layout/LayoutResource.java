package ca.ulaval.glo4002.cafe.api.rest.layout;

import ca.ulaval.glo4002.cafe.api.rest.layout.dto.LayoutDTO;
import ca.ulaval.glo4002.cafe.application.service.CafeService;
import ca.ulaval.glo4002.cafe.domain.cafe.CafeId;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class LayoutResource {

    private final LayoutAssembler layoutAssembler = LayoutAssemblerFactory.getFranchiseAssembler();
    private final CafeService cafeService;

    public LayoutResource(CafeService cafeService) {
        this.cafeService = cafeService;
    }

    @Path("layout")
    @GET
    public LayoutDTO franchiseLayout() {
        return layoutAssembler.toLayoutDTO(cafeService.find(CafeId.DEFAULT_CAFE_ID));
    }

}
