package ca.ulaval.glo4002.cafe.domain.client;

public record ClientId(String id) {
    public static final ClientId VOID = new ClientId(null);

    public boolean isVoid() {
        return this == VOID;
    }
}
