package de.cg.fg.objects.ui.menus;

import de.cg.cgge.game.Room;
import de.cg.fg.ctrl.Main;
import de.cg.fg.ctrl.Ressources;
import de.cg.fg.objects.game.placables.Animal;
import de.cg.fg.objects.ui.UIButton;
import de.cg.fg.objects.ui.UILabel;
import de.cg.fg.objects.ui.UIMenu;
import de.cg.fg.objects.ui.UINotification;

import java.awt.*;
import java.awt.event.MouseEvent;

public class MenuAnimalInfo extends UIMenu {

    private Animal animal;

    private UIButton btnFeed;
    private UIButton btnButcher;
    private UIButton btnCollect;

    private UILabel lblTitle;
    private UILabel lblCollect;
    private UILabel lblAge;
    private UILabel lblHungry;

    public MenuAnimalInfo(Room room, Animal animal) {
        super(room, 200, 200, 400, 400);

        int x = (int)this.x;
        int y = (int)this.y;

        this.animal = animal;

        lblTitle   = new UILabel(room, x+10, y+30, animal.getClass().getSimpleName(), Ressources.fontBtnMainGame, Ressources.colorButtons);
        lblAge     = new UILabel(room, x+10, y+60, animal.getAge() + " days old", Ressources.fontMouseInfo, Ressources.colorButtons);
        lblCollect = new UILabel(room, x+10, y+90, (animal.isHasItem() ?  "Has an item to collect!" : "Has no item to collect"), Ressources.fontMouseInfo, Ressources.colorButtons);
        lblHungry  = new UILabel(room, x+10, y+120, "Hungry? " + (animal.isNeedsFood() ? "Yes!" : "No"), Ressources.fontMouseInfo, Ressources.colorButtons);

        btnFeed    = new UIButton(room, x+30, y+180, 200, 50, Ressources.fontBtnMainGame, "Feed " + animal.getEats(), Color.BLACK, Ressources.colorButtons, UIButton.ButtonType.ANIMAL_FEED);
        btnCollect = new UIButton(room, x+30, y+240, 200, 50, Ressources.fontBtnMainGame, "Collect Item", Color.BLACK, Ressources.colorButtons, UIButton.ButtonType.ANIMAL_COLLECT);
        btnButcher = new UIButton(room, x+30, y+300, 200, 50, Ressources.fontBtnMainGame, "Butcher", Color.BLACK, Ressources.colorButtons, UIButton.ButtonType.ANIMAL_BUTCHER);

        addUIObject(lblTitle);
        addUIObject(lblAge);
        addUIObject(lblCollect);
        addUIObject(lblHungry);
        addUIObject(btnButcher);
        addUIObject(btnCollect);
        addUIObject(btnFeed);
    }

    @Override
    public void step() {
        if (btnFeed.fetchClick()) {
            var result = animal.feed();
            if (result) {
                new UINotification(room, animal.getClass().getSimpleName() + " was fed!", Color.GREEN);
                die();
            } else {
                new UINotification(room, "Couldn't feed animal", Color.RED);
            }
        }


        if (btnButcher.fetchClick()) {
            animal.butcher();
            new UINotification(room, animal.getClass().getSimpleName() + " was butchered!", Color.GREEN);
            die();
        }


        if (btnCollect.fetchClick()) {
            var res = animal.collectItem();
            if (res) {
                new UINotification(room, animal.getProduces().name() + " was collected", Color.GREEN);
                die();
            } else {
                new UINotification(room, "Nothing to collect", Color.RED);
            }

        }

    }
}
