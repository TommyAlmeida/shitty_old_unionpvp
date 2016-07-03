package eu.union.dev.utils.globals;

import org.bukkit.entity.Player;

import java.util.Comparator;

/**
 * Created by Fentis on 15/06/2016.
 */
public class CompassCompare implements Comparator<Player>{

    private Player player;

    public CompassCompare(Player player) {
        this.player = player;
    }

    @Override
    public int compare(Player target1, Player target2) {
        return compare(
                target1.getLocation().distance(player.getLocation()),
                target2.getLocation().distance(player.getLocation()));
    }

    private int compare(double a, double b) {
        return a > b ? 1 : a > b ? -1 : 0;
    }
}
