package de.cg.fg.objects.game.placables.animals;

import de.cg.cgge.game.Room;
import de.cg.cgge.gui.Sprite;
import de.cg.fg.ctrl.Ressources;
import de.cg.fg.enums.ItemType;
import de.cg.fg.objects.game.placables.Animal;

public class Pig extends Animal {
    public Pig(Room room, int fx, int fy) {
        super(room, Ressources.spritePig, fx, fy, ItemType.CORN, ItemType.NONE);
    }
}
