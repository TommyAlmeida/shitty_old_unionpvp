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
            player.sendMessage(Messages.PREFIX.toString() + " §cUse: /report <text, specify the player name");
            return true;
        }

	    StringBuilder sb = new StringBuilder();
		for (int i = 1; i < args.length; i++) {
		     sb.append(args[i]).append(" ");
		 }

		 String allArgs = sb.toString().trim();

        if(allArgs.length() == 0) {
            player.sendMessage(Messages.PREFIX.toString() + " §cYour report must have more than 10 characters");
            return true;
        }

        Bukkit.getOnlinePlayers().stream().filter(Perms::isStaff).forEach(online -> {
            online.sendMessage(Messages.PREFIX.toString() + " §a§ Report from §b" + player.getName() + ": §c" + allArgs);
        });

        return true;

    }
}
