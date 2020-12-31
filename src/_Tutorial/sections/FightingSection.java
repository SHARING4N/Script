package _Tutorial.sections;

import _Tutorial.script.RSUtil;
import org.powerbot.script.Condition;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.*;
import z.Con;
import z.T;

import java.util.Arrays;

public final class FightingSection extends RSUtil {

    public FightingSection() {
        instructor = ctx.npcs.select().name("Combat Instructor").peek();
    }

    public void Exec() {

        if (HasMessage("In this area you will find out about melee and ranged combat")) {
            Tile tile = new Tile(3107, 9509, 0);
            if (ctx.players.local().tile().distanceTo(tile) <= 2) {
                InteractNPC(instructor);
            } WalkToPos(tile);
            return;
        }

        if (HasMessage("Click on the flashing icon of a man")) {
            ctx.widgets.widget(548).component(55).click();
            return;
        }

        if (HasMessage("button with a shield and helmet")) {
            ctx.widgets.widget(548).component(55).click();
            ctx.widgets.widget(387).component(1).click();
            return;
        }

        if (HasMessage("Click your dagger to equip it")) {
            ctx.inventory.select().name("Bronze dagger").peek().interact("Equip");
            ctx.widgets.widget(84).component(3).component(11).click();
            return;
        }

        if (HasMessage("You're now holding your dagger")) {
            InteractNPC(instructor);
            return;
        }

        if (HasMessage("swapping your dagger for the sword and shield")) {
            ctx.widgets.widget(548).component(54).click();
            Item sowrd = ctx.inventory.select().name("Bronze sword").peek();
            Item shield = ctx.inventory.select().name("Wooden shield").peek();
            if (sowrd.valid()) sowrd.interact("Wield");
            if (shield.valid()) shield.interact("Wield");
            return;
        }

        if (HasMessage("Click on the flashing crossed swords icon")) {
            ctx.widgets.widget(548).component(51).click();
            return;
        }

        if (HasMessage("This is your combat interface.")) {
            Tile tile = new Tile(3111,9518, 0);
            if (ctx.players.local().tile().distanceTo(tile) <= 2) {
                handleObstacle(tile,"Gate", "Open");
            } WalkToPos(tile);
            return;
        }

        if (HasMessage("It's time to slay some rats!")) {
            Npc rat = FindFreeTarget("Giant rat");
            if (rat != null) {
                rat.interact("Attack");
            }
            return;
        }

        if (HasMessage("Pass through the gate and talk to the combat instructor")) {
            if (instructor.tile().matrix(ctx).reachable()) {
                Tile tile = instructor.tile();
                if (ctx.players.local().tile().distanceTo(tile) <= 2) {
                    InteractNPC(instructor);
                } WalkToPos(tile);
            }
            else handleObstacle(new Tile(3111,9518,0),"Gate", "Open");
            return;
        }

        if (HasMessage("Rat ranging")) {
            Tile tile = new Tile(3107,9512,0);
            if (ctx.players.local().tile().distanceTo(tile) > 2) {
                WalkToPos(tile);
            }
            ctx.widgets.widget(548).component(54).click();
            Item bow = ctx.inventory.select().name("Shortbow").peek();
            Item arrow = ctx.inventory.select().name("Bronze arrow").peek();
            if (bow.valid()) bow.interact("Wield");
            if (arrow.valid()) arrow.interact("Wield");

            Npc rat = FindFreeTarget("Giant rat");
            if (rat != null) {
                rat.interact("Attack");
                Condition.sleep(3000);
                Condition.wait(() -> !ctx.players.local().interacting().valid(), 1000, 30);
            }
            return;
        }

        if (HasMessage("click on the indicated ladder")) {
            Tile tile = new Tile(3111,9525,0);
            if (ctx.players.local().tile().distanceTo(tile) <= 2) {
                handleObstacle(tile,"Ladder","Climb");
            } WalkToPos(tile);
            return;
        }
    }
}
