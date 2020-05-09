package de.cg.fg.objects.ui;

import de.cg.cgge.game.Room;
import de.cg.fg.ctrl.InventoryItem;
import de.cg.fg.ctrl.Ressources;
import de.cg.fg.objects.ui.menus.MenuInventory;

import java.awt.*;

public class UIInventoryButton extends UIButton {

    private MenuInventory menu;
    private InventoryItem item;

    private int startY;

    public UIInventoryButton(Room room, MenuInventory menu, int y, InventoryItem item) {
        super(room, (int) menu.getX()+10, y,  300, 40, Ressources.fontBtnMainGame,
                item.getType().name() + " || " + item.getQuality() + "Q", Color.BLACK, Color.GRAY, ButtonType.INVENTORY);

        this.menu = menu;
        this.startY = y;
        this.item = item;
    }

    @Override
    public void postDraw(Graphics g) {
        super.postDraw(g);

        int mx = mh.getMouseX();
        int my = mh.getMouseY();

        if (mx > x && mx < x+w && my > y && my < y+h) {
            g.setFont(Ressources.fontMouseInfo);
            g.setColor(Color.GREEN);
            g.drawString("Click to sell",mx+7, my+15+7);
        }
    }

    public InventoryItem getItem() {
        return item;
    }
}
