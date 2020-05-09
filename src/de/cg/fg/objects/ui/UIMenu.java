package de.cg.fg.objects.ui;

import de.cg.cgge.game.GameObject;
import de.cg.cgge.game.Room;
import de.cg.cgge.io.MouseHelper;
import de.cg.fg.enums.GameState;
import de.cg.fg.ctrl.Main;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;

public class UIMenu extends GameObject {

    protected ArrayList<UIObject> uiObjects = new ArrayList<>();
    protected MouseHelper mh = new MouseHelper(room.getGameInstance());

    public UIMenu(Room room, int x, int y, int w, int h) {
        super(room);

        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;

        Main.gc.handler.state = GameState.MENU_OPENED;
    }

    public UIMenu(Room room, int x, int y, int w, int h, UIObject... objects) {
        this(room, x, y, w, h);

        uiObjects.addAll(Arrays.asList(objects));

    }

    public void addUIObject(UIObject uiObject) {
        uiObjects.add(uiObject);
    }

    protected void die() {
        Main.gc.handler.state = GameState.OPEN;
        uiObjects.forEach(GameObject::destroy);
        destroy();
    }

    @Override
    public void postDraw(Graphics g) {
        g.setColor(new Color(50, 50, 50, 200));
        g.fillRect((int)x, (int) y, w, h);

        //Close button
        Color close = new Color(255, 60, 43);
        int mx = mh.getMouseX();
        int my = mh.getMouseY();

        if (mx > x+(w-30) && mx < x+w && my > y && my < y+30) close = close.brighter();
        g.setColor(close);
        g.fillRect((int) (x+w-30), (int) y, 30, 30);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int mx = mh.getMouseX();
        int my = mh.getMouseY();

        if (mx > x+(w-30) && mx < x+w && my > y && my < y+30) die(); //If the close button is clicked
    }

}
