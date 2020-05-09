package de.cg.fg.objects.ui;

import de.cg.cgge.game.Room;

import java.awt.*;

public class UILabel extends UIObject {

    private String text;
    private Font font;
    private Color fg;

    public UILabel(Room room, int x, int y, String text, Font font, Color fg) {
        super(room, x, y);
        this.text = text;
        this.font = font;
        this.fg = fg;
    }

    @Override
    public void postDraw(Graphics g) {
        g.setFont(font);
        g.setColor(fg);
        g.drawString(text, (int) x, (int) y);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
