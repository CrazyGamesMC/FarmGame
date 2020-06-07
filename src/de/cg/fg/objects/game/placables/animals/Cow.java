package de.cg.fg.objects.game.placables.animals;

import de.cg.cgge.game.Room;
import de.cg.cgge.gui.Sprite;
import de.cg.fg.enums.ItemType;
import de.cg.fg.objects.game.placables.Animal;

public class Cow extends Animal {
    public Cow(Room room, Sprite sprite, int fx, int fy) {
        super(room, sprite, fx, fy, ItemType.WHEAT, ItemType.MILK);
    }
}
