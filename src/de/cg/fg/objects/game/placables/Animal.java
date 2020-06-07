package de.cg.fg.objects.game.placables;

import de.cg.cgge.game.Room;
import de.cg.cgge.gui.Sprite;
import de.cg.fg.ctrl.InventoryItem;
import de.cg.fg.ctrl.Main;
import de.cg.fg.enums.ItemType;
import de.cg.fg.objects.game.Placable;
import de.cg.fg.objects.ui.UINotification;

import javax.swing.text.Position;
import java.util.ArrayList;
import java.util.Random;

public class Animal extends Placable {

    private int fx;
    private int fy;
    private int quality = 2;
    private int age = 0;

    private boolean needsFood = false;
    private boolean hasItem = false;
    private boolean sick = false;

    private ItemType eats = ItemType.NONE;
    private ItemType produces = ItemType.NONE;


    public Animal(Room room, Sprite sprite, int fx, int fy, ItemType eats, ItemType produces) {
        super(room, sprite, fx, fy);
        this.fx = fx;
        this.fy = fy;
        this.eats = eats;
        this.produces = produces;
    }

    @Override
    public void increaseDay() {
        System.out.println("Increased pig day");
        moveAnimalAtRandom();

        Random random = new Random();

        /*  Handling food   */
        if (needsFood) {
            quality--;
        } else {
            quality++;
        }
        needsFood = true;

        /*  Sickness    */
        if (sick) quality--;

        /*  Producing items */
        if (produces != ItemType.NONE) {
            hasItem = true;
        }


        /*  Dying   */
        if (quality == 0
                || random.nextInt(30-5)+5 - (sick ? 4 : 0) < age)  {
            die();
        }

        age++;
    }

    public void moveAnimalAtRandom() {
        final var handler = Main.gc.handler;

        /*  MOVING THE ANIMAL   */
        final int maxSize = handler.placables.length;

        Random random = new Random();

        int nx = this.fx;
        int ny = this.fy;

        do {
            nx += (random.nextInt(3)-1);
            ny += (random.nextInt(3)-1);
        } while (!handler.checkIfFieldsAreFree(nx, ny));

        handler.placables[fy][fx] = null;

        fx = nx;
        fy = ny;
        x = fx*32;
        y = fy*32;

        handler.placables[fy][fx] = this;
    }

    public boolean feed() {
        var gh = Main.gc.handler;

        if (!gh.checkItemInInventory(eats, 1, -1)) return false;

        needsFood = false;
        gh.removeFromInventory(eats, 1, -1);

        return true;
    }

    public void butcher() {
        var gh = Main.gc.handler;

        InventoryItem item;

        item = new InventoryItem(ItemType.MEAT, quality);

        gh.inventory.add(item);

        die();
    }

    public void die() {
        var gh = Main.gc.handler;

        gh.placables[fy][fx] = null;

        System.out.println(getClass().getSimpleName() + " died!");

        destroy();
    }

    public int getQuality() {
        return quality;
    }

    public boolean isNeedsFood() {
        return needsFood;
    }

    public boolean isHasItem() {
        return hasItem;
    }

    public ItemType getEats() {
        return eats;
    }

    public ItemType getProduces() {
        return produces;
    }

    public int getAge() {
        return age;
    }

    public boolean isSick() {
        return sick;
    }

    public boolean collectItem() {
        if (!hasItem) return false;

        hasItem = false;

        InventoryItem item = new InventoryItem(produces, quality);

        Main.gc.handler.inventory.add(item);

        return true;

    }
}
