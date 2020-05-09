package de.cg.fg.objects.ui.menus;

import de.cg.cgge.game.GameObject;
import de.cg.cgge.game.Room;
import de.cg.fg.enums.GameState;
import de.cg.fg.ctrl.Main;
import de.cg.fg.enums.PlacableType;
import de.cg.fg.ctrl.Ressources;
import de.cg.fg.objects.ui.*;

import java.awt.*;

public class MenuPlace extends UIMenu {

    private UIButton btnField;
    private UIButton btnFlourMachine;

    public MenuPlace(Room room, int x, int y, int w, int h) {
        super(room, x, y, w, h);

        btnField = new UIButton(room, x+20, y+40, 300, 50, Ressources.fontBtnMainGame, "FIELD | 20G", Color.BLACK, Color.GRAY, UIButton.ButtonType.MENU_PLACE_FIELD);
        addUIObject(btnField);
        btnFlourMachine = new UIButton(room, x+20, y+100, 300, 50, Ressources.fontBtnMainGame, "FLOUR MACHINE | 200G", Color.BLACK, Color.GRAY, UIButton.ButtonType.MENU_PLACE_FLOUR_MACHINE);
        addUIObject(btnFlourMachine);

    }

    public void step() {

        for (UIObject obj : uiObjects) {
            UIButton btn = (UIButton) obj;
            if (btn.fetchClick()) {

                int cost = 0;
                PlacableType type = PlacableType.NONE;

                if (btn.getType() == UIButton.ButtonType.MENU_PLACE_FIELD) {
                    cost = PlacableType.FIELD.getCost();
                    type = PlacableType.FIELD;
                }

                if (btn.getType() == UIButton.ButtonType.MENU_PLACE_FLOUR_MACHINE) {
                    cost = PlacableType.FLOUR_MACHINE.getCost();
                    type = PlacableType.FLOUR_MACHINE;
                }

                if (Main.gc.handler.money >= cost) {
                    Main.gc.handler.currentlyPlacing = type;
                    die();
                    Main.gc.handler.state = GameState.PLACING;
                } else {
                    new UINotification(room, "You do not have enough money!", Color.RED);
                }
            }
        }

    }

}
