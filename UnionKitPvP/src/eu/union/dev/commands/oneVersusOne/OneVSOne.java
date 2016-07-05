package eu.union.dev.commands.oneVersusOne;

import eu.union.dev.engine.Kit;
import eu.union.dev.engine.games.OneVSOneTempStorage;
import eu.union.dev.engine.managers.KitManager;
import eu.union.dev.engine.storage.ConfigManager;
import eu.union.dev.utils.chatUtil;
import eu.union.dev.utils.globals.Messages;
import jdk.nashorn.internal.runtime.regexp.joni.Config;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Josue on 05/07/2016.
 */
public class OneVSOne implements CommandExecutor{

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            return true; //Retorna
        }

        Player p = (Player) sender;

        if (args.length == 0) {
            p.sendMessage(Messages.PREFIX.toString() + " §c/1v1 entrar");
            return true;
        }
        if (args.length == 1)
        {
            if (args[0].equals("entrar")) {
                if (OneVSOneTempStorage.match.contains(p) == true) {
                    p.sendMessage(chatUtil.color(" &cYou are already in the quee!"));
                    return true;
                }
                p.sendMessage(chatUtil.color(" &aYou entered in the quee!"));
                OneVSOneTempStorage.match.add(p);
                if (OneVSOneTempStorage.match.size() == 2)
                {
                    Player p1 = OneVSOneTempStorage.match.get(0);
                    Player p2 = OneVSOneTempStorage.match.get(1);
                    Location l = ConfigManager.getInstance().getLocation("1V1.SpawnPoint1");
                    Location l1 = ConfigManager.getInstance().getLocation("1V1.SpawnPoint2");
                    eu.union.dev.engine.games.OneVSOne v = new eu.union.dev.engine.games.OneVSOne(p1, p2, l, l1);
                    v.startMatch();
                }
            }
        }

        return true;
    }

}
