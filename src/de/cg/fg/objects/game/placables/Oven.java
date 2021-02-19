package de.cg.fg.objects.game.placables;

import de.cg.cgge.game.Room;
import de.cg.cgge.gui.Sprite;
import de.cg.fg.ctrl.InventoryItem;
import de.cg.fg.ctrl.Ressources;
import de.cg.fg.enums.ItemType;
import de.cg.fg.enums.MachineType;

public class Oven extends Machine{
    public Oven(Room room, int fx, int fy) {
        super(room, Ressources.spriteOven, fx, fy);

        this.neededItems.add(new InventoryItem(ItemType.FLOUR, 0));

        this.reward = ItemType.BREAD;
        this.type = MachineType.OVEN;
    }
}
