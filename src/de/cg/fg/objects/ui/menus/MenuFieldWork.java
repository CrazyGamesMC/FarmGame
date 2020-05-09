package de.cg.fg.objects.ui.menus;

import de.cg.cgge.game.GameObject;
import de.cg.cgge.game.Room;
import de.cg.fg.ctrl.Main;
import de.cg.fg.ctrl.Ressources;
import de.cg.fg.enums.GameState;
import de.cg.fg.objects.ui.UIButton;
import de.cg.fg.objects.ui.UIMenu;

import java.awt.*;

public class MenuFieldWork extends UIMenu {

    private UIButton btnPlant;
    private UIButton btnHarvest;
    private UIButton btnWater;

    public MenuFieldWork(Room room, int x, int y, int w, int h) {
        super(room, x, y, w, h);

        btnPlant = new UIButton(room, x+20, y+40, 300, 50, Ressources.fontBtnMainGame, "PLANT", Color.BLACK, Color.GRAY, UIButton.ButtonType.MENU_FIELD_PLANT);
        addUIObject(btnPlant);
        btnHarvest = new UIButton(room, x+20, y+100, 300, 50, Ressources.fontBtnMainGame, "HARVEST", Color.BLACK, Color.GRAY, UIButton.ButtonType.MENU_FIELD_HARVEST);
        addUIObject(btnHarvest);
        btnWater = new UIButton(room, x+20, y+160, 300, 50, Ressources.fontBtnMainGame, "WATER", Color.BLACK, Color.GRAY, UIButton.ButtonType.MENU_FIELD_WATER);
        addUIObject(btnWater);

    }

    @Override
    public void step() {
        for (GameObject obj : room.getObjectManager().getObjects()) {
            if (obj instanceof UIButton) {
                UIButton btn = (UIButton) obj;
                if (btn.fetchClick()) {

                    if (btn.getType() == UIButton.ButtonType.MENU_FIELD_PLANT) {
                        die();
                        new MenuPlant(room, 200, 200, 500, 500);
                    }

                    if (btn.getType() == UIButton.ButtonType.MENU_FIELD_HARVEST) {
                        die();
                        Main.gc.handler.state = GameState.HARVESTING;
                    }

                    if (btn.getType() == UIButton.ButtonType.MENU_FIELD_WATER) {
                        die();
                        Main.gc.handler.state = GameState.WATERING;
                    }

                }
            }
        }
    }
}
