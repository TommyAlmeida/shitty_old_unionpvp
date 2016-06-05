package eu.union.dev.commands.staff;

import eu.union.dev.utils.globals.Perms;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClearChatCMD implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!(commandSender instanceof Player)){
            return true;
        }

        Player p = (Player) commandSender;

        if(Perms.isStaff(p)){
            for(int i = 0; i < 100; i++){
                Bukkit.broadcastMessage(" ");
            }
            Bukkit.broadcastMessage("§aChat has been cleared by " + p.getName());
        }

        return true;
    }
}
