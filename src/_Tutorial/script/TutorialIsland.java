package _Tutorial.script;


import _Tutorial.sections.*;
import org.powerbot.script.*;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Component;

import java.awt.*;

@org.powerbot.script.Script.Manifest(
        name = "Tutorial",
        description = "",
        version = "1.0.0"
)

public class TutorialIsland extends PollingScript<ClientContext>  implements PaintListener  {

    private int getProgess() {
        int val = -1;
        Component current = ctx.widgets.widget(614).component(18);

        if (current.visible()) {
            val = current.width();
        }
        return val;
    }


    private boolean TutorialCompleted() {
        return getProgess() == -1;
    }

    @Override
    public void start() {
        System.out.println("Script Started!");

        new Thread(() -> {
            while (!TutorialCompleted()) {
                try {
                    ctx.input.send("{VK_SPACE}");
                } catch (Exception ignore) {}
                Condition.sleep(100);
            }
        }).start();
    }

    @Override
    public void poll() {

        if (TutorialCompleted()) {
            ctx.game.logout();
            if (ctx.game.loginState() == 0) ctx.controller.stop();
            return;
        }

        if (ctx.game.resizable()) new RSUtil().SetFixedMode();

        ctx.camera.pitch(99);

        switch (getProgess()) {
            case 0:
                new RuneScapeGuideArea().Exec();
                break;
            case 31:
            case 47:
                new SurvivalArea().Exec();
                break;
            case 63:
            case 80:
                new CookingArea().Exec();
                break;
            case 95:
                new PathToQuestArea().Exec();
                break;
            case 111:
                new QuestArea().Exec();
                break;
            case 127:
            case 143:
                new MiningArea().Exec();
                break;
            case 160:
            case 175:
            case 191:
                new FightingArea().Exec();
                break;
            case 223:
            case 240:
                new BankArea().Exec();
                break;
            case 255:
            case 271:
                new PriestArea().Exec();
                break;
            case 287:
            case 320:
                new WizardArea().Exec();
                break;
        }
        Condition.sleep(1000);
    }

    @Override
    public void stop() {
        System.out.println("Script Stopped!");
    }


    @Override
    public void repaint(Graphics graphics) {
        long milsec = getRuntime();
        int seconds = (int) (milsec / 1000) % 60 ;
        int minutes = (int) ((milsec / (1000*60)) % 60);
        int hours   = (int) ((milsec / (1000*60*60)) % 24);
        graphics.setFont(new Font("Helvetica", 0, 14));
        graphics.setColor(new Color(0, 255, 255,255));
        graphics.drawString("Time running: " + hours+":"+String.format("%02d",minutes)+":"+String.format("%02d",seconds), 8, 60);
    }
}
