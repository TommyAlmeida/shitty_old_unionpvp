package eu.union.dev.commands;

import eu.union.dev.utils.globals.Messages;
import eu.union.dev.utils.globals.Perms;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReportCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            return true; //Retorna
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            player.sendMessage(Messages.PREFIX.toString() + " §cUse: /report <text>");
            return true;
        }

        String message = "";

        for(int i = 1; i != args.length; i++) {
            message += args[i] + " ";
        }

        if(message.length() == 0) {
            player.sendMessage(Messages.PREFIX.toString() + " §cYour report must have more than 10 characters");
            return true;
        }

        for(Player online : Bukkit.getOnlinePlayers()){
            if(Perms.isStaff(online)){
                online.sendMessage(Messages.PREFIX.toString() + " §a§ Report from §b" + player.getName() + ": §c" + message);
            }
        }

        return true;

    }
}
