package de.cg.fg.objects.game.placables;

import de.cg.cgge.game.Room;
import de.cg.cgge.gui.Sprite;
import de.cg.fg.ctrl.InventoryItem;
import de.cg.fg.ctrl.Ressources;
import de.cg.fg.enums.ItemType;
import de.cg.fg.enums.MachineType;

public class FlourMachine extends Machine {
    public FlourMachine(Room room, int fx, int fy) {
        super(room, Ressources.spriteFlourMachine, fx, fy);

        neededItems.add(new InventoryItem(ItemType.WHEAT, 0));

        this.type = MachineType.FLOUR;
        this.reward = ItemType.FLOUR;
    }
}
