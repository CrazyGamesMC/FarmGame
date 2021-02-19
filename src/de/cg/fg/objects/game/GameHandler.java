package de.cg.fg.objects.game;

import de.cg.cgge.game.CameraRenderer;
import de.cg.cgge.game.GameObject;
import de.cg.cgge.game.Room;
import de.cg.cgge.io.KeyManager;
import de.cg.cgge.io.MouseHelper;
import de.cg.fg.ctrl.InventoryItem;
import de.cg.fg.ctrl.Loan;
import de.cg.fg.enums.CropType;
import de.cg.fg.enums.GameState;
import de.cg.fg.enums.ItemType;
import de.cg.fg.enums.PlacableType;
import de.cg.fg.ctrl.Ressources;
import de.cg.fg.objects.game.placables.Animal;
import de.cg.fg.objects.game.placables.Field;
import de.cg.fg.objects.game.placables.FlourMachine;
import de.cg.fg.objects.game.placables.Oven;
import de.cg.fg.objects.game.placables.animals.Chicken;
import de.cg.fg.objects.game.placables.animals.Pig;
import de.cg.fg.objects.ui.UIButton;
import de.cg.fg.objects.ui.UILabel;
import de.cg.fg.objects.ui.UINotification;
import de.cg.fg.objects.ui.UIProgressBar;
import de.cg.fg.objects.ui.menus.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import static de.cg.fg.utils.StringConstants.*;

public class GameHandler extends GameObject {

    public int money = Ressources.startMoney;
    public int currentDay = 0;
    public int currentDayPart = 0;
    public int maxDayPart = Ressources.startMaxDayPart;
    public int employees = 0;
    public int employeesUsedForAuto = 0;

    public boolean[] researched = new boolean[10];

    public Placable[][] placables = new Placable[16][16];
    public GameState state = GameState.OPEN;
    public Loan currentLoan = null;

    public PlacableType currentlyPlacing = PlacableType.NONE;
    public CropType currentlyPlanting = CropType.EMPTY;

    public ArrayList<InventoryItem> inventory = new ArrayList<>();

    private MouseHelper mh = new MouseHelper(room.getGameInstance());
    private KeyManager keyManager = room.getGameInstance().getDrawer().getWindow().getKeyManger();

    private UILabel lblMoney;
    private UILabel lblState;
    private UIProgressBar progressBar;


    public GameHandler(Room room) {
        super(room);

        for (int y = 0; y<placables.length; y++) {
            for (int x = 0; x<placables.length; x++) {
                placables[y][x] = null;
            }
        }

        new UIButton(room, room.getGameInstance().getWidth()-200, room.getGameInstance().getHeight()-100, 150, 50, Ressources.fontBtnMainGame, "PLACE", Color.BLACK, Ressources.colorButtons, UIButton.ButtonType.PLACE);
        new UIButton(room, room.getGameInstance().getWidth()-375, room.getGameInstance().getHeight()-100, 150, 50, Ressources.fontBtnMainGame, "MANAGE", Color.BLACK, Ressources.colorButtons, UIButton.ButtonType.MANAGE);
        new UIButton(room, room.getGameInstance().getWidth()-200, room.getGameInstance().getHeight()-175, 150, 50, Ressources.fontBtnMainGame, "FIELD", Color.BLACK, Ressources.colorButtons, UIButton.ButtonType.FIELD);
        new UIButton(room, room.getGameInstance().getWidth()-375, room.getGameInstance().getHeight()-175, 150, 50, Ressources.fontBtnMainGame, "RESEARCH", Color.BLACK, Ressources.colorButtons, UIButton.ButtonType.RESEARCH);
        progressBar = new UIProgressBar(room, 50, room.getGameInstance().getHeight()-40, room.getGameInstance().getWidth()-100, 30, 0, maxDayPart, Color.GREEN);
        lblMoney = new UILabel(room, room.getGameInstance().getWidth()-600, room.getGameInstance().getHeight()-70, "Money: " + money + "G", Ressources.fontBtnMainGame, Ressources.colorMoneyLabel);
        lblState = new UILabel(room, room.getGameInstance().getWidth()-300, 50, "", Ressources.fontBtnMainGame, Color.WHITE);
    }


    @Override
    public void step() {
        if (state == GameState.OPEN) {
            for (GameObject obj : room.getObjectManager().getObjects()) {
                if (obj instanceof UIButton) {
                    UIButton btn = (UIButton) obj;
                    if (btn.fetchClick()) {
                        if (btn.getType() == UIButton.ButtonType.PLACE) {
                            new MenuPlace(room, 100, 100, 500, 500);
                        }

                        else if (btn.getType() == UIButton.ButtonType.MANAGE) {
                            new MenuManage(room, 100, 100, 500, 500);
                        }

                        else if (btn.getType() == UIButton.ButtonType.FIELD) {
                            new MenuFieldWork(room, 100, 100, 500, 500);
                        }
                        else if (btn.getType() == UIButton.ButtonType.RESEARCH) {
                            new MenuResearch(room);
                        }
                    }
                }
            }
        }
        else if (state == GameState.PLACING || state == GameState.PLANTING || state == GameState.HARVESTING || state == GameState.WATERING || state == GameState.FERTILIZING) {
            if (keyManager.checkKey(KeyEvent.VK_ESCAPE)) {
                state = GameState.OPEN;
            }
        }

        /*  INPUTS  */
        if (state == GameState.OPEN) {
            if (keyManager.checkInput(INVENTORY_OPENED)) {
                new MenuInventory(room);
            }

            if (keyManager.checkInput(PROGRESS_DAY)) {
                increaseDayPart();
            }

            if (keyManager.checkInput(SWITCH_HARVEST)) {
                state = GameState.HARVESTING;
            }

            if (keyManager.checkInput(SWITCH_WATERING)) {
                state = GameState.WATERING;
            }
        }

        if (state == GameState.HARVESTING) {

        }

        /*  STATE NOTICE    */
        if (state != GameState.OPEN && state != GameState.INVENTORY_OPENED && state != GameState.MENU_OPENED) {
            lblState.setText(state.name());
        } else {
            lblState.setText("");
        }

    }

    @Override
    public void draw(Graphics g) {
        /*  Rendering scenery   */
        CameraRenderer cr = new CameraRenderer(g, room.getCamera());

        g.setColor(Ressources.colorBG);
        g.fillRect(0,0,room.getGameInstance().getWidth(), room.getGameInstance().getHeight());

        g.setColor(Ressources.colorField);
        cr.fillRect(0,0,placables[0].length*32, placables.length*32);

        lblMoney.setText("Money: " + money + "G");
    }

    @Override
    public void postDraw(Graphics g) {
        CameraRenderer cr = new CameraRenderer(g, room.getCamera());

        for (int y = 0; y<placables.length; y++) {
            for (int x = 0; x<placables.length; x++) {
                g.setColor(new Color(70,70,70,100));
                //cr.drawRect(x*32,y*32,32,32);

                if (state == GameState.OPEN) {
                    int[] fieldOfMouse = getFieldOfMouse();
                    if (fieldOfMouse[0] == x && fieldOfMouse[1] == y) {
                        g.setColor(new Color(200, 200, 200, 70));
                        cr.fillRect(x * 32, y * 32, 32, 32);
                    }
                }

                else if (state == GameState.PLACING) {
                    int[] fieldOfMouse = getFieldOfMouse();
                    if (fieldOfMouse[0] == x && fieldOfMouse[1] == y) {
                        g.setColor(new Color(25, 200, 0, 70));
                        cr.fillRect(x * 32, y * 32, 32, 32);
                    }
                }

                else if (state == GameState.PLANTING) {
                    Placable placable = placables[y][x];

                    if (placable instanceof Field && ((Field)(placable)).getCropType() == CropType.EMPTY) {
                        g.setColor(new Color(25, 200, 0, 70));
                        cr.fillRect(x * 32, y * 32, 32, 32);
                    }

                    int[] fieldOfMouse = getFieldOfMouse();
                    if (fieldOfMouse[0] == x && fieldOfMouse[1] == y) {
                        g.setColor(new Color(200, 200, 200, 70));
                        cr.fillRect(x * 32, y * 32, 32, 32);
                    }
                }

                else if (state == GameState.WATERING) {
                    Placable placable = placables[y][x];

                    if (placable instanceof Field) {
                        Field field = (Field) placable;
                        if (field.getCropType() != CropType.EMPTY && field.needsWater()) {
                            g.setColor(new Color(100, 100, 255, 200));
                            cr.fillRect(x * 32, y * 32, 32, 32);
                        }
                    }

                    int[] fieldOfMouse = getFieldOfMouse();
                    if (fieldOfMouse[0] == x && fieldOfMouse[1] == y) {
                        g.setColor(new Color(200, 200, 200, 70));
                        cr.fillRect(x * 32, y * 32, 32, 32);
                    }
                }

                else if (state == GameState.FERTILIZING) {
                    Placable placable = placables[y][x];

                    if (placable instanceof Field) {
                        Field field = (Field) placable;
                        if (field.getCropType() != CropType.EMPTY && !field.isFertilized()) {
                            g.setColor(new Color(224, 116, 0, 200));
                            cr.fillRect(x * 32, y * 32, 32, 32);
                        }
                    }

                    int[] fieldOfMouse = getFieldOfMouse();
                    if (fieldOfMouse[0] == x && fieldOfMouse[1] == y) {
                        g.setColor(new Color(200, 200, 200, 70));
                        cr.fillRect(x * 32, y * 32, 32, 32);
                    }
                }

                else if (state == GameState.HARVESTING) {
                    Placable placable = placables[y][x];

                    if (placable instanceof Field) {
                        Field field = (Field) placable;
                        if (field.getCropType() != CropType.EMPTY && field.getCurrentDay() >= field.getCropType().getGrowthDuration()) {
                            g.setColor(new Color(25, 200, 0, 70));
                            cr.fillRect(x * 32, y * 32, 32, 32);
                        }
                    }

                    int[] fieldOfMouse = getFieldOfMouse();
                    if (fieldOfMouse[0] == x && fieldOfMouse[1] == y) {
                        g.setColor(new Color(200, 200, 200, 70));
                        cr.fillRect(x * 32, y * 32, 32, 32);
                    }
                }
            }
        }
    }

    public int[] getFieldOfMouse() {
        int[] ret = new int[2];

        ret[0] = (mh.getMouseX()-room.getCamera().getX())/32;
        ret[1] = (mh.getMouseY()-room.getCamera().getY())/32;

        return ret;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);

        int fx = getFieldOfMouse()[0];
        int fy = getFieldOfMouse()[1];

        if (fx > 0 && fx < placables[0].length && fy > 0 && fy < placables[0].length) {


            if (state == GameState.OPEN) {

                //If a field is clicked, show info menu
                if (placables[fy][fx] instanceof Field) {
                    Field f = (Field) placables[fy][fx];
                    new MenuFieldInfo(room, mh.getMouseX(), mh.getMouseY(), 500, 300, f);
                }


                //If a flour machine is pressed, show machine menu of flour machine
                else if (placables[fy][fx] instanceof FlourMachine) {
                    FlourMachine fm = (FlourMachine) placables[fy][fx];
                    new MenuFlourMachine(room, 100, 100, 500, 500, fm);
                }


                else if (placables[fy][fx] instanceof Oven) {
                    Oven oven = (Oven) placables[fy][fx];
                    new MenuOven(room, 100, 100, 500, 500, oven);
                }

                else if (placables[fy][fx] instanceof Animal) {
                    Animal animal = (Animal) placables[fy][fx];
                    new MenuAnimalInfo(room, animal);
                }

            }

            else if (state == GameState.HARVESTING) {

                if (placables[fy][fx] instanceof Field) {
                    Field f = (Field) placables[fy][fx];
                    if (f.getCropType() != CropType.EMPTY && f.getCurrentDay() >= f.getCropType().getGrowthDuration()) {

                        ItemType itemType = ItemType.NONE;
                        int quality = 3+f.getDaysWithFertilization()-f.getDaysWithoutWater();
                        quality = (Math.max(quality, 0));

                        if (f.getCropType() == CropType.WHEAT) itemType = ItemType.WHEAT;
                        if (f.getCropType() == CropType.CORN) itemType = ItemType.CORN;

                        inventory.add(new InventoryItem(itemType, quality));

                        f.setCropType(CropType.EMPTY);
                        f.updateSprite();
                        f.setCurrentDay(0);
                        f.setDaysWithFertilization(0);
                        f.setDaysWithoutWater(0);
                        f.setFertilized(false);
                        f.setNeedsWater(true);

                        Ressources.soundHarvest.play();
                        increaseDayPart();
                    }
                }

            }

            else if (state == GameState.PLACING) {
                if (checkIfFieldsAreFree(fx, fy)) {
                    if (money >= currentlyPlacing.getCost()) {

                        final int size = currentlyPlacing.getSize();

                        final Placable placable;

                        if (currentlyPlacing == PlacableType.FIELD) placable = new Field(room, fx, fy);
                        else if (currentlyPlacing == PlacableType.FLOUR_MACHINE) placable = new FlourMachine(room, fx, fy);
                        else if (currentlyPlacing == PlacableType.PIG) placable = new Pig(room, fx, fy);
                        else if (currentlyPlacing == PlacableType.OVEN) placable = new Oven(room, fx, fy);
                        else if (currentlyPlacing == PlacableType.CHICKEN) placable = new Chicken(room, fx, fy);

                        else placable = new Placable(room, null, fx, fy);

                        for (int y = 0; y<size; y++) {
                            for (int x = 0; x<size; x++) {
                                placables[fy+y][fx+x] = placable;
                            }
                        }
                        money -= currentlyPlacing.getCost();
                        Ressources.soundPlace.play();
                        increaseDayPart();
                    }
                } else {
                    new UINotification(room, "This spot is already occupied!", Color.RED);
                }
            }

            else if (state == GameState.PLANTING) {
                if (placables[fy][fx] instanceof Field) {
                    Field f = (Field) placables[fy][fx];

                    final int cost = currentlyPlanting.getCost();

                    if (f.getCropType() == CropType.EMPTY) {
                        if (money >= cost) {
                            f.setCropType(currentlyPlanting);
                            f.setCurrentDay(1);
                            f.updateSprite();
                            if (f.isAutoWatered()) f.setNeedsWater(false);
                            money -= cost;
                            Ressources.soundPlace.play();
                            increaseDayPart();
                        } else {
                            new UINotification(room, "You do not have enough money!" , Color.RED);
                        }
                    } else {
                        new UINotification(room, "This field is already occupied!" , Color.RED);
                    }

                }
            }

            else if (state == GameState.FERTILIZING)
            {
                if (placables[fy][fx] instanceof Field)
                {
                    var f = (Field) placables[fy][fx];

                    if (f.getCropType() == CropType.EMPTY)
                    {
                        new UINotification(room, "You can't fertilize an empty field", Color.RED);
                        return;
                    }

                    if (f.isFertilized())
                    {
                        new UINotification(room, "This field is already fertilized", Color.RED);
                        return;
                    }

                    if (money < 2)
                    {
                        new UINotification(room, "You don't have enough money!", Color.RED);
                        return;
                    }

                    money -= 2;
                    increaseDayPart();
                    f.setFertilized(true);
                }
            }

            else if (state == GameState.WATERING) {
                if (placables[fy][fx] instanceof Field) {
                    Field f = (Field) placables[fy][fx];

                    if (f.getCropType() != CropType.EMPTY && f.needsWater()) {
                        f.setNeedsWater(false);
                        increaseDayPart();
                    }

                }
            }
        }

    }

    public boolean checkIfFieldsAreFree(int fx, int fy) {
        int size = currentlyPlacing.getSize();
        System.out.println("Checking " + fx + ", " + fy);

        if (fx < 0 || fy < 0) return false;

        if (size + fx > placables[0].length) return false;

        if (size + fy > placables.length) return false;

        for (int y = 0; y<size; y++) {
            for (int x = 0; x<size; x++) {
                if (placables[fy+y][fx+x] != null) return false;
            }
        }

        return true;
    }

    public void increaseDayPart() {
        currentDayPart++;
        if (currentDayPart >= maxDayPart) {
            currentDayPart = 0;
            simulateDayEnd();
            currentDay++;
        }
        progressBar.setCurrent(currentDayPart);
        progressBar.setGoal(maxDayPart);
    }

    private void simulateDayEnd() {
        System.out.println("DEBUG - Simulating day end!");
        for (int y = 0; y<placables.length; y++) {
            for (int x = 0; x<placables[0].length; x++) {
                Placable placable = placables[y][x];
                if (placable != null) {
                    placable.increaseDay();
                }
            }
        }

        money-=getDailyCost();
        if (currentLoan != null) currentLoan.paybackChecker();
    }

    public int getDailyCost() {
        int cost = (int) (Math.pow(Math.pow(placables.length, 2)/10, 1.2));
        cost += Math.pow(employees*4, 2);
        cost/=5;
        if (currentLoan != null && currentLoan.getDaysGoing() > 10)
        {
            cost += currentLoan.getDailyCost();
        }
        return cost;
    }

    public boolean checkItemInInventory(ItemType type, int amount, int minQuality) {

        int amountFound = 0;

        for (InventoryItem item : inventory) {
            if (item.getType() != type) continue;
            if (item.getQuality() < minQuality) continue;

            amountFound++;

            if (amountFound == amount) return true;
        }

        return false;

    }

    public void removeFromInventory(ItemType type, int amount, int minQuality) {
        for (int i = 0; i<amount; i++) {
            for (int j = 0; j<inventory.size(); j++) {
                InventoryItem item = inventory.get(j);
                if (item.getType() != type) continue;
                if (item.getQuality() < minQuality) continue;
                inventory.remove(item);
                break;
            }
        }
    }

}
