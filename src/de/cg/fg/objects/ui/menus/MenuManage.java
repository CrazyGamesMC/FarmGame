package de.cg.fg.objects.ui.menus;

import de.cg.cgge.game.Room;
import de.cg.fg.ctrl.Main;
import de.cg.fg.ctrl.Ressources;
import de.cg.fg.objects.ui.*;

import java.awt.*;

public class MenuManage extends UIMenu {

    private UILabel lblMoney;
    private UILabel lblCost;
    private UILabel lblEmployees;
    private UIButton btnAddEmployee;
    private UIButton btnRemoveEmployee;

    private UIButton btnLoan;

    public MenuManage(Room room, int x, int y, int w, int h) {
        super(room, x, y, w, h);

        lblMoney = new UILabel(room, x+10, y+50, "Money: " + Main.gc.handler.money, Ressources.fontBtnMainGame, Color.YELLOW);
        addUIObject(lblMoney);

        lblCost = new UILabel(room, x+10, y+75, "Daily Cost: " + Main.gc.handler.getDailyCost(), Ressources.fontMouseInfo, Color.WHITE);
        addUIObject(lblCost);

        lblEmployees = new UILabel(room, x+10, y+125, "Employees: " + Main.gc.handler.employees, Ressources.fontBtnMainGame, Color.WHITE);
        addUIObject(lblEmployees);

        btnRemoveEmployee = new UIButton(room, x+10, y+150, 50, 50, Ressources.fontBtnMainGame, "-", Color.BLACK, Color.GRAY, UIButton.ButtonType.MENU_MANAGE_EMPLOYEE_REMOVE);
        addUIObject(btnRemoveEmployee);

        btnAddEmployee = new UIButton(room, x+65, y+150, 50, 50, Ressources.fontBtnMainGame, "+", Color.BLACK, Color.GRAY, UIButton.ButtonType.MENU_MANAGE_EMPLOYEE_ADD);
        addUIObject(btnAddEmployee);

        btnLoan = new UIButton(room, x+10, y+250, 150, 50, Ressources.fontBtnMainGame, "Take Loan", Color.BLACK,
                (Main.gc.handler.currentLoan == null ? Color.GRAY : Color.DARK_GRAY), UIButton.ButtonType.ANIMAL_BUTCHER);
        addUIObject(btnLoan);


    }

    @Override
    public void step() {
        if (btnAddEmployee.fetchClick()) {
            if (Main.gc.handler.money >= Main.gc.handler.getDailyCost()) {
                Main.gc.handler.employees++;
                Main.gc.handler.maxDayPart += 2;
                Main.gc.handler.increaseDayPart();
                Main.gc.handler.money -= Main.gc.handler.getDailyCost();
                lblEmployees.setText("Employees: " + Main.gc.handler.employees);
                lblCost.setText("Daily Cost: " + Main.gc.handler.getDailyCost());
            } else {
                new UINotification(room, "You need at least " + Main.gc.handler.getDailyCost() + "G", Color.RED);
            }
        }

        if (btnRemoveEmployee.fetchClick()) {
            if (Main.gc.handler.money >= Main.gc.handler.getDailyCost()) {
                Main.gc.handler.employees--;
                Main.gc.handler.maxDayPart -= 2;
                Main.gc.handler.increaseDayPart();
                lblEmployees.setText("Employees: " + Main.gc.handler.employees);
                lblCost.setText("Daily Cost: " + Main.gc.handler.getDailyCost());
            }
        }

        if (btnLoan.fetchClick())
        {
            if (Main.gc.handler.currentLoan != null) return;
            die();
            new MenuLoan(room, 100, 100, 500, 500);
        }


    }

}
