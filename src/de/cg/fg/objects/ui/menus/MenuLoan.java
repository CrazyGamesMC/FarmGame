package de.cg.fg.objects.ui.menus;

import de.cg.cgge.game.Room;
import de.cg.fg.ctrl.Loan;
import de.cg.fg.ctrl.Main;
import de.cg.fg.ctrl.Ressources;
import de.cg.fg.objects.ui.UIButton;
import de.cg.fg.objects.ui.UILabel;
import de.cg.fg.objects.ui.UIMenu;

import java.awt.*;

public class MenuLoan extends UIMenu {

    private int loanAmount = 1000;

    private UILabel lblAmount;
    private UIButton btnAdd;
    private UIButton btnSubtract;

    private UIButton btnConfirm;

    public MenuLoan(Room room, int x, int y, int w, int h) {
        super(room, x, y, w, h);

        lblAmount = new UILabel(room, x+10, y+125, "Amount: " + loanAmount + " / Interest: " + Loan.fetchInterestRates(loanAmount, Main.gc.handler), Ressources.fontBtnMainGame, Color.WHITE);
        addUIObject(lblAmount);

        btnSubtract = new UIButton(room, x+10, y+150, 50, 50, Ressources.fontBtnMainGame, "-", Color.BLACK, Color.GRAY, UIButton.ButtonType.MENU_MANAGE_EMPLOYEE_REMOVE);
        addUIObject(btnSubtract);

        btnAdd = new UIButton(room, x+65, y+150, 50, 50, Ressources.fontBtnMainGame, "+", Color.BLACK, Color.GRAY, UIButton.ButtonType.MENU_MANAGE_EMPLOYEE_ADD);
        addUIObject(btnAdd);

        btnConfirm = new UIButton(room ,x+10, y+220, 150, 50, Ressources.fontBtnMainGame, "Confirm", Color.BLACK, Color.GRAY, UIButton.ButtonType.MENU_FIELD_FERTILIZE);
        addUIObject(btnConfirm);
    }

    @Override
    public void step() {
        if (btnAdd.fetchClick())
        {
            loanAmount += 100;
        }

        if (btnSubtract.fetchClick())
        {
            loanAmount -= 100;
            if (loanAmount < 500) loanAmount = 500;
        }

        if (btnConfirm.fetchClick())
        {
            Loan loan = new Loan(loanAmount, Loan.fetchInterestRates(loanAmount, Main.gc.handler));
            Main.gc.handler.currentLoan = loan;
            Main.gc.handler.money += loanAmount;
            die();
        }

        lblAmount.setText("Amount: " + loanAmount + " / Interest: " + Loan.fetchInterestRates(loanAmount, Main.gc.handler));
    }
}
