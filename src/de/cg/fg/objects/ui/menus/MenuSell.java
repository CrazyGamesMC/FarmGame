package de.cg.fg.objects.ui.menus;

import de.cg.cgge.game.Room;
import de.cg.fg.ctrl.InventoryItem;
import de.cg.fg.ctrl.Main;
import de.cg.fg.ctrl.Ressources;
import de.cg.fg.objects.ui.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;

public class MenuSell extends UIMenu {

    private UILabel lblCurrent;
    private UILabel lblValue;
    private UIButton btnLower;
    private UIButton btnHigher;

    private InventoryItem item;

    private int setPrice = 0;

    public MenuSell(Room room, int x, int y, int w, int h, InventoryItem item) {
        super(room, x, y, w, h);

        this.item = item;
        this.setPrice = item.getValue();

        lblValue = new UILabel(room, x+10, y+h-30, "Recommended: " + item.getValue(), Ressources.fontMouseInfo, Color.WHITE);
        addUIObject(lblValue);

        lblCurrent = new UILabel(room, x+10, y+100, "" + setPrice, Ressources.fontBtnMainGame, Color.WHITE);
        addUIObject(lblCurrent);

        btnLower = new UIButton(room, x+10, y+160, 50, 50, Ressources.fontBtnMainGame, "-", Color.BLACK, Color.GRAY, UIButton.ButtonType.MENU_SELL_REMOVE);
        addUIObject(btnLower);

        btnHigher = new UIButton(room, x+65, y+160, 50, 50, Ressources.fontBtnMainGame, "+", Color.BLACK, Color.GRAY, UIButton.ButtonType.MENU_SELL_ADD);
        addUIObject(btnHigher);
    }

    private boolean keyLockEnter = false;

    @Override
    public void step() {
        for (UIObject obj : uiObjects) {
            if (obj instanceof UIButton) {
                UIButton btn = (UIButton) obj;

                if (btn.fetchClick()) {
                    if (btn.getType() == UIButton.ButtonType.MENU_SELL_ADD) setPrice+=5;
                    if (btn.getType() == UIButton.ButtonType.MENU_SELL_REMOVE) setPrice-=5;
                }
            }
        }

        lblCurrent.setText("" + setPrice);

        if (room.getGameInstance().getDrawer().getWindow().getKeyManger().checkKey(KeyEvent.VK_ENTER)) {
            if (!keyLockEnter) {
                enter();
                keyLockEnter = true;
            }
        } else {
            keyLockEnter = false;
        }
    }

    private void enter() {
        int price = setPrice;
        int value = item.getValue();
        int dif = price-value;
        int ran;

        Random rand = new Random();

        ran = rand.nextInt(100)+1;

        //If someone buys it
        if (ran < 80-dif) {
            Main.gc.handler.inventory.remove(item);
            Main.gc.handler.increaseDayPart();
            Main.gc.handler.money += setPrice;

            new UINotification(room, "Item sold for " + setPrice + "G", Color.GREEN);
            die();
        } else {
            Main.gc.handler.increaseDayPart();

            new UINotification(room, "No customer was found!", Color.RED);
        }


    }


}
