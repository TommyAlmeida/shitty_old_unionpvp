package eu.union.dev.commands;

import eu.union.dev.engine.KPlayer;
import eu.union.dev.engine.managers.PlayerManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StatsCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            return true; //Retorna
        }

        Player player = (Player) sender;
        KPlayer kplayer = PlayerManager.getPlayer(player.getUniqueId());

        if (cmd.getName().equalsIgnoreCase("stats")) {
            if (kplayer != null) {
                player.sendMessage("§7§m-------------------------------");
                player.sendMessage("§9Level: §e%s".replace("%s", String.valueOf(kplayer.getLevel())));
                player.sendMessage("§9Current EXP: §e%s".replace("%s", String.valueOf(kplayer.getCurrentEXP())));
                player.sendMessage("§9Needed EXP: §e%s".replace("%s", String.valueOf(kplayer.getNeededXP())));
                player.sendMessage("§9Coins: §e%s".replace("%s", String.valueOf(kplayer.getCoins())));
                player.sendMessage("§9Kills: §e%s".replace("%s", String.valueOf(kplayer.getKills())));
                player.sendMessage("§9Deaths: §e%s".replace("%s", String.valueOf(kplayer.getDeaths())));
                player.sendMessage("§9KDR: §e%s".replace("%s", String.valueOf(kplayer.getKDR())));
                player.sendMessage("§7§m-------------------------------");
            }
        }


        return true;

    }
}
