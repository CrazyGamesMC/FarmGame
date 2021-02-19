package de.cg.fg.objects.ui.menus;

import de.cg.cgge.game.Room;
import de.cg.fg.ctrl.Main;
import de.cg.fg.ctrl.Ressources;
import de.cg.fg.enums.CropType;
import de.cg.fg.objects.game.placables.Field;
import de.cg.fg.objects.ui.*;

import java.awt.*;

public class MenuFieldInfo extends UIMenu {

    private UILabel lblName;
    private UILabel lblAutoWater;


    private UISwitch swAutoWater;

    private Field field;

    public MenuFieldInfo(Room room, int x, int y, int w, int h, Field f) {
        super(room, x, y, w, h);

        this.field = f;

        if (f.getCropType() != CropType.EMPTY) addUIObject(new UIProgressBar(room, x+10, y+h-30, w-20, 20, f.getCurrentDay(), f.getCropType().getGrowthDuration(), Color.YELLOW));

        lblName = new UILabel(room, x+10, y+75, f.getCropType().name(), Ressources.fontBtnMainGame, Color.WHITE);
        addUIObject(lblName);
        lblAutoWater = new UILabel(room, x+20, y+100, "Auto Watering:", Ressources.fontMouseInfo, Color.WHITE);
        addUIObject(lblAutoWater);
        swAutoWater = new UISwitch(room, x+w-110, y+90);
        swAutoWater.setActivated(f.isAutoWatered());
        addUIObject(swAutoWater);
    }

    @Override
    public void step() {
        if (swAutoWater.fetchStateChange()) {
            boolean newState = swAutoWater.isActivated();

            //Check if switching the state is legal
            if (newState) {
                int currentAutoEmployees = Main.gc.handler.employeesUsedForAuto;
                int currentEmployees = Main.gc.handler.employees;

                if (currentAutoEmployees < currentEmployees*Ressources.employeeIncrease) {
                    Main.gc.handler.employeesUsedForAuto++;
                    Main.gc.handler.increaseDayPart();
                    field.setAutoWatered(true);
                    new UINotification(room, "Employee used: " + (currentAutoEmployees+1) + "/" + currentEmployees*Ressources.employeeIncrease, Color.GREEN);
                }

                else {
                    swAutoWater.setActivated(false);
                    new UINotification(room, "Not enough employees available", Color.RED);
                }
            }


            else {
               Main.gc.handler.employeesUsedForAuto--;
               field.setAutoWatered(false);
               new UINotification(room, "Employee removed: " + Main.gc.handler.employeesUsedForAuto + "/" + Main.gc.handler.employees*2, Color.GREEN);
            }


        }
    }
}
