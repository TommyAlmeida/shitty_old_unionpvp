package eu.union.dev.commands.location;

import eu.union.dev.engine.storage.ConfigManager;
import eu.union.dev.utils.globals.Messages;
import eu.union.dev.utils.globals.Perms;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

public class SetSpawn implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(!(commandSender instanceof Player)){
            return true;
        }

        Player p = (Player) commandSender;

        if(command.getName().equalsIgnoreCase("setspawn")){
            if(Perms.isStaff(p)){
                try {
                    ConfigManager.getInstance().setLocation("Spawn", p.getLocation());
                    p.sendMessage(Messages.PREFIX.toString() + " §7Spawn has been set.");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
}
