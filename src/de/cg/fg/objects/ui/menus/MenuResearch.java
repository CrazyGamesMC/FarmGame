package de.cg.fg.objects.ui.menus;

import de.cg.cgge.game.Room;
import de.cg.fg.ctrl.Main;
import de.cg.fg.ctrl.Ressources;
import de.cg.fg.objects.ui.UIButton;
import de.cg.fg.objects.ui.UIMenu;
import de.cg.fg.objects.ui.UINotification;

import java.awt.*;
import java.util.ArrayList;

import static de.cg.fg.utils.StringConstants.*;

public class MenuResearch extends UIMenu {

    private ArrayList<UIButton> buttons = new ArrayList<>();

    public MenuResearch(Room room) {
        super(room, room.getGameInstance().getWidth()-350, 0, 350, room.getGameInstance().getHeight());

        for (int i = 0; i<4; i++) {
            var button = new UIButton(room, (int)x+5, (int)5+i*(40+5), 250, 40, Ressources.fontBtnMainGame, NAMES_RESEARCH[i] + " | " + COSTS_RESEARCH[i] + "G", Color.BLACK, Color.GRAY, UIButton.ButtonType.START_RESEARCH);
            addUIObject(button);
            buttons.add(button);
        }
    }

    @Override
    public void step() {
        for (int i = 0; i<buttons.size(); i++) {
            var btn = buttons.get(i);
            if (!btn.fetchClick()) continue;

            if (Main.gc.handler.researched[i])
            {
                new UINotification(room, NAMES_RESEARCH[i] + " has already been researched!", Color.RED);
                return;
            }

            if (Main.gc.handler.money < COSTS_RESEARCH[i])
            {
                new UINotification(room, "You don't have enough money!", Color.RED);
                return;
            }
            Main.gc.handler.money -= COSTS_RESEARCH[i];

            Main.gc.handler.researched[i] = true;

            String name = NAMES_RESEARCH[i];
            new UINotification(room, name + " was researched!", Color.GREEN);
        }
    }
}
