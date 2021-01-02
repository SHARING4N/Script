package _Tutorial.sections;


import _Tutorial.script.RSUtil;
import org.powerbot.script.Condition;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.Component;
import org.powerbot.script.rt4.Npc;

import java.util.Random;

public final class RuneScapeGuideArea extends RSUtil {


    public RuneScapeGuideArea() {
        instructor = ctx.npcs.select().name("Gielinor Guide").peek();
    }

    public void Exec() {

        Component comp1 = ctx.widgets.widget(558).component(17).component(9);
        if (comp1.visible()) {
            WriteName();
            return;
        }

        Component comp2 = ctx.widgets.widget(679).component(68);
        if (comp2.visible()) {
            comp2.click();
            Condition.wait(() -> !ctx.widgets.widget(679).component(68).visible(),1000, 30);
            return;
        }

        if (HasMessage("Getting started")) {
            InteractNPC(instructor);

            Condition.wait(() -> {
                for (Component comp : ctx.widgets.widget(219).component(1).components()) {
                    if (comp.visible() && comp.text().contains("experienced player")) {
                        comp.click();
                        return true;
                    }
                }
                return false;
            },1000, 30);
            return;
        }

        if (HasMessage("Settings menu")) {
            ctx.widgets.widget(548).component(38).click();
            InteractNPC(instructor);
            return;
        }

        if (HasMessage("Moving on")) {
            handleObstacle(new Tile(3097, 3107, 0),"Door", "Open");
            return;
        }


    }

    private void WriteName() {

        // Look up name
        ctx.widgets.widget(558).component(17).click();

        Condition.wait(() -> ctx.widgets.widget(162).component(44).text().contains("display name"),1000, 30);

        // write name
        String name = GenerateName();
        ctx.input.sendln(name);

        // Wait status message
        success = Condition.wait(() -> ctx.widgets.widget(558).component(12).visible(),1000, 30);

        if (success) {
            success = Condition.wait(() -> ctx.widgets.widget(558).component(12).text().contains("00ff00"),1000, 30);
            if (success) {
                ctx.widgets.widget(558).component(18).click();
            } else WriteName();
        }
    }

    private String GenerateName() {
        String letters = "ABCDEFGHIJKLMNOPQRSTWXYZabcdefghijklmnopqrstuvwxyz1234567890";
        StringBuilder name = new StringBuilder();
        Random rnd = new Random();
        for (int a = 0; a <= 12; a++) name.append(letters.charAt(rnd.nextInt(letters.length())));
        return name.toString();
    }

}