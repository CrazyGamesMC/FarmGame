package de.cg.fg.ctrl;

import de.cg.cgge.game.GameInstance;
import de.cg.cgge.game.Room;
import de.cg.fg.objects.game.GameHandler;

public class GameController {

    public GameInstance game;

    public GameHandler handler;

    public GameController() {
        game = new GameInstance("assets//cfg//config.data");
        launchRoom(RoomType.GAME);
    }

    public void launchRoom(RoomType type) {
        Room r = new Room(game);
        switch(type) {
            case GAME:
                handler = new GameHandler(r);
                break;
            case MENU:
                //TODO
                break;
        }

        game.getDrawer().changeRoomSafely(r);
    }

    public enum RoomType {
        MENU, GAME;
    }

}
