package ca.ulaval.glo4002.cafe.domain.cafe.layout;

import ca.ulaval.glo4002.cafe.domain.cafe.layout.cube.CubeFactory;

public class LayoutFactory {

    private final CubeFactory cubeFactory;

    public LayoutFactory(CubeFactory cubeFactory) {
        this.cubeFactory = cubeFactory;
    }

    public LayoutFactory() {
        this(new CubeFactory());
    }

    public Layout getLayout(int cubeSize) {
        return new Layout(cubeFactory.getCubes(cubeSize));
    }

    public void reset() {
        cubeFactory.reset();
    }
}
