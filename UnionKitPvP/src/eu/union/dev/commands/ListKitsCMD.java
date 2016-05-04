package eu.union.dev.commands;

import java.util.List;
import java.util.stream.Collectors;

import eu.union.dev.engine.Kit;
import eu.union.dev.engine.managers.KitManager;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ListKitsCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        List<String> kitNames = KitManager.getManager().getKits().stream().map(Kit::getName).collect(Collectors.toList());

        String allKits = StringUtils.join(kitNames, ", ");

        sender.sendMessage("§a" + "(" + KitManager.getManager().getKits().size() + ") Kits: " + allKits + ".");

        return true;
    }


}
