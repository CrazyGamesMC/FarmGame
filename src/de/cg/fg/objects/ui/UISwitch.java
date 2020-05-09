package de.cg.fg.objects.ui;

import de.cg.cgge.game.Room;
import de.cg.cgge.io.MouseHelper;
import de.cg.fg.ctrl.Ressources;

import java.awt.*;
import java.awt.event.MouseEvent;

public class UISwitch extends UIObject {

    private boolean activated;
    private boolean stateChanged;

    private MouseHelper mouseHelper = new MouseHelper(room.getGameInstance());

    public UISwitch(Room room, int x, int y) {
        super(room, x, y);

        this.w = 60;
        this.h = 20;
    }

    @Override
    public void postDraw(Graphics g) {
        final int btnPos;

        if (activated) {
            g.setColor(Color.GREEN);
            btnPos = (int) x+w-h;
        } else {
            g.setColor(Color.BLACK);
            btnPos = (int) x;
        }

        g.fillRect((int) x, (int) y, w, h);

        g.setColor(Color.WHITE);

        g.fillRect(btnPos, (int) y, h, h);

    }

    @Override
    public void mousePressed(MouseEvent e) {
        int mx = mouseHelper.getMouseX();
        int my = mouseHelper.getMouseY();

        if (mx > x && mx < x+w && my > y && my < y+h) {
            activated = !activated;
            stateChanged = true;
            Ressources.soundClick.play();
        }
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public boolean fetchStateChange() {

        if (stateChanged) {
            stateChanged = false;
            return true;
        }

        return false;


    }
}
