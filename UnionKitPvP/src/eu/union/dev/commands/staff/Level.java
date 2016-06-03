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

public class Level implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            return true; //Retorna
        }

        Player player = (Player) sender;
        KPlayer kPlayer = PlayerManager.getPlayer(player.getUniqueId());

        if (cmd.getName().equalsIgnoreCase("level")) {
            if(Perms.isStaff(player)){
                if (args.length == 0) {
                    player.sendMessage(Messages.PREFIX.toString() + " §7Use: /level <exp> <player>");
                    return true;
                }

                try{
                    int exp = Integer.parseInt(args[0]);

                    if(exp == 0){
                        player.sendMessage(Messages.PREFIX.toString() + " §7Level cant be 0");
                        return true;
                    }

                    if(kPlayer != null){
                        kPlayer.addCurrentEXP(exp);
                        player.sendMessage(Messages.PREFIX.toString() + " §7EXP Given §b" + exp);
                    }else{
                        return true;
                    }
                }catch (NumberFormatException e){
                    e.printStackTrace();
                }
            }
        }

        return true;

    }
}
