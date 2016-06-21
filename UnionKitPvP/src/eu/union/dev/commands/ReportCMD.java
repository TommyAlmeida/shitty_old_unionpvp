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

        if (args.length <= 1) {
            player.sendMessage(Messages.PREFIX.toString() + " §cUse: /report <player> <text>");
            return true;
        }

	    StringBuilder sb = new StringBuilder();
		for (int i = 1; i < args.length; i++) {
		     sb.append(args[i]).append(" ");
		 }

        String allArgs = sb.toString().trim();
        Player target = Bukkit.getPlayer(args[0]);

        if(!target.isOnline()){
            player.sendMessage(Messages.PREFIX.toString() + " §cYou cant report an offline player");
            return true;
        }

        for(Player online : Bukkit.getOnlinePlayers()){
            online.sendMessage(Messages.PREFIX.toString() + " §7:: §bReport §7::");
            online.sendMessage(Messages.PREFIX.toString() + " §bBy: §r" + player.getName());
            online.sendMessage(Messages.PREFIX.toString() + " §bPlayer reported: §f" + target.getName());
            online.sendMessage(Messages.PREFIX.toString() + " §bMessage: §c" + allArgs);
        }

        return true;

    }
}
