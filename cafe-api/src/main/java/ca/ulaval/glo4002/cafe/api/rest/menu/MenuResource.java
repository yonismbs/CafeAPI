package ca.ulaval.glo4002.cafe.api.rest.menu;


import ca.ulaval.glo4002.cafe.api.rest.menu.dto.MenuDTO;
import ca.ulaval.glo4002.cafe.application.service.ProductService;
import ca.ulaval.glo4002.cafe.domain.product.Ingredients;
import ca.ulaval.glo4002.cafe.domain.product.Product;
import ca.ulaval.glo4002.cafe.domain.product.ProductInfo;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/menu")
@Produces(MediaType.APPLICATION_JSON)
public class MenuResource {

    ProductService productService;

    public MenuResource(ProductService productService) {
        this.productService = productService;
    }

    @POST
    public Response postMenu(MenuDTO menuDTO) {
        productService.addMenu(MenuAssembler.fromMenuDTOToProduct(menuDTO));
        return Response.status(Response.Status.OK).build();
    }
}
