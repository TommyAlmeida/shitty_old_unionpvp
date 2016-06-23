package eu.union.dev.commands.location;

import eu.union.dev.listeners.menus.submenus.WarpsMenu;
import eu.union.dev.utils.globals.Inv;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Fentis on 21/05/2016.
 */
public class Warps implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("warps")) {
            if (sender instanceof Player) {
                new WarpsMenu().setItems();
                ((Player) sender).openInventory(Inv.getInstance().warps);
            }
        }
        return false;
    }
}
