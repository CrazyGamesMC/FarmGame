package de.cg.fg.ctrl;

import de.cg.cgge.files.GameFile;
import de.cg.cgge.game.GameInstance;
import de.cg.cgge.game.Room;
import de.cg.cgge.io.Input;
import de.cg.cgge.io.KeyManager;
import de.cg.fg.objects.game.GameHandler;
import de.cg.fg.objects.ui.menus.MenuMainMenu;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static de.cg.fg.utils.StringConstants.*;

public class GameController {

    public GameInstance game;

    public GameHandler handler;

    public GameController() {
        game = new GameInstance("assets//cfg//config.data");
        launchRoom(RoomType.GAME);
        initInputs();
    }

    public void launchRoom(RoomType type) {
        Room r = new Room(game);
        switch(type) {
            case GAME:
                handler = new GameHandler(r);
                break;
            case MENU:
                new MenuMainMenu(r);
                break;
        }

        game.getDrawer().changeRoomSafely(r);
    }

    public enum RoomType {
        MENU, GAME;
    }

    private void initInputs() {
        KeyManager km = game.getRoom().getKeyManager();

        km.addInput(INVENTORY_OPENED, new Input(Input.Type.KEY_JUST_PRESSED, KeyEvent.VK_I));
        km.addInput(MENU_CLOSED, new Input(Input.Type.KEY_JUST_PRESSED, KeyEvent.VK_ESCAPE));
        km.addInput(PROGRESS_DAY, new Input(Input.Type.KEY_JUST_PRESSED, KeyEvent.VK_Q));
        km.addInput(SWITCH_HARVEST, new Input(Input.Type.KEY_JUST_PRESSED, KeyEvent.VK_H));
        km.addInput(SWITCH_WATERING, new Input(Input.Type.KEY_JUST_PRESSED, KeyEvent.VK_W));

    }

    public void saveFile() {
        try {
            GameFile gf = new GameFile("save.data");
            File file = gf.getFile();
            if (!file.exists()) {
                file.createNewFile();
            }

            List<String> store = new ArrayList<>();
            List<String> subStore = new ArrayList<>();

            store.add("money: " + handler.money);
            store.add("currentDayPart: " + handler.currentDayPart);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
