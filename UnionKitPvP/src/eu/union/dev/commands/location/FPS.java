package eu.union.dev.commands.location;

import eu.union.dev.PvPMain;
import eu.union.dev.api.Packets;
import eu.union.dev.engine.managers.KitManager;
import eu.union.dev.engine.storage.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Fentis on 21/05/2016.
 */
public class FPS implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!(commandSender instanceof Player)) {
            return true;
        }

        Player p = (Player) commandSender;

        if (command.getName().equalsIgnoreCase("fps")) {
            Packets.getAPI().sendActionBar(p, "§9You need to wait §c" + 5 + " seconds §9to teleport.");

            Bukkit.getScheduler().scheduleSyncDelayedTask(PvPMain.getInstance(), () -> {
                Location loc = ConfigManager.getInstance().getLocation("FPS");
                p.teleport(loc);
                Packets.getAPI().sendTitle(p, "§aWelcome to FPS", "§4No times! Or will be banned!", 3, 4, 5);
                KitManager km = KitManager.getManager();
                km.readyPlayer(p);
                km.applyKit(p, km.getKitByName("pvp"));
            }, 20 * 5);
        }
        return false;
    }
}
