package _Tutorial.sections;

import _Tutorial.script.RSUtil;
import org.powerbot.script.Condition;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.Component;
import org.powerbot.script.rt4.Npc;
import z.T;

public final class MiningSection extends RSUtil {

    public MiningSection() {
        instructor = ctx.npcs.select().name("Mining Instructor").peek();
    }

    public void Exec() {

        if (HasMessage("mining instructor will help you")) {
            Tile tile = new Tile(3079, 9502, 0);
            if (ctx.players.local().tile().distanceTo(tile) <= 2) {
                InteractNPC(instructor);
                Condition.sleep(5000);
            } WalkToPos(tile);
            return;
        }

        if (HasMessage("try mining some tin")) {
            ctx.objects.select(new Tile(3077, 9503,0),3).name("Rocks").nearest().peek().interact("Mine");
            return;
        }

        if (HasMessage("you just need some copper")) {
            ctx.objects.select(new Tile(3084, 9501,0),3).name("Rocks").nearest().peek().interact("Mine");
            return;
        }

        if (HasMessage("smelt these into a bronze bar")) {
            Tile tile = new Tile(3079, 9497,ctx.players.local().tile().floor());
            handleObstacle(tile,"Furnace","Use");
            return;
        }

        if (HasMessage("how to make it into a weapon")) {
            InteractNPC(instructor);
            return;
        }

        if (HasMessage("click on the anvil")) {
            ctx.objects.select(new Tile(3083, 9499,0),1).name("Anvil").nearest().peek().interact("Smith");
            return;
        }

        if (HasMessage("select the dagger to continue")) {
            Component dagger = ctx.widgets.widget(312).component(9);
            if (dagger.visible()) dagger.click();
            return;
        }

        if (HasMessage("Moving on")) {
            Tile tile = new Tile(3094,9503,0);
            if (ctx.players.local().tile().distanceTo(tile) <= 2) {
                handleObstacle(new Tile(3094,9503,0),"Gate", "Open");
            } WalkToPos(tile);
            return;
        }

    }

}
