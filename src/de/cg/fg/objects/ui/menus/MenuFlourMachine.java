package de.cg.fg.objects.ui.menus;

import de.cg.cgge.game.Room;
import de.cg.fg.ctrl.InventoryItem;
import de.cg.fg.enums.ItemType;
import de.cg.fg.objects.game.placables.FlourMachine;
import de.cg.fg.objects.game.placables.Machine;

public class MenuFlourMachine extends MenuMachine {
    public MenuFlourMachine(Room room, int x, int y, int w, int h, FlourMachine machine) {
        super(room, x, y, w, h, (Machine) machine);
    }

}
