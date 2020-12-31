package _Tutorial.sections;

import _Tutorial.script.RSUtil;
import org.powerbot.script.Condition;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.*;
import z.T;

public final class PathToQuestSection extends RSUtil {

    public void Exec() {
        Tile tile = new Tile(3086,3126,0);
        if (ctx.players.local().tile().distanceTo(tile) <= 2) {
            handleObstacle(tile,"Door", "Open");
        } else WalkToPos(tile);
    }
}
