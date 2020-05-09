package de.cg.fg.ctrl;

import de.cg.cgge.files.FileContents;
import de.cg.cgge.files.GameFile;
import de.cg.cgge.gui.Sprite;
import de.cg.cgge.io.Sound;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.*;
import java.io.IOException;

public class Ressources {

    public static FileContents fcStartvals = getContents("assets//cfg//startvals.data");
    public static FileContents fcColors = getContents("assets//cfg//colors.data");

    public static final Font fontBtnMainGame = new Font("Roboto Mono", Font.BOLD, 24);
    public static final Font fontMouseInfo = new Font("Roboto Mono", Font.PLAIN, 14);

    public static final Sprite spriteFieldEmpty = new Sprite("assets//fields//empty.png", 32, 32, 0);
    public static final Sprite spriteFieldStage1 = new Sprite("assets//fields//stage1.png", 32, 32, 0);
    public static final Sprite spriteFieldStage2 = new Sprite("assets//fields//stage2.png", 32, 32, 0);
    public static final Sprite spriteFieldStage3 = new Sprite("assets//fields//stage3.png", 32, 32, 0);
    public static final Sprite spriteFlourMachine = new Sprite("assets//flour_machine.png", 32, 32, 0);

    public static final int startMoney = Integer.parseInt(fcStartvals.getFromKeyword("money"));
    public static final int startMaxDayPart = Integer.parseInt(fcStartvals.getFromKeyword("max day part"));

    public static final Sound soundClick = loadSound("assets//sounds//click.wav");
    public static final Sound soundInventoryOpen = loadSound("assets//sounds//inventory_open.wav");
    public static final Sound soundInventoryClose = loadSound("assets//sounds//inventory_close.wav");
    public static final Sound soundPlace = loadSound("assets//sounds//place.wav");
    public static final Sound soundHarvest = loadSound("assets//sounds//harvest.wav");
    public static final Sound soundPlant = loadSound("assets//sounds//harvest.wav");

    public static final Color colorBG = Color.decode(fcColors.getFromKeyword("background"));
    public static final Color colorMoneyLabel = Color.decode(fcColors.getFromKeyword("money label"));
    public static final Color colorField = Color.decode(fcColors.getFromKeyword("field"));
    public static final Color colorButtons = Color.decode(fcColors.getFromKeyword("buttons"));

    private static FileContents getContents(String filename) {
        try {
            var gf = new GameFile(filename);
            gf.loadToMemory();
            return gf.getContents();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Sound loadSound(String path) {
        try {
            return new Sound(path);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }

        return null;
    }

}
