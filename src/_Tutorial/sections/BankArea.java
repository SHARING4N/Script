package _Tutorial.sections;

import _Tutorial.script.RSUtil;
import org.powerbot.script.Condition;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.*;
import z.T;

public final class BankArea extends RSUtil {

    public BankArea() {
        instructor = ctx.npcs.select().name("Account Guide").peek();
    }

    public void Exec() {

        if (HasMessage("the Bank of Gielinor")) {
            Tile tile = new Tile(3121,3123,0);
            if (ctx.players.local().tile().distanceTo(tile) <= 2) {
                ctx.bank.open();
                ctx.bank.close();
            } else WalkToPos(tile);
            return;
        }

        if (HasMessage("click on the indicated poll booth")) {
            ctx.objects.select().name("Poll booth").peek().interact("Use");
            return;
        }

        if (HasMessage("move on through the door indicated")) {
            Tile tile = new Tile(3124,3124,0);
            if (ctx.players.local().tile().distanceTo(tile) <= 2) {
                handleObstacle(tile,"Door","Open");
            } else WalkToPos(tile);
            return;
        }

        if (HasMessage("The guide here will tell you all about your account")) {
            InteractNPC(instructor);
            return;
        }

        if (HasMessage("open your Account Management menu")) {
            ctx.widgets.widget(548).component(35).click();
            return;
        }

        if (HasMessage("Talk to the Account Guide to learn more")) {
            InteractNPC(instructor);
            return;
        }

        if (HasMessage("Continue through the next door.")) {
            handleObstacle(new Tile(3129,3124,0),"Door","Open");
            return;
        }

    }
}
