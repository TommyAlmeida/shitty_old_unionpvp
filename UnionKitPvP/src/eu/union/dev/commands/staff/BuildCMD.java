package eu.union.dev.commands.staff;

import eu.union.dev.utils.Messages;
import eu.union.dev.utils.Perms;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class BuildCMD implements CommandExecutor {

    public static ArrayList<Player> build = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!(commandSender instanceof Player)){
           return true;
        }

        Player p = (Player) commandSender;

        if(Perms.isStaff(p)){
            if(build.contains(p)){
                build.remove(p);
                p.sendMessage(Messages.PREFIX.toString() + " §7Build has been disabled.");
            }else{
                build.add(p);
                p.sendMessage(Messages.PREFIX.toString() + " §7Build has been enabled.");
            }
        }

        return false;
    }
}
