package de.cg.fg.objects.game;

import de.cg.cgge.game.CameraRenderer;
import de.cg.cgge.game.GameObject;
import de.cg.cgge.game.Room;
import de.cg.cgge.io.KeyManager;
import de.cg.cgge.io.MouseHelper;
import de.cg.fg.ctrl.InventoryItem;
import de.cg.fg.enums.CropType;
import de.cg.fg.enums.GameState;
import de.cg.fg.enums.ItemType;
import de.cg.fg.enums.PlacableType;
import de.cg.fg.ctrl.Ressources;
import de.cg.fg.objects.game.placables.Animal;
import de.cg.fg.objects.game.placables.Field;
import de.cg.fg.objects.game.placables.FlourMachine;
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

public class GameHandler extends GameObject {

    public int money = Ressources.startMoney;
    public int currentDay = 0;
    public int currentDayPart = 0;
    public int maxDayPart = Ressources.startMaxDayPart;
    public int employees = 0;
    public int employeesUsedForAuto = 0;

    public Placable[][] placables = new Placable[16][16];
    public GameState state = GameState.OPEN;

    public PlacableType currentlyPlacing = PlacableType.NONE;
    public CropType currentlyPlanting = CropType.EMPTY;

    public ArrayList<InventoryItem> inventory = new ArrayList<>();

    private MouseHelper mh = new MouseHelper(room.getGameInstance());
    private KeyManager keyManager = room.getGameInstance().getDrawer().getWindow().getKeyManger();

    private UILabel lblMoney;
    private UIProgressBar progressBar;


    public GameHandler(Room room) {
        super(room);

        for (int y = 0; y<placables.length; y++) {
            for (int x = 0; x<placables.length; x++) {
                placables[y][x] = null;
            }
        }

        new UIButton(room, room.getGameInstance().getWidth()-150, room.getGameInstance().getHeight()-100, 100, 50, Ressources.fontBtnMainGame, "PLACE", Color.BLACK, Ressources.colorButtons, UIButton.ButtonType.PLACE);
        new UIButton(room, room.getGameInstance().getWidth()-300, room.getGameInstance().getHeight()-100, 100, 50, Ressources.fontBtnMainGame, "MANAGE", Color.BLACK, Ressources.colorButtons, UIButton.ButtonType.MANAGE);
        new UIButton(room, room.getGameInstance().getWidth()-300, room.getGameInstance().getHeight()-175, 100, 50, Ressources.fontBtnMainGame, "FIELD", Color.BLACK, Ressources.colorButtons, UIButton.ButtonType.FIELD);
        progressBar = new UIProgressBar(room, 50, room.getGameInstance().getHeight()-40, room.getGameInstance().getWidth()-100, 30, 0, maxDayPart, Color.GREEN);
        lblMoney = new UILabel(room, room.getGameInstance().getWidth()-500, room.getGameInstance().getHeight()-70, "Money: " + money + "G", Ressources.fontBtnMainGame, Ressources.colorMoneyLabel);
    }

    private boolean keyLockSpace = false;

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
                    }
                }
            }
        }
        else if (state == GameState.PLACING || state == GameState.PLANTING || state == GameState.HARVESTING || state == GameState.WATERING) {
            if (keyManager.checkKey(KeyEvent.VK_ESCAPE)) {
                state = GameState.OPEN;
            }
        }

        if (state == GameState.OPEN) {
            if (keyManager.checkKey(KeyEvent.VK_I)) {
                new MenuInventory(room);
            }

            if (keyManager.checkKey(KeyEvent.VK_Q)) {
                if (!keyLockSpace) {
                    increaseDayPart();
                }
                keyLockSpace = true;
            } else {
                keyLockSpace = false;
            }
        }

    }

    @Override
    public void draw(Graphics g) {
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
        for (int y = 0; y<placables.length; y++) {
            for (int x = 0; x<placables[0].length; x++) {
                Placable placable = placables[y][x];
                if (placable != null) {
                    placable.increaseDay();
                }
            }
        }

        money-=getDailyCost();
    }

    public int getDailyCost() {
        return (int) (Math.pow(Math.pow(placables.length, 2)/10, 1.2)+Math.pow(employees*4, 2))/5;
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
