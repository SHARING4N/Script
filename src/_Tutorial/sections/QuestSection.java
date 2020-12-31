package _Tutorial.sections;

import _Tutorial.script.RSUtil;
import org.powerbot.script.Condition;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.*;
import z.T;

public final class QuestSection extends RSUtil {

    public QuestSection() {
        instructor = ctx.npcs.select().name("Quest Guide").peek();
    }

    public void Exec() {
        if (HasMessage("Just talk to the Quest Guide")) {
            InteractNPC(instructor);
            return;
        }

        if (HasMessage("Click on the flashing icon")) {
            Component questB = ctx.widgets.widget(548).component(53);
            if (questB.visible()) {
                questB.click();
            }
            return;
        }

        if (HasMessage("Talk to the quest guide again")) {
            InteractNPC(instructor);
            return;
        }
        if (HasMessage("Moving on")) {
            Tile tile = new Tile(3088,3120,0);
            if (ctx.players.local().tile().distanceTo(tile) <= 2) {
                handleObstacle(tile,"Ladder", "Climb");
            } WalkToPos(tile);
            return;
        }
    }
}
