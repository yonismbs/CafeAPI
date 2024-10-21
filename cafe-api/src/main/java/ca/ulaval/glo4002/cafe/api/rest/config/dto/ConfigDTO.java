package ca.ulaval.glo4002.cafe.api.rest.config.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ConfigDTO(String group_reservation_method, String organization_name, int cube_size,
                        String country, String province, String state, float group_tip_rate) {
}
