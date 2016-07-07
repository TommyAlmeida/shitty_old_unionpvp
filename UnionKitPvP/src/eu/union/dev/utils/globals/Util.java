package eu.union.dev.utils.globals;

import eu.union.dev.PvPMain;
import eu.union.dev.api.Ability;
import eu.union.dev.api.Icon;
import eu.union.dev.engine.KPlayer;
import eu.union.dev.engine.managers.KitManager;
import eu.union.dev.engine.managers.PlayerManager;
import org.bukkit.*;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class Util {

    private static Util instance = new Util();
    private ArrayList<UUID> pvp = new ArrayList<>();

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
        }
    }

    public void giveSoups(Player player) {
        Icon icon = new Icon(Material.MUSHROOM_SOUP, "§cSoup");

        for (int i = 0; i < 50; i++) {
            player.getInventory().addItem(icon.build());
        }

        player.getInventory().setItem(13, new ItemStack(Material.RED_MUSHROOM, 64));
        player.getInventory().setItem(15, new ItemStack(Material.BROWN_MUSHROOM, 64));
        player.getInventory().setItem(14, new ItemStack(Material.BOWL, 64));

    }

    public void giveMilk(Player player) {
        Icon icon = new Icon(Material.MILK_BUCKET, "§cMilk");

        for (int i = 0; i < 50; i++) {
            player.getInventory().addItem(icon.build());
        }

        player.getInventory().setItem(13, new ItemStack(Material.LEATHER, 64));
        player.getInventory().setItem(15, new ItemStack(Material.EGG, 64));
        player.getInventory().setItem(14, new ItemStack(Material.BUCKET, 64));
    }


    public void buildJoinIcons(Player player) {
        Inventory inv = player.getInventory();
        inv.clear();
        {
            Icon kits = new Icon(Material.NETHER_STAR, "§aKits §7(Right-Click)", "§7Choose your kit");
            inv.setItem(0, kits.buildNoDrop());
        }


        {
            Icon warps = new Icon(Material.VINE, " ", " ");
            inv.setItem(3, warps.buildNoDrop());
            inv.setItem(5, warps.buildNoDrop());
        }

        {
            Icon menu = new Icon(Material.COMPASS, "§bMenu §7(Right-Click)", "§7All you need.");
            inv.setItem(4, menu.buildNoDrop());
        }

        {
            ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());

            SkullMeta meta = (SkullMeta) skull.getItemMeta();
            meta.setOwner(player.getName());
            meta.setDisplayName("§9Stats §7(Right-Click)");
            meta.spigot().setUnbreakable(true);
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
        int index = 10;
        stats.setDisplayName("      §b§lUnion §3§lKitPvP      ");
        stats.getScore("§a").setScore(index--);
        stats.getScore("§3[INFO] ").setScore(index--);
        stats.getScore("§1").setScore(index--);
        stats.getScore("§2").setScore(index--);
        stats.getScore("§3").setScore(index--);
        stats.getScore("§4").setScore(index--);
        stats.getScore("§6").setScore(index--);
        stats.getScore("§b").setScore(index--);
        stats.getScore("§7/stats").setScore(index);

        board.registerNewTeam("kills").addEntry("§1");
        board.registerNewTeam("deaths").addEntry("§2");
        board.registerNewTeam("coins").addEntry("§3");
        board.registerNewTeam("level").addEntry("§4");
        board.registerNewTeam("exp").addEntry("§6");

        new BukkitRunnable() {
            final Team deaths = board.getTeam("deaths");
            final Team kills = board.getTeam("kills");
            final Team coins = board.getTeam("coins");
            final Team level = board.getTeam("level");
            final Team exp = board.getTeam("exp");

            public void run() {
                final KPlayer profile = PlayerManager.getPlayer(p.getUniqueId());

                deaths.setPrefix("§fDeaths: §b" + profile.getDeaths());
                kills.setPrefix("§fKills: §b" + profile.getKills());
                coins.setPrefix("§fCoins: §b" + profile.getCoins());
                exp.setPrefix("§fEXP: §b" + profile.getCurrentEXP());
                level.setPrefix("§fLevel: §b" + profile.getLevel());
            }
        }.runTaskTimer(PvPMain.getInstance(), 0, 2 * 20);

        p.setScoreboard(board);
    }

    public void readyPlayer(Player player) {
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
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

    public void randomKit(Player p) {
        Random r = new Random();
        KitManager km = KitManager.getManager();
        int index = r.nextInt(km.getKits().size());

        km.applyKit(p, km.getKits().get(index));
    }

    public boolean inPvP(Player p) {
        return pvp.contains(p.getUniqueId());
    }

    public void addPlayerPvP(Player p) {
        if (!inPvP(p)) {
            pvp.add(p.getUniqueId());
        }
    }

    public void removePlayerPvP(Player p) {
        if (inPvP(p)) {
            pvp.remove(p.getUniqueId());
        }
    }

    public void addPermission(String playerName, String permission) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "/pex user " + playerName + " add " + permission);
    }

    //Make the arraylists for the colors and types
    ArrayList<Color> colors = new ArrayList<Color>();
    ArrayList<FireworkEffect.Type> types = new ArrayList<FireworkEffect.Type>();

    //MAKE SURE YOU PUT THIS IN YOUR ONENABLE!!!
    public void addColors() {
//ADD ALL THE COLORS
        colors.add(Color.WHITE);
        colors.add(Color.PURPLE);
        colors.add(Color.RED);
        colors.add(Color.GREEN);
        colors.add(Color.AQUA);
        colors.add(Color.BLUE);
        colors.add(Color.FUCHSIA);
        colors.add(Color.GRAY);
        colors.add(Color.LIME);
        colors.add(Color.MAROON);
        colors.add(Color.YELLOW);
        colors.add(Color.SILVER);
        colors.add(Color.TEAL);
        colors.add(Color.ORANGE);
        colors.add(Color.OLIVE);
        colors.add(Color.NAVY);
        colors.add(Color.BLACK);
//I think I added them all not sure though
    }

    //MAKE SURE YOU PUT THIS IN YOUR ONENABLE!!!
    public void addTypes() {
//ADD ALL THE TYPES
        types.add(FireworkEffect.Type.BURST);
        types.add(FireworkEffect.Type.BALL);
        types.add(FireworkEffect.Type.BALL_LARGE);
        types.add(FireworkEffect.Type.CREEPER);
        types.add(FireworkEffect.Type.STAR);
//Added all the types
    }

//Getting a random firework

    public FireworkEffect.Type getRandomType() {
        int size = types.size();
        Random ran = new Random();

        FireworkEffect.Type theType = types.get(ran.nextInt(size));

        return theType;
    }

//Getting a random COLOR!!!

    public Color getRandomColor() {
        int size = colors.size();
        Random ran = new Random();
        int rand = ran.nextInt(size);

        if(rand <= 0){
            return Color.AQUA;
        }

        Color color = colors.get(rand);

        return color;
    }

    public void launchRandomFirework(Location loc) {
        Firework fw = loc.getWorld().spawn(loc, Firework.class);
        FireworkMeta fm = fw.getFireworkMeta();
        fm.setPower(1);
//Adding all the effects to the firework meta
        fm.addEffects(FireworkEffect.builder().with(getRandomType()).withColor(getRandomColor()).build());
//set the firework meta to the firework!
        fw.setFireworkMeta(fm);
    }
}
