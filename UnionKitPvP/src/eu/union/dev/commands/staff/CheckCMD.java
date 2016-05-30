package eu.union.dev.commands.staff;

import eu.union.dev.engine.KPlayer;
import eu.union.dev.engine.managers.KitManager;
import eu.union.dev.engine.managers.PlayerManager;
import eu.union.dev.utils.globals.Messages;
import eu.union.dev.utils.globals.Perms;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CheckCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            return true; //Retorna
        }

        Player player = (Player) sender;

        if (cmd.getName().equalsIgnoreCase("check")) {
            if (Perms.isStaff(player)) {
                if (args.length == 0) {
                    player.sendMessage(Messages.PREFIX.toString() + " §7Use: /check <player> - to check is infos");
                }

                Player target = Bukkit.getPlayer(args[0]);
                KPlayer kplayer = PlayerManager.getPlayer(target.getUniqueId());
                KitManager km = KitManager.getManager();

                if (!target.isOnline()) {
                    player.sendMessage(Messages.PREFIX.toString() + " §cSpecify an existing player");
                    return true;
                }

                player.sendMessage("§7§m-------------------------------");
                player.sendMessage("§9Coins: §e%s".replace("%s", String.valueOf(kplayer.getCoins())));
                player.sendMessage("§9Kills: §e%s".replace("%s", String.valueOf(kplayer.getKills())));
                player.sendMessage("§9Deaths: §e%s".replace("%s", String.valueOf(kplayer.getDeaths())));
                player.sendMessage("§9KDR: §e%s".replace("%s", String.valueOf(kplayer.getKDR())));
                player.sendMessage(" ");
                player.sendMessage("§9Kit: §e" + km.getKitByPlayer(target).getName());
                player.sendMessage("§9Kits owned: §e" + km.getKits().size());
                player.sendMessage(" ");
                player.sendMessage("§9IP: §e" + target.getAddress().getAddress().getHostAddress());
                player.sendMessage("§7§m-------------------------------");
            }
        }

        return true;

    }
}
