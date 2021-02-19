package de.cg.fg.objects.ui.menus;

import de.cg.cgge.game.GameObject;
import de.cg.cgge.game.Room;
import de.cg.fg.ctrl.GameController;
import de.cg.fg.ctrl.Main;
import de.cg.fg.ctrl.Ressources;
import de.cg.fg.objects.ui.UIButton;
import de.cg.fg.objects.ui.UIFloatingButton;
import de.cg.fg.objects.ui.UILabel;

import java.awt.*;
import java.awt.event.MouseEvent;

public class MenuMainMenu extends GameObject {

    private UILabel lblTitle;
    private UIFloatingButton btnStart;

    public MenuMainMenu(Room room) {
        super(room);

        lblTitle = new UILabel(room, 20, 20, "Farm Game", Ressources.fontBtnMainGame, Color.WHITE);
        btnStart = new UIFloatingButton(room, 400, Ressources.fontBtnMainGame, "START", Color.WHITE, UIButton.ButtonType.MENU_MAIN_START);
    }

    @Override
    public void step() {
        if (btnStart.fetchClick()) {
            Main.gc.launchRoom(GameController.RoomType.GAME);
        }
    }
}
