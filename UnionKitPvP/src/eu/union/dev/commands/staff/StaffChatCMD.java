package eu.union.dev.commands.staff;

import eu.union.dev.utils.globals.Messages;
import eu.union.dev.utils.globals.Perms;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StaffChatCMD implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(!(commandSender instanceof Player)){
            return true;
        }

        Player player = (Player) commandSender;

        if(Perms.isStaff(player)){
            StringBuilder sb = new StringBuilder();
            for (int i = 1; i < args.length; i++) {
                sb.append(args[i]).append(" ");
            }

            String allArgs = sb.toString().trim();

            if(allArgs.length() == 0) {
                player.sendMessage(Messages.PREFIX.toString() + " §cYour report must have more than 10 characters");
                return true;
            }
        }

        return true;
    }
}
