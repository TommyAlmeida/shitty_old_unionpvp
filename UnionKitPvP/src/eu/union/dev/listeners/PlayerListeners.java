package eu.union.dev.listeners;

import eu.union.dev.PvPMain;
import eu.union.dev.engine.KPlayer;
import eu.union.dev.engine.managers.KitManager;
import eu.union.dev.engine.managers.PlayerManager;
import eu.union.dev.engine.storage.ConfigManager;
import eu.union.dev.utils.globals.CompassCompare;
import eu.union.dev.utils.globals.Messages;
import eu.union.dev.utils.globals.Perms;
import eu.union.dev.utils.globals.Util;
import net.alpenblock.bungeeperms.BungeePerms;
import net.alpenblock.bungeeperms.Group;
import net.alpenblock.bungeeperms.User;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.inventivetalent.bossbar.BossBarAPI;

import java.util.*;

public class PlayerListeners implements Listener {

    KitManager km = KitManager.getManager();
    private Map<Player, Long> cooldown = new HashMap<>();
    private Map<Player, Integer> streaks = new HashMap<>();

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        KitManager km = KitManager.getManager();
        KPlayer kplayer = PlayerManager.getPlayer(player.getUniqueId());

        if (km.usingKit(player))
            km.removeKit(player);

        if (kplayer != null) {
            PvPMain.getInstance().getSQL().updatePlayerProfileSQL(kplayer);
        } else {
            System.out.println("Inexisting PlayerProfile for this Player");
        }

        e.setQuitMessage(null);

        streaks.put(player, 0);

        Bukkit.broadcastMessage("§7(§c-§7) §7" + player.getDisplayName());
        Util.getInstance().removePlayerPvP(player);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        KitManager km = KitManager.getManager();

        e.setJoinMessage(null);
        PvPMain.getInstance().getSQL().createPlayerProfile(p.getUniqueId());
        if (km.usingKit(p))
            km.removeKit(p);

        Location loc = ConfigManager.getInstance().getLocation("Spawn");
        p.teleport(loc);

        Util.getInstance().readyPlayer(p);
        Util.getInstance().buildJoinIcons(p);
        Util.getInstance().buildScoreboard(p);
        streaks.put(p, 0);

        if (!Perms.isStaff(p)){
            Bukkit.broadcastMessage("§7(§a+§7) §7" + p.getDisplayName());
        }
        Util.getInstance().removePlayerPvP(p);
        p.sendMessage(Messages.PREFIX+" §aYou gained the spawn protection");
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e){
        Player p = e.getPlayer();
        KPlayer kPlayer = PlayerManager.getPlayer(p.getUniqueId());

        if(kPlayer == null){
            PvPMain.getInstance().getSQL().createPlayerProfile(p.getUniqueId());
        }
    }


    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        //String prefix = String.valueOf(BungeePerms.getInstance().getPermissionsManager().getUser(e.getPlayer().getUniqueId()).buildPrefix());
        User user = BungeePerms.getInstance().getPermissionsManager().getUser(e.getPlayer().getName());
        Group group = BungeePerms.getInstance().getPermissionsManager().getMainGroup(user);

        KPlayer kPlayer = PlayerManager.getPlayer(e.getPlayer().getUniqueId());

        e.setCancelled(true);

        //Anti-Spam
        /*if(!(e.getPlayer().hasPermission("union.bypass"))) {
            if (this.cooldown.containsKey(e.getPlayer())) {
                int cooldownTime = 5;
                long timeLeft = this.cooldown.get(e.getPlayer()) / 1000L + cooldownTime - System.currentTimeMillis() / 1000L;
                if (timeLeft > 0L) {
                    e.getPlayer().sendMessage(Messages.PREFIX.toString() + " §7You need to wait §a" + timeLeft + "§7 seconds to talk again!");
                    return;
                }
                this.cooldown.remove(e.getPlayer());
            }
            this.cooldown.put(e.getPlayer(), System.currentTimeMillis());

            Bukkit.broadcastMessage("§7[§bLvl.§e" + kPlayer.getLevel() + "§7]" + prefix +" §r§7" + e.getPlayer().getName() + ": §f" + e.getMessage());
        }*/

        Bukkit.broadcastMessage("§7[§bLvl.§e" + kPlayer.getLevel() + "§7]" + ChatColor.translateAlternateColorCodes('&',group.getPrefix()) +" §r§7" + e.getPlayer().getName() + ": §f" + e.getMessage());


    }

    @EventHandler
    void onDrop(final PlayerDropItemEvent event) {

        Player player = event.getPlayer();
        ItemStack i = event.getItemDrop().getItemStack();

        if (!km.usingKit(player))
            return;

        if (i.getType() != Material.MUSHROOM_SOUP && i.getType() != Material.BOWL && i.getType() != Material.BROWN_MUSHROOM
                && i.getType() != Material.RED_MUSHROOM) {
            player.sendMessage(Messages.PREFIX.toString() + " §7You cannot drop this item");
            event.setCancelled(true);
            event.getItemDrop().remove();
        }

    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player killed = e.getEntity();
        Player killer = e.getEntity().getKiller();

        KPlayer kPlayer_killed = PlayerManager.getPlayer(killed.getUniqueId());
        Location loc = ConfigManager.getInstance().getLocation("Spawn");

        //Se o player que está a matar nao for nullo
        if (killer != null) {
            KPlayer kPlayer_killer = PlayerManager.getPlayer(killer.getUniqueId());

            //Variaveis para decidir quanto motante o player recebe de 0 a X
            Random rand = new Random();
            int coins = rand.nextInt(7);
            int lostCoins = rand.nextInt(5);
            int lostEXP = rand.nextInt(56);
            int exp = rand.nextInt(25);

            //Caso o jogador nao existir no player object irá ser kickado para que possa reconectar
            if (kPlayer_killed == null || kPlayer_killer == null) {
                killed.sendMessage(Messages.PREFIX.toString() + " §cReconnect please.");
                killer.sendMessage(Messages.PREFIX.toString() + " §cReconnect please.");
                killed.kickPlayer("§cReconnect please");
                killer.kickPlayer("§cReconnect please");
                return;
            } else {

                //Se o random decidir que for 0 ele irá adicionar +1 para evitar os jogadores receberem 0 de coisn ou exp
                if (coins <= 0 || exp <= 0) {
                    coins++;
                    exp++;
                }

                if(kPlayer_killed.getLevel() >= 5){
                    coins = rand.nextInt(34);
                    exp = rand.nextInt(47);
                }

                if(kPlayer_killed.getMultiplier() > 0){
                    int multiplier = kPlayer_killed.getMultiplier();
                    coins *= multiplier;
                }


                kPlayer_killer.addCurrentEXP(exp);
                kPlayer_killer.addCoins(coins);

                kPlayer_killed.addDeaths(1);
                kPlayer_killer.addKills(1);
                kPlayer_killed.removeCoins(lostCoins);
                kPlayer_killed.subtractEXP(lostEXP);
            }

            //Previne que a mensagem default do minecraft seja mandada
            e.setDeathMessage(null);

            //Envia as mensagens de o jogador ter sido morto e de receber X Stats ou de ser morto
            Bukkit.broadcastMessage("§b" + killed.getDisplayName() + " §7has been killed by §b" + killer.getDisplayName());
            killer.playSound(killer.getLocation(), Sound.ORB_PICKUP, 10f, 10f);

            killer.sendMessage("§e(+" + coins + " coins) §a(+" + exp + " EXP) §cFor killing: §b" + killed.getDisplayName());
            streaks.put(killer, streaks.get(killer) + 1);

            //Killstreaks
            switch (streaks.get(killer)){
                case 5:
                    killstreakMessage(kPlayer_killer, killer, 6);
                    break;
                case 8:
                    killstreakMessage(kPlayer_killer, killer, 10);
                    break;
                case 10:
                    killstreakMessage(kPlayer_killer, killer, 20);
                    break;
                case 14:
                    killstreakMessage(kPlayer_killer, killer, 40);
                    break;
                case 18:
                    killstreakMessage(kPlayer_killer, killer, 70);
                    break;
                case 20:
                    killstreakMessage(kPlayer_killer, killer, 90);
                    break;
                case 23:
                    killstreakMessage(kPlayer_killer, killer, 140);
                    break;
                default:
                    break;
            }


            killed.sendMessage("§8[§aDeath§8] §7-" + lostCoins + " §bcoins §7and §b-" + lostEXP + " §bEXP");
            killed.sendMessage("§8[§aDeath§8] §b" + killer.getDisplayName() + " §7had §b" + (int) killer.getHealth() + " §c❤ §7left.");
            killed.sendMessage("§8[§aDeath§8] §7You were killed by §b" + killer.getDisplayName());
            killed.sendMessage("§8[§aDeath§8] §b" + killer.getDisplayName() + " §7was using §b" + km.getKitByPlayer(killer).getName() + " §7kit.");
            streaks.put(killed, 0);
        }

        if ((killer == null) || (e.getEntity().getKiller() == null)) {
            EntityDamageEvent.DamageCause damage = e.getEntity().getLastDamageCause().getCause();
            switch (damage){
                case PROJECTILE:
                    Bukkit.broadcastMessage("§a" + killed.getName() + "§c has died by projectile");
                    e.setDeathMessage(null);
                    break;
                case SUFFOCATION:
                    Bukkit.broadcastMessage("§a" + killed.getName() + "§c thought he was a fish and died");
                    e.setDeathMessage(null);
                    break;
                case FALL:
                    Bukkit.broadcastMessage("§a" + killed.getName() + "§c thought he was a bird and died");
                    e.setDeathMessage(null);
                    break;
                case FIRE:
                    Bukkit.broadcastMessage("§a" + killed.getName() + "§c has died by fire");
                    e.setDeathMessage(null);
                    break;
                case FIRE_TICK:
                    Bukkit.broadcastMessage("§a" + killed.getName() + "§c has died by fire");
                    e.setDeathMessage(null);
                    break;
                case LAVA:
                    Bukkit.broadcastMessage("§a" + killed.getName() + "§c has died by lava");
                    e.setDeathMessage(null);
                    break;
                case DROWNING:
                    Bukkit.broadcastMessage("§a" + killed.getName() + "§c thought it was a fish and died");
                    e.setDeathMessage(null);
                    break;
                case BLOCK_EXPLOSION:
                    Bukkit.broadcastMessage("§a" + killed.getName() + "§c has died by explosion");
                    e.setDeathMessage(null);
                    break;
                case ENTITY_EXPLOSION:
                    Bukkit.broadcastMessage("§a" + killed.getName() + "§c has died by explosion");
                    e.setDeathMessage(null);
                    break;
                case VOID:
                    Bukkit.broadcastMessage("§a" + killed.getName() + "§c has died by void");
                    e.setDeathMessage(null);
                    break;
                case LIGHTNING:
                    Bukkit.broadcastMessage("§a" + killed.getName() + "§c has died by lightning");
                    e.setDeathMessage(null);
                    break;
                case SUICIDE:
                    Bukkit.broadcastMessage("§a" + killed.getName() + "§c has died by suicide");
                    e.setDeathMessage(null);
                    break;
                case POISON:
                    Bukkit.broadcastMessage("§a" + killed.getName() + "§c has died by poison");
                    e.setDeathMessage(null);
                    break;
                case MAGIC:
                    Bukkit.broadcastMessage("§a" + killed.getName() + "§c has died by magic");
                    e.setDeathMessage(null);
                    break;
                case WITHER:
                    Bukkit.broadcastMessage("§a" + killed.getName() + "§c has died by wither");
                    e.setDeathMessage(null);
                    break;
                case FALLING_BLOCK:
                    Bukkit.broadcastMessage("§a" + killed.getName() + "§c has died by falling block");
                    e.setDeathMessage(null);
                    break;
            }
        }

        e.getDrops().removeIf(k ->
                k != null && !(
                        k.getType() == Material.MUSHROOM_SOUP ||
                                k.getType() == Material.RED_MUSHROOM ||
                                k.getType() == Material.BROWN_MUSHROOM ||
                                k.getType() == Material.BOWL
                )
        );
        killed.setHealth(20.0);
        /*
            Delay para teleportar, pois senão os items
            são dropados no spawn.
         */
       new BukkitRunnable() {
            @Override
            public void run() {
                killed.teleport(loc);
                if(km.usingKit(killed)){
                    km.removeKit(killed);
                }else{
                    km.readyPlayer(killed);
                }
                Util.getInstance().buildJoinIcons(killed);
                for (int i = 0; i < 10; i++) {
                    killed.setFireTicks(0);
                }
                Util.getInstance().removePlayerPvP(killed);
                killed.sendMessage(Messages.PREFIX+" §aYou gained spawn protection");
            }

        }.runTaskLater(PvPMain.getInstance(), 5);

        for (int i = 0; i < 10; i++) {
            killed.setFireTicks(0);
        }

    }

    @EventHandler(priority= EventPriority.HIGH)
    public void onDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player){
            Player p = (Player)e.getEntity();
            if (!Util.getInstance().inPvP(p)){
                e.setCancelled(true);
            }
        }
    }

    private void killstreakMessage(KPlayer kPlayer_killer, Player killer, int coins){
        Bukkit.broadcastMessage("§8[§aKill§8] §b" + killer.getDisplayName() + " §6is in KillStreak of §b5");
        kPlayer_killer.addCoins(coins);
        killer.sendMessage("§8[§aEconomy§8] §aYou have been rewarded with §6" + coins + " §acoins.");
        Util.getInstance().launchRandomFirework(killer.getLocation());
    }

    @EventHandler
    public void onCompass(PlayerInteractEvent e){
        Player p = e.getPlayer();
        if (p.getItemInHand().getType() == Material.COMPASS && p.getItemInHand().getItemMeta().getDisplayName() == "§aCompass"){
            String message = "§c§lNo Players Nearby!";
            List<Player> players = new ArrayList<>();
            for (Player ps : p.getWorld().getPlayers()){
                if (!(ps.getUniqueId().equals(p.getUniqueId())) &&
                        ps.getGameMode() == GameMode.SURVIVAL &&
                        ps.getLocation().distance(p.getLocation()) >=10.0){
                    players.add(ps);
                }
            }
            Collections.sort(players, new CompassCompare(p));
            Player nearest = null;

            try {
                nearest = players.get(0);
            }catch (IndexOutOfBoundsException ex){}
            if (nearest != null){
                message = "§aPlayer: §7"+nearest.getName()+" " +
                        "§aDistance: §7"+((int)nearest.getLocation().distance(p.getLocation()));
                p.setCompassTarget(nearest.getLocation());
            }
            p.sendMessage(Messages.PREFIX+" "+message);
        }
    }

}
