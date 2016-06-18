package eu.union.dev.commands.staff;

import eu.union.dev.engine.KPlayer;
import eu.union.dev.engine.managers.PlayerManager;
import eu.union.dev.utils.globals.Messages;
import eu.union.dev.utils.globals.Perms;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CoinsCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true; //Retorna
        }
        Player player = (Player) sender;
        if (!Perms.isStaff(player)){
            return true;
        }
        Player target = Bukkit.getPlayer(args[0]);
        KPlayer kPlayer = PlayerManager.getPlayer(target.getUniqueId());
        if (args.length < 1) {
            player.sendMessage(Messages.PREFIX.toString() + " §cUse: /coins <player> <amount>");
            return true;
        }

        if(!target.isOnline()){
            return true;
        }

        kPlayer.addCoins(Long.parseLong(args[1]));

        return true;

    }
}
