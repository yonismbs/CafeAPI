package ca.ulaval.glo4002.cafe.api.rest.client.dto;

import java.util.List;

public record BillDTO(List<String> orders, float subtotal, float taxes, float tip, float total) {
}
