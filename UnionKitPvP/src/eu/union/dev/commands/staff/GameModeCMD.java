package eu.union.dev.commands.staff;

import eu.union.dev.utils.globals.Messages;
import eu.union.dev.utils.globals.Perms;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GameModeCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!(commandSender instanceof Player)) {
            return true;
        }

        Player p = (Player) commandSender;

        if (command.getName().equalsIgnoreCase("gm")) {
            if (Perms.isStaff(p)) {
                if (args.length == 0) {
                    p.sendMessage(Messages.PREFIX.toString() + " §7Usage: /gm <o,1>");
                    return true;
                }

                if (args[0].equalsIgnoreCase("0")) {
                    p.setGameMode(GameMode.SURVIVAL);
                    p.sendMessage(Messages.PREFIX.toString() + " §7Gamemode change to §bsurvival");
                } else if (args[0].equalsIgnoreCase("1")) {
                    p.setGameMode(GameMode.CREATIVE);
                    p.sendMessage(Messages.PREFIX.toString() + " §7Gamemode change to §bcreative.");
                }
            }
        }
        return false;
    }
}
