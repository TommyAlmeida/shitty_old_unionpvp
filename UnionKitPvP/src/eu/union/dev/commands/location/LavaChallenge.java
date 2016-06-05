package eu.union.dev.commands.location;

import eu.union.dev.PvPMain;
import eu.union.dev.api.Packets;
import eu.union.dev.engine.storage.ConfigManager;
import eu.union.dev.utils.globals.Util;
import eu.union.dev.listeners.mechanics.BlockListener;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Fentis on 21/05/2016.
 */
public class LavaChallenge implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!(commandSender instanceof Player)) {
            return true;
        }

        Player p = (Player) commandSender;

        if (command.getName().equalsIgnoreCase("lavachallenge")) {
            Location loc = ConfigManager.getInstance().getLocation("LavaChallenge");

            Packets.getAPI().sendActionBar(p, "§9You need to wait §c" + 5 + " seconds §9to teleport.");

            Bukkit.getScheduler().scheduleSyncDelayedTask(PvPMain.getInstance(), new Runnable() {
                @Override
                public void run() {
                    p.teleport(loc);
                    Packets.getAPI().sendTitle(p, "§aWelcome to LavaChallenge", "", 3, 4, 5);
                    p.getInventory().clear();
                    p.getInventory().setArmorContents(null);
                    Util.getInstance().giveSoups(p);
                    BlockListener.lc.add(p.getName());
                }
            }, 20 * 5);
        }
        return false;
    }
}
