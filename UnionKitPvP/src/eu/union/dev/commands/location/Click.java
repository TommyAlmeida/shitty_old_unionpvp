package eu.union.dev.commands.location;

import eu.union.dev.PvPMain;
import eu.union.dev.api.Packets;
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
public class Click implements CommandExecutor{

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(!(commandSender instanceof Player)){
            return true;
        }

        Player p = (Player) commandSender;

        if(command.getName().equalsIgnoreCase("click")){
            Location loc = ConfigManager.getInstance().getLocation("Click");

            Packets.getAPI().sendActionBar(p, "§9You need to wait §c" + 5 + " seconds §9to teleport.");

            Bukkit.getScheduler().scheduleSyncDelayedTask(PvPMain.getInstance(), new Runnable() {
                @Override
                public void run() {
                    p.teleport(loc);
                    Packets.getAPI().sendTitle(p,"§aWelcome to Click","",3,4,5);
                    //Adicionar items ao player etc...
                }
            }, 20*5);
        }
        return false;
    }
}
