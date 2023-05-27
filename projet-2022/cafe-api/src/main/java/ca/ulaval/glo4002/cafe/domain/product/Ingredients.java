package ca.ulaval.glo4002.cafe.domain.product;

public record Ingredients(int chocolateCount, int espressoCount, int milkCount, int waterCount) {

    public Ingredients() {
        this(0, 0, 0, 0);
    }
    public Ingredients subtract(Ingredients ingredients) {
        return new Ingredients(
            chocolateCount - ingredients.chocolateCount,
            espressoCount - ingredients.espressoCount,
            milkCount - ingredients.milkCount,
            waterCount - ingredients.waterCount);
    }

    public Ingredients add(Ingredients ingredients) {
        return new Ingredients(
            chocolateCount + ingredients.chocolateCount,
            espressoCount + ingredients.espressoCount,
            milkCount + ingredients.milkCount,
            waterCount + ingredients.waterCount);
    }

    public boolean anyIngredientLessThan(Ingredients ingredients) {
        return chocolateCount < ingredients.chocolateCount ||
            espressoCount < ingredients.espressoCount ||
            milkCount < ingredients.milkCount ||
            waterCount < ingredients.waterCount;
    }

}
