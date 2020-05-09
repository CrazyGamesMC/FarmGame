package de.cg.fg.ctrl;

import de.cg.fg.enums.ItemType;

public class InventoryItem {

    private ItemType type;
    private int quality = 0;

    public InventoryItem(ItemType type, int quality) {
        this.type = type;
        this.quality = quality;
    }

    public ItemType getType() {
        return type;
    }

    public void setType(ItemType type) {
        this.type = type;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    public int getValue() {
        return type.getBaseValue()*quality;
    }
}
