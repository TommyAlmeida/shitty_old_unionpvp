package eu.union.dev.commands;

import eu.union.dev.engine.Kit;
import eu.union.dev.engine.managers.KitManager;
import eu.union.dev.utils.globals.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KitCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        KitManager km = KitManager.getManager();

        if (!(sender instanceof Player)) {
            return true; //Retorna
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            player.sendMessage(Messages.PREFIX.toString() + " §cUse: /kit <kitName>");
            return true;
        }

        Kit kit = km.getKitByName(args[0]);

        if (kit == null) {
            player.sendMessage(Messages.PREFIX.toString() + " §7This kit doesn't exist! Type §6/kits §7to see all kits available.");
            return true;
        }

        km.applyKit(player, kit);

        return true;

    }
}
