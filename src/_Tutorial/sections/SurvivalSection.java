package _Tutorial.sections;

import _Tutorial.script.RSUtil;
import _Tutorial.script.TutorialIsland;
import org.powerbot.script.Area;
import org.powerbot.script.Condition;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.*;

import javax.swing.text.Utilities;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public final class SurvivalSection extends RSUtil {

    public SurvivalSection() {
        instructor = ctx.npcs.select().name("Survival Expert").peek();
    }

    public void Exec() {

        if (HasMessage("Moving around")) {
            Tile tile = new Tile(3101, 3095, 0);
            if (ctx.players.local().tile().distanceTo(tile) <= 2) {
                InteractNPC(instructor);
            } WalkToPos(tile);
            return;
        }

        if (HasMessage("open your inventory")) {
            ctx.widgets.widget(548).component(54).click();
            return;
        }

        if (HasMessage("Fishing")) {
            Npc fishSpot = ctx.npcs.select().name("Fishing spot").nearest().peek();
            ctx.camera.turnTo(fishSpot.tile());
            ctx.camera.pitch(0);
            fishSpot.interact("Net");
            ctx.camera.angle(0);
            ctx.camera.pitch(99);
            return;
        }

        if (HasMessage("see your skills menu")) {
            Component skillB = ctx.widgets.widget(548).component(52);
            if (skillB.visible()) {
                skillB.click();
                InteractNPC(instructor);
            }
            return;
        }

        if (HasMessage("Woodcutting")) {
            ctx.objects.select().name("Tree").nearest().peek().interact("Chop down");
            return;
        }

        if (HasMessage("Firemaking")) {
            Tile tile = getEmptyTile();
            tile.matrix(ctx).interact("Walk here");
            Condition.sleep(5000);
            ctx.inventory.select().name("Tinderbox").peek().interact("Use");
            ctx.inventory.select().name("Logs").peek().interact("Use");
            return;
        }

        if (HasMessage("Cooking")) {
            ctx.inventory.select().id(2514).peek().interact("Use");
            ctx.objects.select(1).name("Fire").peek().interact("Use");
            return;
        }

        if (HasMessage("Moving on")) {
            Tile tile = new Tile(3090,3092);
            WalkToPos(tile);
            handleObstacle(tile,"Gate", "Open");
            return;
        }
    }

    private Tile getEmptyTile() {
        Area area = new Area(
                new Tile(3100, 3097, 0),
                new Tile(3104, 3097, 0),
                new Tile(3104, 3094, 0),
                new Tile(3100, 3094, 0),
                new Tile(3100, 3097, 0)
        );
        for (Tile tile: area.tiles()) {
            boolean canFire = true;
            for (GameObject a : ctx.objects.select(tile,0))
            {
                if (a.name().equals("Fire")) canFire = false;
                else if (a.name().equals("Logs")) canFire = false;
                else if (a.name().equals("Ashes")) canFire = false;
            }
            if (canFire) return tile;
        }
        Condition.sleep(30000);
        return getEmptyTile();
    }

}
