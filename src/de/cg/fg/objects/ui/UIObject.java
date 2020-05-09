package de.cg.fg.objects.ui;

import de.cg.cgge.game.GameObject;
import de.cg.cgge.game.Room;

public class UIObject extends GameObject {

    public UIObject(Room room, int x, int y) {
        super(room);

        this.x = x;
        this.y = y;
    }

}
