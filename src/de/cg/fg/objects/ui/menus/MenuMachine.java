package de.cg.fg.objects.ui.menus;

import de.cg.cgge.game.Room;
import de.cg.fg.ctrl.InventoryItem;
import de.cg.fg.ctrl.Main;
import de.cg.fg.ctrl.Ressources;
import de.cg.fg.enums.GameState;
import de.cg.fg.objects.game.placables.Machine;
import de.cg.fg.objects.ui.UIButton;
import de.cg.fg.objects.ui.UILabel;
import de.cg.fg.objects.ui.UIMenu;
import de.cg.fg.objects.ui.UINotification;

import java.awt.*;

public class MenuMachine extends UIMenu {

    protected Machine machine;

    private UILabel lblInventory;
    private UILabel[] inventoryContents;

    private UILabel lblNeeds;
    private UILabel[] requirements;

    private UIButton btnAdd;

    private UIButton btnProduce;

    public MenuMachine(Room room, int x, int y, int w, int h, Machine machine) {
        super(room, x, y, w, h);
        this.machine = machine;

        this.lblInventory = new UILabel(room, x+20, y+40, "Inventory: ", Ressources.fontBtnMainGame, Color.WHITE);
        addUIObject(lblInventory);
        this.lblNeeds = new UILabel(room, x+10, y+300, "Requirements: ", Ressources.fontBtnMainGame, Color.WHITE);
        addUIObject(lblNeeds);
        this.btnAdd = new UIButton(room, x+w-100, y+40, 80, 40, Ressources.fontBtnMainGame, "ADD", Color.BLACK, Color.GRAY, UIButton.ButtonType.MENU_MACHINE_ADD);
        addUIObject(btnAdd);
        this.btnProduce = new UIButton(room, x+w-160, y+h-50, 140, 40, Ressources.fontBtnMainGame, "PRODUCE", Color.BLACK, Color.GRAY, UIButton.ButtonType.MENU_MACHINE_PRODUCE);
        addUIObject(btnProduce);

        initItems();

        Main.gc.handler.state = GameState.MENU_OPENED;
        
    }

    private void initItems() {

        {   //Creating inv items
            int ypos = (int) lblInventory.getY()+50;
            int invSize = machine.inventory.size();
            inventoryContents = new UILabel[invSize];

            for (int i = 0; i < machine.inventory.size(); i++) {
                final InventoryItem item = machine.inventory.get(i);
                final String name = item.getType().name() + " | " + item.getQuality() + "Q";
                var label = new UILabel(room, (int) x+20, ypos+i*(10+10), name, Ressources.fontMouseInfo, Color.WHITE);
                inventoryContents[i] = label;
                addUIObject(label);
            }
        }


        {   //Creating requirements
            int ypos = (int) lblNeeds.getY()+20;
            int invSize = machine.neededItems.size();
            requirements = new UILabel[invSize];

            for (int i = 0; i < invSize; i++) {
                final InventoryItem item = machine.neededItems.get(i);
                final String name = item.getType().name();
                var label = new UILabel(room, (int) x+20, ypos+i*(10+10), name, Ressources.fontMouseInfo, Color.WHITE);
                requirements[i] = label;
                addUIObject(label);
            }
        }

    }

    @Override
    public void step() {
        //If add button is clicked, open inventory in machine mode
        if (btnAdd.fetchClick()) {
            die();
            new MenuInventory(room, machine);
        }


        if (btnProduce.fetchClick()) {
            boolean success = machine.produce();
            if (success) {
                die();
                new UINotification(room, "Produced one " + machine.reward.name(), Color.GREEN);
                Main.gc.handler.increaseDayPart();
            }

            else {
                new UINotification(room, "Not enough items in machine inventory", Color.RED);
            }
        }
    }




}
