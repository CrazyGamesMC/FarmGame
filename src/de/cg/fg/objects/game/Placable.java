package de.cg.fg.objects.game;

import de.cg.cgge.game.CameraRenderer;
import de.cg.cgge.game.GameObject;
import de.cg.cgge.game.Room;
import de.cg.cgge.gui.Sprite;

import java.awt.*;

public class Placable extends GameObject{

    protected Sprite sprite;

    public Placable(Room room, Sprite sprite, int fx, int fy) {
        super(room);
        this.sprite = sprite;

        this.x = fx*32;
        this.y = fy*32;
    }

    @Override
    public void draw(Graphics g) {
        CameraRenderer cr = new CameraRenderer(g, room.getCamera());

        cr.drawSprite(sprite,(int) x,(int) y);
    }

    public void increaseDay() {

    }



}
