package eu.union.dev.commands.staff;

import eu.union.dev.engine.KPlayer;
import eu.union.dev.engine.managers.PlayerManager;
import eu.union.dev.utils.globals.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ResetStatsCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            return true; //Retorna
        }

        Player player = (Player) sender;

        if (cmd.getName().equalsIgnoreCase("reset")) {
            KPlayer kplayer = PlayerManager.getPlayer(player.getUniqueId());

            if (kplayer != null) {
                if(kplayer.getCoins() >= 5000){
                    kplayer.clear();
                    player.sendMessage(Messages.PREFIX.toString() + " §7Your stats have been reseted with §e5000 coins");
                }else{
                    player.sendMessage(Messages.PREFIX.toString() + " §7You dont have §c5000 coins §7to reset your stats");
                }
            }
        }
        return true;
    }
}
