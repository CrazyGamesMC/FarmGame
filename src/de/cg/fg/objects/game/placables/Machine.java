package de.cg.fg.objects.game.placables;

import de.cg.cgge.game.Room;
import de.cg.cgge.gui.Sprite;
import de.cg.fg.ctrl.InventoryItem;
import de.cg.fg.ctrl.Main;
import de.cg.fg.ctrl.Ressources;
import de.cg.fg.enums.ItemType;
import de.cg.fg.enums.MachineType;
import de.cg.fg.objects.game.Placable;
import de.cg.fg.objects.ui.UINotification;

import java.awt.*;
import java.util.ArrayList;

public class Machine extends Placable {

    public ArrayList<InventoryItem> inventory = new ArrayList<>();
    public ArrayList<InventoryItem> neededItems = new ArrayList<>();

    public ItemType reward = ItemType.NONE;

    public MachineType type = MachineType.NONE;

    public boolean autoFetch = false;

    public Machine(Room room, Sprite sprite, int fx, int fy) {
        super(room, sprite, fx, fy);
    }

    public boolean produce() {

        int quality;
        boolean hasAllItems = hasAllItems();

        if (hasAllItems) {
            quality  = 1000; //Pseudo number, so that the first quality level is guaranteed lower

            for (InventoryItem needed : neededItems) {
                for (int i = 0; i < inventory.size(); i++) {
                    InventoryItem item = inventory.get(i);
                    if (needed.getType() == item.getType()) {
                        int itemQuality = item.getQuality();
                        if (itemQuality < quality) {
                            quality = itemQuality;
                        }
                        inventory.remove(item);
                        break;
                    }
                }
            }

            InventoryItem itemReward = new InventoryItem(reward, quality);
            Main.gc.handler.inventory.add(itemReward);

            return true;
        }


        return false;
    }

    public boolean hasAllItems() {

        if (reward == ItemType.NONE) return false;

        for (InventoryItem needed : neededItems) {
            boolean foundItem = false;
            for (int i = 0; i < inventory.size(); i++) {
                InventoryItem item = inventory.get(i);
                if (needed.getType() == item.getType()) {
                    foundItem = true;
                }
            }
            if (foundItem) continue;

            return false; //If loop gets to finish, at least one item is missing
        }

        return true;
    }

    @Override
    public void increaseDay() {
        boolean produced = produce();

        if (produced) {
            new UINotification(room, "Machine produced one " + reward.name(), Color.GREEN);
        }

        if (autoFetch)
        {
            for (InventoryItem needed : neededItems)
            {
                for (InventoryItem item : Main.gc.handler.inventory)
                {
                    if (item.getType() == needed.getType())
                    {
                        Main.gc.handler.inventory.remove(item);
                        inventory.add(item);
                        new UINotification(room, "Item fetched: " + item.getType().name(), Color.GREEN);
                        break;
                    }
                }
            }
        }
    }



}
