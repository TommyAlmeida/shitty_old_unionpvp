package eu.union.dev.commands.staff;

import eu.union.dev.engine.KPlayer;
import eu.union.dev.engine.managers.PlayerManager;
import eu.union.dev.utils.globals.Messages;
import org.bukkit.Bukkit;
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

        if(cmd.getName().equalsIgnoreCase("reset")){
            if(args.length == 0){
                player.sendMessage(Messages.PREFIX.toString() + " §7Use: /reset <player>");
                return true;
            }

            Player target = Bukkit.getPlayerExact(args[0]);
            KPlayer kplayer = PlayerManager.getPlayer(target.getUniqueId());

            if(!target.isOnline()){
                player.sendMessage(Messages.PREFIX.toString() + " §cSpecify an existing player");
                return true;
            }

            if(kplayer != null){
                kplayer.clear(true);
            }
        }

        return true;

    }
}
