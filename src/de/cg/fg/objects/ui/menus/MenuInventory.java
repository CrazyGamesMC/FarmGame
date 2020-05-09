package de.cg.fg.objects.ui.menus;

import de.cg.cgge.game.GameObject;
import de.cg.cgge.game.Room;
import de.cg.cgge.io.KeyManager;
import de.cg.fg.ctrl.InventoryItem;
import de.cg.fg.ctrl.Main;
import de.cg.fg.ctrl.Ressources;
import de.cg.fg.enums.GameState;
import de.cg.fg.enums.MachineType;
import de.cg.fg.objects.game.placables.FlourMachine;
import de.cg.fg.objects.game.placables.Machine;
import de.cg.fg.objects.ui.UIInventoryButton;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class MenuInventory extends GameObject {

    private Machine machine = null;

    private ArrayList<UIInventoryButton> invButtons = new ArrayList<>();
    private KeyManager keyManager = room.getGameInstance().getDrawer().getWindow().getKeyManger();


    public MenuInventory(Room room) {
        super(room);
        x = room.getGameInstance().getWidth()-350;
        y = 0;
        w = 350;
        h = room.getGameInstance().getHeight();

        Main.gc.handler.state = GameState.INVENTORY_OPENED;

        var inv = Main.gc.handler.inventory;
        for (int i = 0; i<inv.size(); i++) {
            InventoryItem item = inv.get(i);
            invButtons.add(new UIInventoryButton(room, this, 10+i*50, item));
        }

        Ressources.soundInventoryOpen.play();
    }

    public MenuInventory(Room room, Machine machine) {
        this(room);
        this.machine = machine;
    }

    @Override
    public void postDraw(Graphics g) {
        g.setColor(new Color(50, 50, 50, 200));
        g.fillRect((int)x, (int)y, w, h);
    }

    public void die() {
        Ressources.soundInventoryClose.play();

        invButtons.forEach(GameObject::destroy);
        Main.gc.handler.state = GameState.OPEN;
        destroy();
    }

    public void step() {
        if (keyManager.checkKey(KeyEvent.VK_ESCAPE)) {
            die();
        }

        for (UIInventoryButton btn : invButtons) {
            if (btn.fetchClick()) {
                die();

                if (machine != null) {
                    machine.inventory.add(btn.getItem());
                    Main.gc.handler.inventory.remove(btn.getItem());

                    if (machine.type == MachineType.FLOUR) {
                        new MenuFlourMachine(room, 100, 100, 500, 500, (FlourMachine) machine);
                    }
                }

                else {
                    new MenuSell(room, 200, 200, 400, 400, btn.getItem());
                }
            }
        }
    }

}
