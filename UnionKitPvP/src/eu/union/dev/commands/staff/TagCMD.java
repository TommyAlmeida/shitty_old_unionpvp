package eu.union.dev.commands.staff;

import eu.union.dev.utils.globals.Messages;
import eu.union.dev.utils.globals.Perms;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TagCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            return true; //Retorna
        }

        Player player = (Player) sender;

        if (player.hasPermission(Perms.UNION_ADMIN.toString())
                || player.hasPermission(Perms.UNION_DEV.toString())
                || player.hasPermission(Perms.UNION_OWNER.toString()))
        {
            if(args.length == 0){
                player.sendMessage(Messages.PREFIX.toString() + " §7Tags: §cAdmin §7: §9Mod §7: §5Dev");
                return true;
            }

            String tag = args[0];

            switch (tag.toLowerCase()){
                case "admin":
                    player.setPlayerListName("§c[Admin] §7" + player.getDisplayName());
                    player.sendRawMessage(Messages.PREFIX.toString() + " §7You changed your tab tag to §b" + tag);
                    break;
                case "mod":
                    player.setPlayerListName("§9[Mod] §7" + player.getDisplayName());
                    player.sendRawMessage(Messages.PREFIX.toString() + " §7You changed your tab tag to §b" + tag);
                    break;
                case "dev":
                    player.setPlayerListName("§5[Dev] §7" + player.getDisplayName());
                    player.sendRawMessage(Messages.PREFIX.toString() + " §7You changed your tab tag to §b" + tag);

                    break;
            }

        }else if (player.hasPermission(Perms.UNION_MOD.toString())) {
            if(args.length == 0){
                player.sendMessage(Messages.PREFIX.toString() + " §7Tags: §9Mod §7: §bTmod");
                return true;
            }

            String tag = args[0];

            switch (tag.toLowerCase()){
                case "mod":
                    player.setPlayerListName("§9[Mod] §7" + player.getDisplayName());
                    player.sendRawMessage(Messages.PREFIX.toString() + " §7You changed your tab tag to §b" + tag);
                    break;
                case "tmod":
                    player.setPlayerListName("§b[Tmod] §7" + player.getDisplayName());
                    player.sendRawMessage(Messages.PREFIX.toString() + " §7You changed your tab tag to §b" + tag);

                    break;
            }

        }else if (player.hasPermission(Perms.UNION_TMOD.toString())) {
            if(args.length == 0){
                player.sendMessage(Messages.PREFIX.toString() + " §7Tags: §bTMod");
                return true;
            }

            String tag = args[0];

            switch (tag.toLowerCase()){
                case "tmod":
                    player.setPlayerListName("§b[Tmod] §7" + player.getDisplayName());
                    player.sendRawMessage(Messages.PREFIX.toString() + " §7You changed your tab tag to §b" + tag);

                    break;
            }

        }

        return true;

    }
}
