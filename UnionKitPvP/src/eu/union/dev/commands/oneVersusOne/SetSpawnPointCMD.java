package eu.union.dev.commands.oneVersusOne;

import eu.union.dev.engine.ConfigYML;
import eu.union.dev.engine.Kit;
import eu.union.dev.engine.managers.KitManager;
import eu.union.dev.engine.storage.ConfigManager;
import eu.union.dev.utils.chatUtil;
import eu.union.dev.utils.globals.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Josue on 04/07/2016.
 */
public class SetSpawnPointCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            return true; //Retorna
        }
        Player p = (Player) sender;
        if (!(p.hasPermission("union.admin")))
        {
            return true;
        }


        if (args.length == 0)
        {
            p.sendMessage(chatUtil.color("&c/1v1spawnpoint 1/2"));
        }
        if (args.length == 1)
        {
            if (args[0].equals("1"))
            {
                try {
                    ConfigManager.getInstance().setLocation("1V1.SpawnPoint1", p.getLocation());
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
                p.sendMessage(chatUtil.color(" &7[&b!&7] &&6Spawnpoint 1 para o KitPvP foi setado!"));
            } else if (args[0].equals("2"))
            {
                try {
                    ConfigManager.getInstance().setLocation("1V1.SpawnPoint2", p.getLocation());
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
                p.sendMessage(chatUtil.color(" &7[&b!&7] &&6Spawnpoint 2 para o KitPvP foi setado!"));
            } else {
                p.sendMessage(chatUtil.color("&c/1v1spawnpoint 1/2"));
            }
        }

        return true;

    }

}
