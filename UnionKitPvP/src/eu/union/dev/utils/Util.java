package eu.union.dev.utils;

import eu.union.dev.api.Ability;
import eu.union.dev.api.Icon;
import eu.union.dev.api.Packets;
import eu.union.dev.engine.KPlayer;
import eu.union.dev.engine.Kit;
import eu.union.dev.engine.managers.PlayerManager;
import net.minecraft.server.v1_8_R3.AttributeModifier;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.concurrent.TimeUnit;

public class Util {

    private static Util instance = new Util();

    public static Util getInstance() {
        return instance;
    }

    public String center(String msg, int length)
    {
        StringBuilder b = new StringBuilder("");
        int msglength = msg.length();
        int numberspaces = Math.round(length / 2) - Math.round(msglength / 2);
        for (int i = 0; i <= numberspaces; i++) {
            b.append(" ");
        }
        for (int i = 0; i < msglength; i++) {
            b.append(msg.charAt(i));
        }
        return b.toString();
    }

    public void sendCooldownMessage(Player player, Ability cooldown, TimeUnit timeUnit, boolean actionbar){
        if(!actionbar){
            player.sendMessage(Messages.PREFIX.toString() + " §7You're in cooldown of §c{time} §7seconds".replace("{time}",String.valueOf(cooldown.getStatus(player).getRemainingTime(timeUnit))));
        }else{
            player.sendMessage(Messages.PREFIX.toString() + " §7You're in cooldown of §c{time} §7seconds".replace("{time}",String.valueOf(cooldown.getStatus(player).getRemainingTime(timeUnit))));
            Packets.getAPI().sendActionBar(player,"§7You're in cooldown of §c{time} §7seconds".replace("{time}",String.valueOf(cooldown.getStatus(player).getRemainingTime(timeUnit))));
        }
    }

    public void giveSoups(Player player) {

        for(int i=0; i < 50; i++) {
            player.getInventory().addItem(new ItemStack(Material.MUSHROOM_SOUP));
        }

        player.getInventory().setItem(31,new ItemStack(Material.RED_MUSHROOM, 64));
        player.getInventory().setItem(32,new ItemStack(Material.BROWN_MUSHROOM, 64));
        player.getInventory().setItem(33,new ItemStack(Material.BOWL, 64));

    }

    public void buildJoinIcons(Player player){
        Inventory inv = player.getInventory();
        KPlayer profile = PlayerManager.getPlayer(player.getUniqueId());

        {
            Icon kits = new Icon(Material.NETHER_STAR, "§aKits §7(Right-Click)", "§7Choose your kit");
            inv.setItem(0,kits.build());
        }


        {
            Icon warps = new Icon(Material.VINE, " ", " ");
            inv.setItem(3,warps.build());
            inv.setItem(5,warps.build());
        }

        {
            Icon menu = new Icon(Material.COMPASS, "§bMenu §7(Right-Click)", "§7All you need.");
            inv.setItem(4,menu.build());
        }

        {
            Icon stats = new Icon(Material.IRON_SWORD, "§9Stats §7(Right-Click)", "§7Where do you wanna go?");
            inv.setItem(8,stats.build());
        }

        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective obj = board.registerNewObjective("dummy","dummy");
        obj.setDisplayName("§aLv.§e" + profile.getLevel());
        obj.setDisplaySlot(DisplaySlot.BELOW_NAME);

        player.setScoreboard(board);
    }

}
