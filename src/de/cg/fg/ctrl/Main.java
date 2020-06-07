package de.cg.fg.ctrl;

import de.cg.fg.enums.ItemType;

import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static GameController gc;

    public static void main(String[] args) {
        gc = new GameController();
        debugMode();
    }

    private static void debugMode() {
        Thread t = new Thread(() -> {
            Scanner sc = new Scanner(System.in);
            while (true) {
                String input = sc.nextLine();
                String[] parts = input.split(" ");
                String cmd = parts[0];
                String[] args = new String[parts.length-1];
                for (int i = 1; i<parts.length; i++) {
                    args[i-1] = parts[i];
                }

                if (cmd.equalsIgnoreCase("money")) {
                    String subCMD = args[0];
                    int amount = Integer.parseInt(args[1]);
                    if (subCMD.equalsIgnoreCase("add")) {
                        gc.handler.money+=amount;
                    }

                    else if (subCMD.equalsIgnoreCase("remove")) {
                        gc.handler.money-=amount;
                    }
                }

                else if (cmd.equalsIgnoreCase("give")) {
                    try {
                        ItemType type = ItemType.valueOf(args[0]);
                        int quality = Integer.parseInt(args[1]);
                        int amount = Integer.parseInt(args[2]);

                        for (int i = 0; i<amount; i++) {
                            InventoryItem item = new InventoryItem(type, quality);
                            Main.gc.handler.inventory.add(item);
                        }

                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        t.start();
    }
}
