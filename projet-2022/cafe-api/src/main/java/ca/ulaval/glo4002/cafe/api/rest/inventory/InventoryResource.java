package ca.ulaval.glo4002.cafe.api.rest.inventory;

import ca.ulaval.glo4002.cafe.api.rest.inventory.dto.InventoryDTO;
import ca.ulaval.glo4002.cafe.application.service.CafeService;
import ca.ulaval.glo4002.cafe.domain.product.Ingredients;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/inventory")
@Produces(MediaType.APPLICATION_JSON)
public class InventoryResource {

    private final CafeService cafeService;

    public InventoryResource(CafeService cafeService) {
        this.cafeService = cafeService;
    }

    @PUT
    public Response putInventory(InventoryDTO inventoryDTO) {
        cafeService.addStock(new Ingredients(inventoryDTO.Chocolate(), inventoryDTO.Espresso(), inventoryDTO.Milk(), inventoryDTO.Water()));

        return Response.status(Response.Status.OK).build();
    }

    @GET
    public Response getInventory() {

        return Response.status(Response.Status.OK)
                .entity(InventoryAssembler.toInventoryDTO(cafeService.getInventory())).build();
    }
}