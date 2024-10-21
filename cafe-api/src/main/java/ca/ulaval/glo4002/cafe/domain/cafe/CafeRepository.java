package ca.ulaval.glo4002.cafe.domain.cafe;

public interface CafeRepository {

    void save(CafeId id, Cafe cafe);

    Cafe find(CafeId id);
}
