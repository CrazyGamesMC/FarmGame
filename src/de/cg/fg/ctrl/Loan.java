package de.cg.fg.ctrl;

import de.cg.fg.enums.CropType;
import de.cg.fg.objects.game.GameHandler;
import de.cg.fg.objects.game.placables.Animal;
import de.cg.fg.objects.game.placables.Field;
import de.cg.fg.objects.game.placables.FlourMachine;
import de.cg.fg.objects.game.placables.Oven;

public class Loan {

    private int amount;
    private double interest;
    private int daysGoing;
    private int dailyCost = 0;

    public Loan(int amount, double interest)
    {
        this.amount = (int) (amount*1.0+(100/interest));
        this.interest = interest;
        this.dailyCost = amount/100;
    }

    public static double fetchInterestRates(int amount, GameHandler h)
    {
        double baseValue = 1;
        double liquidity = 1.0+(double)h.money/(double)amount;

        for (int y = 0; y<h.placables.length; y++)
        {
            for (int x = 0; x<h.placables[0].length; x++)
            {
                var p = h.placables[y][x];

                if (p instanceof Field)
                {
                    var f = (Field) p;
                    if (f.getCropType() != CropType.EMPTY) baseValue++;
                }

                else if (p instanceof Animal)
                {
                    baseValue+=2;
                }

                else if (p instanceof FlourMachine)
                {
                    baseValue ++;
                }

                else if (p instanceof Oven)
                {
                    baseValue ++;
                }

            }
        }

        for (boolean b : h.researched)
        {
            if (b) baseValue += 2;
        }


        baseValue*=liquidity;
        //System.out.println("Base Value: " + baseValue + " SQRT: " + Math.sqrt(2/(double)baseValue));
        double ret = Math.sqrt(2/baseValue)*(double)15;
        int shortened = (int)(ret*100);
        ret = shortened;
        return (double)(ret/100.0);


    }

    public void paybackChecker()
    {
        daysGoing++;
        if (daysGoing < 11) return;
        if (daysGoing >= 110) Main.gc.handler.currentLoan = null;
    }

    public int getAmount() {
        return amount;
    }

    public double getInterest() {
        return interest;
    }

    public int getDaysGoing() {
        return daysGoing;
    }

    public int getDailyCost() {
        return dailyCost;
    }
}
