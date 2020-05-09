package de.cg.fg.objects.ui;

import de.cg.cgge.game.Room;

import java.awt.*;

public class UIProgressBar extends UIObject{

    private int current = 0;
    private int goal = 0;

    private Color color;

    public UIProgressBar(Room room, int x, int y, int w, int h, int current, int goal, Color color) {
        super(room, x, y);
        this.w = w;
        this.h = h;
        this.color = color;
        this.current = current;
        this.goal = goal;
    }

    @Override
    public void postDraw(Graphics g) {
        int sectionSize = w/goal;
        for (int i = 0; i<goal; i++) {
            g.setColor(color);
            if (i < current) {
                g.fillRect((int) x + i * sectionSize, (int) y, sectionSize, h);
            }
            g.setColor(Color.BLACK);
            g.drawRect((int) x + i * sectionSize, (int) y, sectionSize, h);

        }
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getGoal() {
        return goal;
    }

    public void setGoal(int goal) {
        this.goal = goal;
    }
}
