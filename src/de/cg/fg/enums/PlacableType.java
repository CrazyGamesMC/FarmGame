package de.cg.fg.enums;

public enum PlacableType {

    NONE(0, 1),
    FIELD(20, 1),
    FLOUR_MACHINE(200, 1),
    PIG(300, 1);

    private int size;
    private int cost;

    PlacableType(int cost, int size) {
        this.cost = cost;
        this.size = size;
    }

    public int getSize() {
        return this.size;
    }

    public int getCost() {
        return this.cost;
    }

}
