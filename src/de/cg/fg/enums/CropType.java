package de.cg.fg.enums;

public enum CropType {

    EMPTY(0, 0, 0), WHEAT(30, 5, 3), CORN(20, 7, 5);

    CropType(int cost, int duration, int drop) {
        this.cost = cost;
        this.growthDuration = duration;
        this.drop = drop;
    }

    private int cost = 0;
    private int growthDuration = 0;
    private int drop = 0;

    public int getCost() {
        return cost;
    }

    public int getGrowthDuration() {
        return growthDuration;
    }

    public int getDrop() {
        return drop;
    }
}
