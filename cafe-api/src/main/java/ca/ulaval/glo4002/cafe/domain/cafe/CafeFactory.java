package ca.ulaval.glo4002.cafe.domain.cafe;

import ca.ulaval.glo4002.cafe.domain.cafe.layout.Layout;

public class CafeFactory {

    public Cafe getCafe(CafeConfig cafeConfig, Layout layout) {
        return new Cafe(CafeId.DEFAULT_CAFE_ID, layout, cafeConfig);
    }

}
