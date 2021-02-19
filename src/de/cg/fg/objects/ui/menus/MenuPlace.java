package de.cg.fg.objects.ui.menus;

import de.cg.cgge.game.GameObject;
import de.cg.cgge.game.Room;
import de.cg.fg.enums.GameState;
import de.cg.fg.ctrl.Main;
import de.cg.fg.enums.ItemType;
import de.cg.fg.enums.PlacableType;
import de.cg.fg.ctrl.Ressources;
import de.cg.fg.objects.ui.*;

import static de.cg.fg.utils.StringConstants.*;

import java.awt.*;

public class MenuPlace extends UIMenu {

    private UIButton btnField;
    private UIButton btnFlourMachine;
    private UIButton btnOven;
    private UIButton btnPig;
    private UIButton btnChicken;

    public MenuPlace(Room room, int x, int y, int w, int h) {
        super(room, x, y, w, h);

        btnField        = new UIButton(room, x+20, y+40, 300, 50, Ressources.fontBtnMainGame, "FIELD | " + PlacableType.FIELD.getCost() + "G", Color.BLACK, Color.GRAY, UIButton.ButtonType.MENU_PLACE_FIELD);
        addUIObject(btnField);
        btnFlourMachine = new UIButton(room, x+20, y+100, 300, 50, Ressources.fontBtnMainGame, "FLOUR MACHINE | " + PlacableType.FLOUR_MACHINE.getCost() + "G", Color.BLACK, (Main.gc.handler.researched[RESEARCH_FLOUR] ? Color.GRAY : Color.DARK_GRAY), UIButton.ButtonType.MENU_PLACE_FLOUR_MACHINE);
        addUIObject(btnFlourMachine);
        btnOven = new UIButton(room, x+20, y+160, 300, 50, Ressources.fontBtnMainGame, "OVEN | " + PlacableType.OVEN.getCost() + "G", Color.BLACK, (Main.gc.handler.researched[RESEARCH_OVEN] ? Color.GRAY : Color.DARK_GRAY), UIButton.ButtonType.MENU_PLACE_OVEN);
        addUIObject(btnOven);
        btnPig          = new UIButton(room, x+20, y+220, 300, 50, Ressources.fontBtnMainGame, "PIG | " + PlacableType.PIG.getCost() + "G", Color.BLACK, (Main.gc.handler.researched[RESEARCH_ANIMALS] ? Color.GRAY : Color.DARK_GRAY), UIButton.ButtonType.MENU_PLACE_PIG);
        addUIObject(btnPig);
        btnChicken      = new UIButton(room, x+20, y+280, 300, 50, Ressources.fontBtnMainGame, "CHICKEN | " + PlacableType.CHICKEN.getCost() + "G", Color.BLACK, (Main.gc.handler.researched[RESEARCH_ANIMALS] ? Color.GRAY : Color.DARK_GRAY), UIButton.ButtonType.MENU_PLACE_CHICKEN);
        addUIObject(btnChicken);

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
                    if (!Main.gc.handler.researched[RESEARCH_FLOUR])
                    {
                        new UINotification(room, "This has not yet been researched!", Color.red);
                        return;
                    }

                    cost = PlacableType.FLOUR_MACHINE.getCost();
                    type = PlacableType.FLOUR_MACHINE;
                }

                if (btn.getType() == UIButton.ButtonType.MENU_PLACE_PIG) {
                    if (!Main.gc.handler.researched[RESEARCH_ANIMALS])
                    {
                        new UINotification(room, "This has not yet been researched!", Color.red);
                        return;
                    }

                    cost = PlacableType.PIG.getCost();
                    type = PlacableType.PIG;
                }

                if (btn.getType() == UIButton.ButtonType.MENU_PLACE_OVEN) {
                    if (!Main.gc.handler.researched[RESEARCH_OVEN])
                    {
                        new UINotification(room, "This has not yet been researched!", Color.red);
                        return;
                    }

                    cost = PlacableType.OVEN.getCost();
                    type = PlacableType.OVEN;
                }

                if (btn.getType() == UIButton.ButtonType.MENU_PLACE_CHICKEN)
                {
                    if (!Main.gc.handler.researched[RESEARCH_ANIMALS])
                    {
                        new UINotification(room, "This has not yet been researched!", Color.red);
                        return;
                    }

                    cost = PlacableType.CHICKEN.getCost();
                    type = PlacableType.CHICKEN;
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
