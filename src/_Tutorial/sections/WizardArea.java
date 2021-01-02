package _Tutorial.sections;

import _Tutorial.script.RSUtil;
import org.powerbot.script.Condition;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.*;
import z.T;

public final class WizardArea extends RSUtil {

    public WizardArea() { instructor = ctx.npcs.select().name("Magic Instructor").peek();  }

    public void Exec() {

        if (HasMessage("talk with the magic instructor")) {
            Tile tile = new Tile(3141,3089,0);
            if (ctx.players.local().tile().distanceTo(tile) <= 2) {
                InteractNPC(instructor);
                return;
            } else WalkToPos(tile);
        }

        if (HasMessage("Open up the magic interface")) {
            ctx.widgets.widget(548).component(57).click();
        }

        if (HasMessage("This is your magic interface.")) {
            InteractNPC(instructor);
        }

        if (HasMessage("Look for the Wind Strike spell")) {
            for (Magic.MagicSpell spell : ctx.magic.book().spells()){
                if (spell.level() == 1) {
                    ctx.magic.cast(spell);
                    break;
                }
            }
            Npc chicken = FindFreeTarget("Chicken");
            if (chicken != null) chicken.interact("Cast");
        }

        if (HasMessage("teleport you to Lumbridge")) {
            InteractNPC(instructor);
        }

        Component comp;
        if ((comp = ctx.widgets.widget(219).component(1).component(0)).visible() ) {
            if (comp.text().contains("Do you want to go to the mainland")) {
                ctx.input.send("1");
                return;
            }
            if (comp.text().contains("Select an Option")) {
                ctx.input.sendln("3");
                return;
            }
        }

    }
}
