package eu.union.dev.commands.staff;

import eu.union.dev.engine.managers.KitManager;
import eu.union.dev.utils.globals.Messages;
import eu.union.dev.utils.globals.Perms;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Tomas on 04-07-2016.
 */
public class FlyCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            return true; //Retorna
        }

        Player player = (Player) sender;
        KitManager km = KitManager.getManager();

        if (cmd.getName().equalsIgnoreCase("fly")) {
            if (Perms.isStaff(player)) {
                if (args.length == 0) {
                    player.sendMessage(Messages.PREFIX.toString() + " /fly <nickname>");
                    return true;
                }

                Player target = Bukkit.getPlayerExact(args[0]);

                if (target == null) {
                    player.sendMessage(Messages.PREFIX.toString() + " §cSpecify an existing player");
                    return true;
                }

                if(target.isFlying()){
                    target.setAllowFlight(false);
                    target.setFlying(false);
                    target.sendMessage(Messages.PREFIX.toString() + " §7Now you cant fly!");
                }else{
                    target.setAllowFlight(true);
                    target.setFlying(true);
                    target.sendMessage(Messages.PREFIX.toString() + " §7Now you can fly!");
                }

            }
        }

        return true;

    }

}
