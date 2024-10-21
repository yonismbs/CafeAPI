package ca.ulaval.glo4002.cafe.infrastructure.local;

import java.util.HashMap;
import ca.ulaval.glo4002.cafe.domain.cafe.Cafe;
import ca.ulaval.glo4002.cafe.domain.cafe.CafeId;
import ca.ulaval.glo4002.cafe.domain.cafe.CafeRepository;

public class LocalCafeRepository implements CafeRepository {

    private final HashMap<CafeId, Cafe> cafeMap = new HashMap<>();

    @Override
    public void save(CafeId id, Cafe cafe) {
        cafeMap.put(id, cafe);
    }

    @Override
    public Cafe find(CafeId id) {
        return cafeMap.get(id);
    }

}
