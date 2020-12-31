package _Tutorial.sections;

import _Tutorial.script.RSUtil;
import org.powerbot.script.Condition;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.*;
import z.T;

public final class CookingSection extends RSUtil {

    public CookingSection() {
        instructor = ctx.npcs.select().name("Master Chef").peek();
    }

    public void Exec() {
        Item bread = ctx.inventory.select().name("Bread").peek();

        if (HasMessage("Moving on") && !bread.valid()) {
            Tile tile = new Tile(3079,3084,0);
            if (ctx.players.local().tile().distanceTo(tile) <= 2) {
                handleObstacle(tile,"Door", "Open");
            } WalkToPos(tile);
            return;
        }

        if (HasMessage("Talk to the chef indicated")) {
            InteractNPC(instructor);
            return;
        }

        if (HasMessage("Making doug")) {
            ctx.inventory.select().name("Pot of flour").peek().interact("Use");
            ctx.inventory.select().name("Bucket of water").peek().interact("Use");
            return;
        }

        if (HasMessage("Now you have made the dough")) {
            ctx.objects.select().name("Range").nearest().peek().interact("Cook");
            return;
        }

        if (HasMessage("Moving on") && bread.valid()) {
            handleObstacle(new Tile(3073,3090),"Door", "Open");
            return;
        }
    }
}
