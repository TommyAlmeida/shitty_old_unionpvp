package eu.union.dev.commands.staff;

import eu.union.dev.engine.KPlayer;
import eu.union.dev.engine.managers.PlayerManager;
import eu.union.dev.utils.globals.Messages;
import eu.union.dev.utils.globals.Perms;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EconomyCMD implements CommandExecutor{

    /*
        Command Structure:
        /eco - show player coins
        /eco add <type> [player] <amount>
        /eco remove <type> [player] <amount>
        /eco types
        /echo help

    */
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(!(commandSender instanceof Player)){
            return true;
        }

        Player p = (Player) commandSender;
        KPlayer kplayer = PlayerManager.getPlayer(p.getUniqueId());

        if(command.getName().equalsIgnoreCase("eco")){
            if(args.length == 0){
                p.sendMessage(Messages.PREFIX.toString()
                        + " §7You have §e" + kplayer.getCoins()
                        + " coins §7and §a"  + kplayer.getCurrentEXP() + " EXP"
                );

                return true;
            }

            if(Perms.isStaff(p)){
                switch (args[0].toLowerCase()){
                    case "add":
                        String type = args[1];
                        Player target = Bukkit.getPlayer(args[0]);
                        int amount = Integer.parseInt(args[2]);

                        if(type.isEmpty()){
                            p.sendMessage(Messages.PREFIX.toString() + " §cYou must specify the economy type!");
                            return true;
                        }

                        if(target == null || !target.isOnline()){
                            p.sendMessage(Messages.PREFIX.toString() + " §cThis player is offline!");
                            return true;
                        }

                        if(amount == 0){
                            p.sendMessage(Messages.PREFIX.toString() + " §cThe amount specify must be greater than 0");
                            return true;
                        }

                        switch(type.toLowerCase()){
                            case "coins":
                                kplayer.addCoins(amount);
                                break;
                            case "level":
                                kplayer.setLevel(+amount);
                                break;
                        }
                        break;
                    case "remove":
                        String type2 = args[1];
                        Player target2 = Bukkit.getPlayer(args[0]);
                        int amount2 = Integer.parseInt(args[2]);

                        if(type2.isEmpty()){
                            p.sendMessage(Messages.PREFIX.toString() + " §cYou must specify the economy type!");
                            return true;
                        }

                        if(target2 == null || !target2.isOnline()){
                            p.sendMessage(Messages.PREFIX.toString() + " §cThis player is offline!");
                            return true;
                        }

                        if(amount2 == 0){
                            p.sendMessage(Messages.PREFIX.toString() + " §cThe amount specify must be greater than 0");
                            return true;
                        }

                        switch(type2.toLowerCase()){
                            case "coins":
                                kplayer.removeCoins(amount2);
                                break;
                            case "level":
                                kplayer.subtractEXP(amount2);
                                break;
                        }
                        break;
                    case "help":
                        helpItems(p);
                        break;
                    case "types":
                        p.sendMessage("§9--------------- §bEconomy Types §9 ---------------");
                        p.sendMessage("§7 - §6Coins");
                        p.sendMessage("§7 - §aLevel");
                        p.sendMessage("§9---------------------------------------------");
                        break;
                }
            }
        }
        return false;
    }


    private void helpItems(Player p){
        p.sendMessage("§9--------------- §bEconomy §9---------------");
        p.sendMessage("§9 - §7/eco add <type> [player] <amount>");
        p.sendMessage("§9 - §7/eco remove <type> [player] <amount>");
        p.sendMessage("§9 - §7/eco types");
        p.sendMessage("§9--------------------------------------");
    }

}
