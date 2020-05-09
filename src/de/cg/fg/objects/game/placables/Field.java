package de.cg.fg.objects.game.placables;

import de.cg.cgge.game.Room;
import de.cg.fg.enums.CropType;
import de.cg.fg.ctrl.Ressources;
import de.cg.fg.objects.game.Placable;

public class Field extends Placable {

    private int currentDay = 0;
    private int daysWithoutWater = 0;
    private int daysWithFertilization = 0;

    private CropType cropType = CropType.EMPTY;

    private boolean needsWater = true;
    private boolean isFertilized = false;
    private boolean autoWatered = false;

    public Field(Room room, int fx, int fy) {
        super(room, Ressources.spriteFieldEmpty, fx, fy);
    }

    public CropType getCropType() {
        return cropType;
    }

    public void setCropType(CropType cropType) {
        this.cropType = cropType;
    }

    @Override
    public void increaseDay() {
        currentDay++;
        if (needsWater && cropType != CropType.EMPTY && !autoWatered)
            daysWithoutWater++;
        isFertilized = false;
        needsWater = !autoWatered;
        updateSprite();
    }

    public void updateSprite() {
        if (cropType != CropType.EMPTY) {
            int goal = cropType.getGrowthDuration();
            if (currentDay < goal / 2) {
                this.sprite = Ressources.spriteFieldStage1;
            } else if (currentDay >= goal / 2 && currentDay < goal) {
                this.sprite = Ressources.spriteFieldStage2;
            } else if (currentDay >= goal) {
                this.sprite = Ressources.spriteFieldStage3;
            }
        } else {
            this.sprite = Ressources.spriteFieldEmpty;
        }

    }

    public int getCurrentDay() {
        return currentDay;
    }

    public void setCurrentDay(int currentDay) {
        this.currentDay = currentDay;
    }

    public int getDaysWithoutWater() {
        return daysWithoutWater;
    }

    public void setDaysWithoutWater(int daysWithoutWater) {
        this.daysWithoutWater = daysWithoutWater;
    }

    public int getDaysWithFertilization() {
        return daysWithFertilization;
    }

    public void setDaysWithFertilization(int daysWithFertilization) {
        this.daysWithFertilization = daysWithFertilization;
    }

    public boolean needsWater() {
        return needsWater;
    }

    public void setNeedsWater(boolean needsWater) {
        this.needsWater = needsWater;
    }

    public boolean isFertilized() {
        return isFertilized;
    }

    public void setFertilized(boolean fertilized) {
        isFertilized = fertilized;
    }

    public boolean isNeedsWater() {
        return needsWater;
    }

    public boolean isAutoWatered() {
        return autoWatered;
    }

    public void setAutoWatered(boolean autoWatered) {
        this.autoWatered = autoWatered;
    }
}
