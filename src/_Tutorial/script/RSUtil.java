package _Tutorial.script;

import org.powerbot.script.Condition;
import org.powerbot.script.MenuCommand;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.*;
import org.powerbot.script.rt4.Component;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class RSUtil extends TutorialIsland {

    public boolean success = false;
    public Npc instructor;

    public void SetFixedMode() {

        Condition.wait(() -> ctx.game.loggedIn() && ctx.players.local().valid(), 5000, 1000);

        success = false;
        List<Component> comps = ctx.components.get();
        for (Component c : comps) {
            if (c.textureId() == 908 && c.hover()) {
                for (MenuCommand a : ctx.menu.commands()) {
                    if (a.action.contains("Settings")) {
                        ctx.input.click(true);
                        success = true;
                        Condition.sleep(1000);
                        break;
                    }
                }
            }
        }
        if (!success) return;
        Component screenB = ctx.widgets.widget(116).component(103);
        if (screenB.visible()) {
            screenB.click();
            Condition.sleep(1000);
        }
        Component optionsB = ctx.widgets.widget(116).component(12).component(0);
        if (optionsB.visible()) {
            optionsB.click();
            Condition.sleep(1000);
        }
        Component fixedB = ctx.widgets.widget(116).component(37).component(1);
        if (fixedB.visible()) {
            fixedB.click();
            Condition.sleep(1000);
        }
    }

    public void InteractNPC(Npc npc) {
        if (!npc.valid()) return;
        ctx.camera.pitch(0);
        ctx.camera.turnTo(npc.tile());
        success = false;
        int a = 0;
        while (a < 10) {
            a++;
            npc.interact("Talk-to");
            success = Condition.wait(() -> npc.interacting().equals(ctx.players.local()),50, 20*20);
            if (success) a = 10;
        }
        ctx.camera.angle(0);
        ctx.camera.pitch(99);
    }

    public void handleObstacle(Tile t, String name, String action) {
        ctx.camera.pitch(0);
        ctx.camera.turnTo(t);
        Player p = ctx.players.local();
        GameObject obstacle = ctx.objects.select(t,1).nearest().name(name).peek();
        obstacle.interact((f)-> f.action.contains(action) );
        if (ctx.game.crosshair() == Game.Crosshair.ACTION) {
            success = Condition.wait(() -> p.animation() == -1 && !p.inMotion(), 50, 10*20);
        }
        if (!success) handleObstacle(t,name,action);
        else {
            ctx.camera.angle(0);
            ctx.camera.pitch(99);
        }
    }

    public boolean HasMessage(String str) {
        Components comps = ctx.components.textContains(str);
        if (comps.size() == 1) {
            return true;
        } else {
            Component comp = ctx.widgets.widget(263).component(1).component(0);
            if (comp.visible()) {
                return comp.text().contains(str);
            }
        }

        return false;
    }

    // Used because webwalkingservice is alot of errors
    public boolean WalkToPos(Tile finalPos) {
        List<Tile> tiles = new ArrayList<>();
        Tile oldTile = ctx.movement.findPath(finalPos).start().tile();
        tiles.add(oldTile);
        Tile newTile = ctx.movement.findPath(finalPos).next().tile();
        while (!oldTile.equals(newTile) && newTile.matrix(ctx).valid()) {
            tiles.add(newTile);
            oldTile = newTile;
            newTile = ctx.movement.findPath(finalPos).next().tile();
        }
        tiles.add(ctx.movement.findPath(finalPos).end().tile());

        success = true;
        for (Tile t : tiles) {
            if (t.matrix(ctx).valid() && t.matrix(ctx).reachable() && ctx.players.local().tile().distanceTo(t) > 2) {
                if (!ctx.movement.closestOnMap(t).equals(t)) {
                    Tile nT = ctx.movement.closestOnMap(t);
                    ctx.movement.step(nT);
                    Condition.wait(() -> ctx.players.local().tile().distanceTo(nT) <= 2,1000,15);
                    return WalkToPos(finalPos);
                }
                ctx.movement.step(t);
                Condition.wait(() -> ctx.players.local().tile().distanceTo(t) <= 2,1000,15);
                success = true;
            } else success = false;
        }
        return success;
    }

    public Npc FindFreeTarget(String name) {
        BasicQuery<Npc> npcs = ctx.npcs.select().name(name).nearest();
        for (Npc npc : npcs) {
            if (npc.valid() && !npc.healthBarVisible() && !npc.interacting().valid()) {
                return npc;
            }
        }
        return null;
    }
}
