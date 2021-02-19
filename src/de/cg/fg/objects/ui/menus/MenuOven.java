package de.cg.fg.objects.ui.menus;

import de.cg.cgge.game.Room;
import de.cg.fg.objects.game.placables.Machine;
import de.cg.fg.objects.game.placables.Oven;

public class MenuOven extends MenuMachine{
    public MenuOven(Room room, int x, int y, int w, int h, Oven machine) {
        super(room, x, y, w, h, machine);
    }
}
