package eu.union.dev.commands.staff;

import eu.union.dev.engine.managers.KitManager;
import eu.union.dev.utils.globals.Messages;
import eu.union.dev.utils.globals.Perms;
import eu.union.dev.utils.globals.Util;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReadyPlayerCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            return true; //Retorna
        }

        Player player = (Player) sender;
        KitManager km = KitManager.getManager();

        if (cmd.getName().equalsIgnoreCase("ready")) {
            if (Perms.isStaff(player)) {
                if (args.length == 0) {
                    km.removeKit(player);
                    Util.getInstance().readyPlayer(player);
                    Util.getInstance().buildJoinIcons(player);
                    return true;
                }

                Player target = Bukkit.getPlayerExact(args[0]);

                if (!target.isOnline()) {
                    player.sendMessage(Messages.PREFIX.toString() + " §cSpecify an existing player");
                    return true;
                }

                km.removeKit(target);
                Util.getInstance().readyPlayer(target);
                Util.getInstance().buildJoinIcons(target);
            }
        }

        return true;

    }
}
