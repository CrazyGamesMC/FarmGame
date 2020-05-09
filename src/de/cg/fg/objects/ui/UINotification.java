package de.cg.fg.objects.ui;

import de.cg.cgge.game.GameObject;
import de.cg.cgge.game.Room;
import de.cg.fg.ctrl.Ressources;

import java.awt.*;

public class UINotification extends GameObject {

    private int passedFrames = 0;
    private String message = "";
    private Color fg;

    public UINotification(Room room, String message, Color fg) {
        super(room);
        this.message = message;
        this.fg = fg;
    }

    @Override
    public void postDraw(Graphics g) {
        double strWidth = g.getFontMetrics(Ressources.fontBtnMainGame).getStringBounds(message, g).getBounds().getWidth();
        int boxPadding = (int) (room.getGameInstance().getWidth()/2-strWidth/2)-50;
        g.setColor(new Color(30, 30, 30, 200));
        g.fillRect(boxPadding, 10, room.getGameInstance().getWidth()-boxPadding*2, 50);

        g.setFont(Ressources.fontBtnMainGame);
        g.setColor(fg);
        g.drawString(message, (int) (room.getGameInstance().getWidth()/2-strWidth/2), 30+10);
    }

    @Override
    public void step() {
        passedFrames++;
        if (passedFrames >= 60*4) {
            destroy();
        }
    }

}
