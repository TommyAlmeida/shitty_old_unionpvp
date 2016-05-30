package eu.union.dev.commands.staff;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReconnectCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            return true; //Retorna
        }

        Player player = (Player) sender;

        if (player.hasPermission("union.admin") || player.hasPermission("union.mod")) {
            Player target = Bukkit.getPlayer(args[0]);

            if (target == null) {
                player.sendMessage("§cThe player is offline.");
            }

            target.kickPlayer("§eReconnect please.");
        }

        return true;

    }
}
