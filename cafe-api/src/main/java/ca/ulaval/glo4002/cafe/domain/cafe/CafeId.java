package ca.ulaval.glo4002.cafe.domain.cafe;

public record CafeId(String id) {
    public static final CafeId DEFAULT_CAFE_ID = new CafeId("l4f");
}
