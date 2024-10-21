package ca.ulaval.glo4002.cafe.unit.domain.cafe.layout;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import ca.ulaval.glo4002.cafe.domain.cafe.layout.LayoutFactory;

class LayoutFactoryTest {

    private static final int CUBE_SIZE = 4;
    private static final int DEFAULT_NB_OF_CUBE = 4;
    LayoutFactory layoutFactory;

    @BeforeEach
    public void setup() {
        layoutFactory = new LayoutFactory();
    }

    @Test
    void whenGetLayout_thenNewFranchiseIsNotNull() {
        assertNotNull(layoutFactory.getLayout(CUBE_SIZE));
    }

    @Test
    void givenCubeSize_whenGetLayout_thenLayoutHasSameCubeSize() {
        assertEquals(CUBE_SIZE * DEFAULT_NB_OF_CUBE, layoutFactory.getLayout(CUBE_SIZE).getMaxCapacity());
    }
}