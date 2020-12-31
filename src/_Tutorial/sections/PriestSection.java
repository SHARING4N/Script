package _Tutorial.sections;

import _Tutorial.script.RSUtil;
import org.powerbot.script.Condition;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.*;
import z.T;

public final class PriestSection extends RSUtil {

    public PriestSection() {
        instructor = ctx.npcs.select().name("Brother Brace").peek();
    }

    public void Exec() {

        if (HasMessage("talk to the monk")) {
            Tile tile = new Tile(3124,3106,0);
            if (ctx.players.local().tile().distanceTo(tile) <= 2) {
                InteractNPC(instructor);
            } else WalkToPos(tile);
            return;
        }

        if (HasMessage("open the Prayer menu")) {
            ctx.widgets.widget(548).component(56).click();
            return;
        }

        if (HasMessage("tell you about prayers")) {
            InteractNPC(instructor);
            return;
        }

        if (HasMessage("open your friends and ignore lists")) {
            ctx.widgets.widget(548).component(36).click();
            return;
        }

        if (HasMessage("Speak with Brother Brace to learn more")) {
            InteractNPC(instructor);
            return;
        }

        if (HasMessage("Pass through the door")) {
            handleObstacle(new Tile(3122,3103,0),"Door","Open");
            return;
        }
    }
}
