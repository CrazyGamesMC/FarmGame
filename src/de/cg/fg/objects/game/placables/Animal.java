package de.cg.fg.objects.game.placables;

import de.cg.cgge.game.Room;
import de.cg.cgge.gui.Sprite;
import de.cg.fg.ctrl.Main;
import de.cg.fg.objects.game.Placable;

import javax.swing.text.Position;
import java.util.Random;

public class Animal extends Placable {

    private int fx;
    private int fy;

    public Animal(Room room, Sprite sprite, int fx, int fy) {
        super(room, sprite, fx, fy);
        this.fx = fx;
        this.fy = fy;
    }

    @Override
    public void increaseDay() {
        moveAnimalAtRandom();
    }

    public void moveAnimalAtRandom() {
        final var handler = Main.gc.handler;

        final int maxSize = handler.placables.length;

        Random random = new Random();

        int nx = this.fx;
        int ny = this.fy;

        do {
            nx += (random.nextInt(2)-1);
            ny += (random.nextInt(2)-1);
        } while (handler.checkIfFieldsAreFree(nx, ny));

        handler.placables[fy][fx] = null;

        fx = nx;
        fy = ny;

        handler.placables[fy][fx] = this;
    }
}
