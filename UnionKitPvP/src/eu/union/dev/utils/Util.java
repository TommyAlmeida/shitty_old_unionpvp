package eu.union.dev.utils;

import eu.union.dev.PvPMain;
import eu.union.dev.api.Ability;
import eu.union.dev.api.Icon;
import eu.union.dev.api.Packets;
import eu.union.dev.engine.KPlayer;
import eu.union.dev.engine.Kit;
import eu.union.dev.engine.managers.PlayerManager;
import net.minecraft.server.v1_8_R3.AttributeModifier;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;
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
            ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());

            SkullMeta meta = (SkullMeta) skull.getItemMeta();
            meta.setOwner(player.getName());
            meta.setDisplayName("§9Stats §7(Right-Click)");
            skull.setItemMeta(meta);

            inv.setItem(8,skull);
        }
    }

    public void buildScoreboard(Player p) {
        final KPlayer profile = PlayerManager.getPlayer(p.getUniqueId());

        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective obj = board.registerNewObjective("dummy","dummy");
        obj.setDisplayName("§aLv.§e" + profile.getLevel());
        obj.setDisplaySlot(DisplaySlot.BELOW_NAME);

        new BukkitRunnable() {
            public void run() {
                final String STATS_OBJC_ID = "Stats";
                final Scoreboard board;

                if (p.getScoreboard() == null) {
                    board = Bukkit.getScoreboardManager().getNewScoreboard();
                } else {
                    board = p.getScoreboard();
                }

                Objective obj;

                if (board.getObjective(STATS_OBJC_ID) == null) {
                    obj = board.registerNewObjective(STATS_OBJC_ID, "dummy");
                } else {
                    board.getObjective(STATS_OBJC_ID).unregister();
                    obj = board.registerNewObjective(STATS_OBJC_ID, "dummy");
                }

                obj.setDisplaySlot(DisplaySlot.SIDEBAR);
                int sc = 8;
                obj.setDisplayName("      §e§lUNION KITPVP      ");
                obj.getScore("§a").setScore(sc--);
                obj.getScore("§e [INFO] ").setScore(sc--);
                obj.getScore("§f  Kills: §e" + profile.getKills()).setScore(sc--);
                obj.getScore("§f  Deaths: §e" + profile.getDeaths()).setScore(sc--);
                obj.getScore("§f  Coins: §e" + profile.getCoins()).setScore(sc--);
                obj.getScore("§f  Streak: §e").setScore(sc--); //TODO: implementar
                obj.getScore("§f  Level: §e" + profile.getLevel()).setScore(sc--);
                obj.getScore("§b").setScore(sc--);
                obj.getScore("§f  www.unionnetwork.eu").setScore(sc);

                p.setScoreboard(board);
            }
        }.runTaskTimer(PvPMain.getInstance(), 0, 20);
    }

    public void readyPlayer(Player player) {
        player.getInventory().clear();
        player.setHealth(player.getMaxHealth());
        player.setFoodLevel(20);
        player.setExhaustion(0f);
        player.setFallDistance(0f);
        player.setFireTicks(0);

        for (PotionEffect pE : player.getActivePotionEffects()) {
            player.removePotionEffect(pE.getType());
        }

    }
}
