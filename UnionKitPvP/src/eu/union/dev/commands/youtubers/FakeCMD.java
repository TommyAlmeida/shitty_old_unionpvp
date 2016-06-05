package eu.union.dev.commands.youtubers;

import eu.union.dev.utils.globals.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

public class FakeCMD implements CommandExecutor {


    HashMap<UUID, String> originalName = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(!(sender instanceof Player)){
            return true;
        }

        Player p = (Player) sender;
        String nicks[] = {
                "DanTDM",
                "Spring", "Lfkv",
                "Supersues", "Benjamin",
                "Mikecool31", "TotallyNotMe",
                "Nikewray", "FearlessMC",
                "Pencil", "Cocabale",
                "Assasin", "Scene",
                "Me!", "Thor1818",
                "Deadpool", "Joe",
                "Aaron", "Oasis_pvp",
                "Doge",
        };
        Random r = new Random();

        if(p.hasPermission("fake.use")){
            if(args.length == 0){
                originalName.put(p.getUniqueId(), p.getName());
                p.setDisplayName(nicks[r.nextInt(nicks.length)]);
                p.setPlayerListName(p.getDisplayName());
                p.chat("/disguise player " + nicks);
                p.sendMessage(Messages.PREFIX.toString() + " §7Your new fake name: §b" + p.getDisplayName());
                return true;
            }

            if(args[0].equalsIgnoreCase("reset")){
                p.setDisplayName(originalName.get(1));
                p.setPlayerListName(originalName.get(1));
                p.chat("/undisguise");
                p.sendMessage(Messages.PREFIX.toString() + " §cYour fake is disabled");

                if(originalName.containsKey(p.getUniqueId())){
                    originalName.remove(p.getUniqueId(), originalName.get(1));
                }

                return true;
            }
        }
        return false;
    }
}
