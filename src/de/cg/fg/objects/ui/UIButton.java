package de.cg.fg.objects.ui;

import de.cg.cgge.game.Room;
import de.cg.cgge.io.MouseHelper;
import de.cg.fg.ctrl.Main;
import de.cg.fg.ctrl.Ressources;
import de.cg.fg.enums.GameState;

import java.awt.*;
import java.awt.event.MouseEvent;

public class UIButton extends UIObject {

    protected boolean isClicked = false;

    protected String text;

    protected Color fg;
    protected Color bg;
    protected Font font;

    protected MouseHelper mh = new MouseHelper(room.getGameInstance());

    protected ButtonType type;

    public UIButton(Room room, int x, int y, int w, int h, Font font, String text, Color fg, Color bg, ButtonType type) {
        super(room, x, y);

        this.w = w;
        this.h = h;

        this.text = text;
        this.fg = fg;
        this.bg = bg;
        this.font = font;
        this.type = type;
    }



     @Override
    public void postDraw(Graphics g) {

        int x = (int)this.x;
        int y = (int)this.y;

        int mx = mh.getMouseX();
        int my = mh.getMouseY();

         g.setColor(bg);


         //Mouse highlighting
         if (Main.gc.handler.state != GameState.INVENTORY_OPENED) {
             if (mx > x && mx < x + w && my > y && my < y + h) {
                 y += 2;
                 g.setColor(bg.brighter());
             }
         }

        g.setFont(font);
        if (bg != null) {
            g.fillRect(x, y, w, h);
        }

        g.setColor(fg);
        g.drawString(text, x+10, y+h/2);
     }

     @Override
     public void mousePressed(MouseEvent e) {
        int mx = mh.getMouseX();
        int my = mh.getMouseY();

        if (Main.gc.handler != null && (Main.gc.handler.state != GameState.INVENTORY_OPENED || type == ButtonType.INVENTORY)) {
            if (mx > x && mx < x + w && my > y && my < y + h) {
                isClicked = true;
                Ressources.soundClick.play();
            }
        }
     }

     public boolean fetchClick() {
        if (isClicked) {
            isClicked = false;
            return true;
        }

        return false;

     }

     public ButtonType getType() {
        return this.type;
     }


    public enum ButtonType {
        PLACE, MANAGE, FIELD, RESEARCH,
        MENU_MAIN_START,
        MENU_PLACE_FIELD, MENU_PLACE_FLOUR_MACHINE, MENU_PLACE_PIG, MENU_PLACE_OVEN, MENU_PLACE_CHICKEN,
        MENU_SELL_ADD, MENU_SELL_REMOVE,
        MENU_FIELD_PLANT, MENU_FIELD_HARVEST, MENU_FIELD_WATER, MENU_FIELD_FERTILIZE,
        MENU_MANAGE_EMPLOYEE_ADD, MENU_MANAGE_EMPLOYEE_REMOVE,
        MENU_MACHINE_ADD, MENU_MACHINE_PRODUCE,
        CROP_WHEAT, CROP_CORN,
        ANIMAL_FEED, ANIMAL_COLLECT, ANIMAL_BUTCHER,
        START_RESEARCH,
        INVENTORY;
    }

}
