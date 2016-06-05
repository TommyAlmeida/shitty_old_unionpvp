package eu.union.dev.utils.globals;

import eu.union.dev.PvPMain;
import eu.union.dev.api.Ability;
import eu.union.dev.api.Icon;
import eu.union.dev.api.Packets;
import eu.union.dev.engine.KPlayer;
import eu.union.dev.engine.managers.KitManager;
import eu.union.dev.engine.managers.PlayerManager;
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
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Util {

    private static Util instance = new Util();
    private ArrayList<String> pvp = new ArrayList<>();

    public static Util getInstance() {
        return instance;
    }

    public String center(String msg, int length) {
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

    public void sendCooldownMessage(Player player, Ability cooldown, TimeUnit timeUnit, boolean actionbar) {
        if (!actionbar) {
            player.sendMessage(Messages.PREFIX.toString() + " §7You're in cooldown of §c{time} §7seconds".replace("{time}", String.valueOf(cooldown.getStatus(player).getRemainingTime(timeUnit))));
        } else {
            player.sendMessage(Messages.PREFIX.toString() + " §7You're in cooldown of §c{time} §7seconds".replace("{time}", String.valueOf(cooldown.getStatus(player).getRemainingTime(timeUnit))));
            Packets.getAPI().sendActionBar(player, "§7You're in cooldown of §c{time} §7seconds".replace("{time}", String.valueOf(cooldown.getStatus(player).getRemainingTime(timeUnit))));
        }
    }

    public void giveSoups(Player player) {

        for (int i = 0; i < 50; i++) {
            player.getInventory().addItem(new ItemStack(Material.MUSHROOM_SOUP));
        }

        player.getInventory().setItem(13, new ItemStack(Material.RED_MUSHROOM, 64));
        player.getInventory().setItem(15, new ItemStack(Material.BROWN_MUSHROOM, 64));
        player.getInventory().setItem(14, new ItemStack(Material.BOWL, 64));

    }

    public void buildJoinIcons(Player player) {
        Inventory inv = player.getInventory();
        inv.clear();
        {
            Icon kits = new Icon(Material.NETHER_STAR, "§aKits §7(Right-Click)", "§7Choose your kit");
            inv.setItem(0, kits.build());
        }


        {
            Icon warps = new Icon(Material.VINE, " ", " ");
            inv.setItem(3, warps.build());
            inv.setItem(5, warps.build());
        }

        {
            Icon menu = new Icon(Material.COMPASS, "§bMenu §7(Right-Click)", "§7All you need.");
            inv.setItem(4, menu.build());
        }

        {
            ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());

            SkullMeta meta = (SkullMeta) skull.getItemMeta();
            meta.setOwner(player.getName());
            meta.setDisplayName("§9Stats §7(Right-Click)");
            skull.setItemMeta(meta);

            inv.setItem(8, skull);
        }
    }

    public void buildScoreboard(Player p) {
        final KPlayer profile = PlayerManager.getPlayer(p.getUniqueId());
        KitManager km = KitManager.getManager();

        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();

        Objective stats = board.registerNewObjective("stats", "dummy");
        stats.setDisplaySlot(DisplaySlot.SIDEBAR);
        int index = 11;
        stats.setDisplayName("      §e§lUNION KITPVP      ");
        stats.getScore("§a").setScore(index--);
        stats.getScore("§e[INFO] ").setScore(index--);
        stats.getScore("§1").setScore(index--);
        stats.getScore("§2").setScore(index--);
        stats.getScore("§3").setScore(index--);
        stats.getScore("§4").setScore(index--);
        stats.getScore("§6").setScore(index--);
        stats.getScore("§5").setScore(index--);
        stats.getScore("§b").setScore(index--);
        stats.getScore("§7/stats").setScore(index);
        stats.getScore("§fwww.unionnetwork.eu").setScore(index);

        board.registerNewTeam("kills").addEntry("§1");
        board.registerNewTeam("deaths").addEntry("§2");
        board.registerNewTeam("coins").addEntry("§3");
        board.registerNewTeam("level").addEntry("§4");
        board.registerNewTeam("kdr").addEntry("§5");
        board.registerNewTeam("exp").addEntry("§6");

        new BukkitRunnable() {
            final Team deaths = board.getTeam("deaths");
            final Team kills = board.getTeam("kills");
            final Team coins = board.getTeam("coins");
            final Team level = board.getTeam("level");
            final Team exp = board.getTeam("exp");
            final Team kdr = board.getTeam("kdr");

            public void run() {
                final KPlayer profile = PlayerManager.getPlayer(p.getUniqueId());

                deaths.setPrefix("§fDeaths: §e" + profile.getDeaths());
                kills.setPrefix("§fKills: §e" + profile.getKills());
                coins.setPrefix("§fCoins: §e" + profile.getCoins());
                exp.setPrefix("§fEXP: §e" + profile.getCurrentEXP());
                level.setPrefix("§fLevel: §e" + profile.getLevel());
                kdr.setPrefix("§fKDR: §e" + profile.getKDR());
            }
        }.runTaskTimer(PvPMain.getInstance(), 0, 2 * 20);

        p.setScoreboard(board);
    }

    public void readyPlayer(Player player) {
        player.getInventory().clear();
        player.setHealth(player.getMaxHealth());
        player.setFoodLevel(20);
        player.setExhaustion(0f);
        player.setFallDistance(0f);
        player.setFireTicks(0);
        player.setAllowFlight(false);

        for (PotionEffect pE : player.getActivePotionEffects()) {
            player.removePotionEffect(pE.getType());
        }

    }

    public void readyPlayerNoHealth(Player player) {
        player.getInventory().clear();
        player.setFoodLevel(20);
        player.setExhaustion(0f);
        player.setFallDistance(0f);
        player.setFireTicks(0);
        player.setAllowFlight(false);

        for (PotionEffect pE : player.getActivePotionEffects()) {
            player.removePotionEffect(pE.getType());
        }



    }

    public void randomKit(Player p) {
        Random r = new Random();
        KitManager km = KitManager.getManager();
        int index = r.nextInt(km.getKits().size());

        km.applyKit(p, km.getKits().get(index));
    }

    public boolean inPvP(Player p) {
        if (pvp.contains(p.getName())) {
            return true;
        } else {
            return false;
        }
    }

    public void addPlayerPvP(Player p) {
        if (!inPvP(p)){
            pvp.add(p.getName());
        }
    }

    public void removePlayerPvP(Player p) {
        if (inPvP(p)){
            pvp.remove(p.getName());
        }
    }

    public void addPermission(String playerName, String permission){
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "/pex user " + playerName + " add " + permission);
    }
}
