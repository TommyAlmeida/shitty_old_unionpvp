package eu.union.dev.commands.location;

import eu.union.dev.PvPMain;
import eu.union.dev.api.Packets;
import eu.union.dev.engine.managers.KitManager;
import eu.union.dev.engine.storage.ConfigManager;
import eu.union.dev.utils.globals.Util;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Spawn implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!(commandSender instanceof Player)) {
            return true;
        }

        Player p = (Player) commandSender;

        if (command.getName().equalsIgnoreCase("spawn")) {
            Location loc = ConfigManager.getInstance().getLocation("Spawn");

            Packets.getAPI().sendActionBar(p, "§9You need to wait §c" + 5 + " seconds §9to teleport.");

            Bukkit.getScheduler().scheduleSyncDelayedTask(PvPMain.getInstance(), new Runnable() {
                int seconds = 0;

                @Override
                public void run() {
                    KitManager km = KitManager.getManager();
                    if (km.usingKit(p))
                        km.removeKit(p);
                    p.teleport(loc);
                    p.getInventory().clear();
                    Util.getInstance().readyPlayer(p);
                    Util.getInstance().buildJoinIcons(p);
                }
            }, 20 * 5);
        }
        return false;
    }
}
