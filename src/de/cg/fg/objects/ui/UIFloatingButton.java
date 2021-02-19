package de.cg.fg.objects.ui;

import de.cg.cgge.game.Room;

import java.awt.*;

public class UIFloatingButton extends UIButton {
    public UIFloatingButton(Room room, int y, Font font, String text, Color fg, ButtonType type) {
        super(room, -10000, y, 0, 0, font, text, fg, Color.WHITE, type);
    }

    @Override
    public void postDraw(Graphics g) {
        g.setFont(font);
        g.setColor(fg);

        //Centering the button
        var metrics = g.getFontMetrics(font);
        var bounds = metrics.getStringBounds(text, g);
        w = (int)bounds.getWidth();
        h = (int)bounds.getHeight();
        x = room.getGameInstance().getWidth()/2-(int)bounds.getWidth()/2;

        g.drawString(text, (int)x, (int)y);
    }

}
