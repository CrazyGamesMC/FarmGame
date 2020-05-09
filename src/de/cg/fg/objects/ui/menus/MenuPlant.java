package de.cg.fg.objects.ui.menus;

import de.cg.cgge.game.GameObject;
import de.cg.cgge.game.Room;
import de.cg.fg.ctrl.*;
import de.cg.fg.enums.CropType;
import de.cg.fg.enums.GameState;
import de.cg.fg.objects.ui.UIButton;
import de.cg.fg.objects.ui.UIMenu;
import de.cg.fg.objects.ui.UINotification;

import java.awt.*;

public class MenuPlant extends UIMenu {

    private UIButton btnWheat;
    private UIButton btnCorn;

    public MenuPlant(Room room, int x, int y, int w, int h) {
        super(room, x, y, w, h);

        btnWheat = new UIButton(room, x+20, y+40, 300, 50, Ressources.fontBtnMainGame, "WHEAT | " + CropType.WHEAT.getCost(), Color.BLACK, Color.GRAY, UIButton.ButtonType.CROP_WHEAT);
        btnCorn = new UIButton(room, x+20, y+100, 300, 50, Ressources.fontBtnMainGame, "CORN | " + CropType.CORN.getCost(), Color.BLACK, Color.GRAY, UIButton.ButtonType.CROP_CORN);
        addUIObject(btnWheat);
        addUIObject(btnCorn);
    }

    public void step() {
        for (GameObject obj : room.getObjectManager().getObjects()) {
            if (obj instanceof UIButton) {
                UIButton btn = (UIButton) obj;
                if (btn.fetchClick()) {

                    int cost = 0;
                    CropType type = CropType.EMPTY;

                    if (btn.getType() == UIButton.ButtonType.CROP_WHEAT) {
                        cost = CropType.WHEAT.getCost();
                        type = CropType.WHEAT;
                    }

                    else if (btn.getType() == UIButton.ButtonType.CROP_CORN) {
                        cost = CropType.CORN.getCost();
                        type = CropType.CORN;
                    }

                    if (Main.gc.handler.money >= cost) {
                        Main.gc.handler.currentlyPlanting = type;
                        die();
                        Main.gc.handler.state = GameState.PLANTING;
                    } else {
                        new UINotification(room, "You do not have enough money!", Color.RED);
                    }

                }
            }
        }
    }
}
