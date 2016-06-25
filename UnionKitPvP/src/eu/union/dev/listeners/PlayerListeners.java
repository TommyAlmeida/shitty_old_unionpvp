package eu.union.dev.listeners;

import eu.union.dev.PvPMain;
import eu.union.dev.engine.KPlayer;
import eu.union.dev.engine.managers.KitManager;
import eu.union.dev.engine.managers.PlayerManager;
import eu.union.dev.engine.storage.ConfigManager;
import eu.union.dev.utils.globals.Messages;
import eu.union.dev.utils.globals.Perms;
import eu.union.dev.utils.globals.Util;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.inventivetalent.bossbar.BossBarAPI;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

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
        if (kplayer != null) {
            kplayer.clearKillstreak();
        }
        streaks.put(player, 0);

        Bukkit.broadcastMessage("§7(§c-§7) §7" + player.getDisplayName());

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
       // Util.getInstance().removePlayerPvP(p);
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
    public void onKitShow(EntityDamageByEntityEvent e){
        Player p = (Player) e.getDamager();
        Player v = (Player) e.getEntity();

        KitManager km = KitManager.getManager();

        if(e.getDamager() instanceof Player && e.getEntity() instanceof Player){
            BossBarAPI.setMessage(p, "§b" + v.getDisplayName() + " §ris using the kit: §b" + km.getKitByPlayer(v).getName(),100,3);
            //BarAPI.setMessage(p, "§b" + v.getDisplayName() + " §ris using the kit: §b" + km.getKitByPlayer(v).getName(), 10);
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        String prefix = PermissionsEx.getUser(e.getPlayer()).getGroups()[0].getPrefix();
        KPlayer kPlayer = PlayerManager.getPlayer(e.getPlayer().getUniqueId());

        e.setCancelled(true);

        //Anti-Spam
        if(!(e.getPlayer().hasPermission("union.bypass"))) {
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
        } else {
            Bukkit.broadcastMessage("§7[§bLvl.§e" + kPlayer.getLevel() + "§7]" + prefix + " §r§7" + e.getPlayer().getName() + ": §f" + e.getMessage());
        }


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

                if(killer.hasPermission("union.yt")){
                    coins *= 1.5;
                    exp *= 1.5;
                }

                kPlayer_killer.addCurrentEXP(exp);
                kPlayer_killer.addCoins(coins);

                kPlayer_killed.addDeaths(1);
                kPlayer_killer.addKills(1);
                kPlayer_killed.removeCoins(lostCoins);
            }

            //Previne que a mensagem default do minecraft seja mandada
            e.setDeathMessage(null);

            //Envia as mensagens de o jogador ter sido morto e de receber X Stats ou de ser morto
            Bukkit.broadcastMessage("§b" + killed.getDisplayName() + " §7has been killed by §b" + killer.getDisplayName());
            killer.playSound(killer.getLocation(), Sound.ORB_PICKUP, 10f, 10f);

            killer.sendMessage("§e(+" + coins + " coins) §a(+" + exp + " EXP) §cFor killing: §b" + killed.getDisplayName());
            streaks.put(killer, streaks.get(killer) + 1);

            //Killstreaks
            if(streaks.get(killer) == 5){
                Bukkit.broadcastMessage("§8[§aKill§8] §b" + killer.getDisplayName() + " §6is in KillStreak of §b5");
                kPlayer_killer.addCoins(6);
                killer.sendMessage("§8[§aEconomy§8] §aYou have been rewarded with §6" + 6 + " §acoins.");
                Util.getInstance().launchRandomFirework(killer.getLocation());
            }else if(streaks.get(killer) == 8){
                Bukkit.broadcastMessage("§8[§aKill§8] §b" + killer.getDisplayName() + " §6is in KillStreak of §b5");
                kPlayer_killer.addCoins(10);
                killer.sendMessage("§8[§aEconomy§8] §aYou have been rewarded with §6" + 10 + " §acoins.");
                Util.getInstance().launchRandomFirework(killer.getLocation());
            }else if(streaks.get(killer) == 10){
                Bukkit.broadcastMessage("§8[§aKill§8] §b" + killer.getDisplayName() + " §6is in KillStreak of §b5");
                kPlayer_killer.addCoins(20);
                killer.sendMessage("§8[§aEconomy§8] §aYou have been rewarded with §6" + 20 + " §acoins.");
                Util.getInstance().launchRandomFirework(killer.getLocation());
            }else if(streaks.get(killer) == 14){
                Bukkit.broadcastMessage("§8[§aKillStreak§8] §b" + killer.getDisplayName() + " §6is in KillStreak of §b5");
                kPlayer_killer.addCoins(40);
                killer.sendMessage("§8[§aEconomy§8] §aYou have been rewarded with §6" + 40 + " §acoins.");
                Util.getInstance().launchRandomFirework(killer.getLocation());
            }else if(streaks.get(killer) == 18){
                Bukkit.broadcastMessage("§8[§aKillStreak§8] §b" + killer.getDisplayName() + " §6is in KillStreak of §b5");
                kPlayer_killer.addCoins(70);
                killer.sendMessage("§8[§aEconomy§8] §aYou have been rewarded with §6" + 70 + " §acoins.");
                Util.getInstance().launchRandomFirework(killer.getLocation());
            }else if(streaks.get(killer) == 20){
                Bukkit.broadcastMessage("§8[§aKill§8] §b" + killer.getDisplayName() + " §6is in KillStreak of §b5");
                kPlayer_killer.addCoins(90);
                killer.sendMessage("§8[§aEconomy§8] §aYou have been rewarded with §6" + 90 + " §acoins.");
                Util.getInstance().launchRandomFirework(killer.getLocation());
            }else if(streaks.get(killer) == 23){
                Bukkit.broadcastMessage("§8[§aKill§8] §b" + killer.getDisplayName() + " §6is in KillStreak of §b5");
                killer.sendMessage("§8[§aEconomy§8] §aYou have been rewarded with §6" + 160 + " §acoins.");
                Util.getInstance().launchRandomFirework(killer.getLocation());
                kPlayer_killer.addCoins(160);
            }

            killed.sendMessage("§8[§aEconomy§8] §aYou have lost §6" + lostCoins + " §acoins.");
            killed.sendMessage("§8[§aDeath§8] §b" + killer.getDisplayName() + " §7had §b" + killer.getHealth() + " §c❤ §7left.");
            killed.sendMessage("§8[§aDeath§8] §7You were killed by §b" + killer.getDisplayName());
            killed.sendMessage("§8[§aDeath§8] §b" + killer.getDisplayName() + " §7was using §b" + km.getKitByPlayer(killer).getName() + " §7kit.");
            streaks.put(killed, 0);
        }

        if ((killer == null) || (e.getEntity().getKiller() == null)) {
            EntityDamageEvent.DamageCause damage = e.getEntity().getLastDamageCause().getCause();
            if (damage == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION ||
                    damage == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) {
                Bukkit.broadcastMessage("§a" + killed.getName() + "§c has died by explosion");
                e.setDeathMessage(null);
            }
            if (damage == EntityDamageEvent.DamageCause.LAVA) {
                Bukkit.broadcastMessage("§a" + killed.getName() + "§c has died by lava");
                e.setDeathMessage(null);
            }
            if (damage == EntityDamageEvent.DamageCause.FALL) {
                Bukkit.broadcastMessage("§a" + killed.getName() + "§c thought it was a bird and died");
                e.setDeathMessage(null);
            }
            if (damage == EntityDamageEvent.DamageCause.FIRE ||
                    damage == EntityDamageEvent.DamageCause.FIRE_TICK) {
                Bukkit.broadcastMessage("§a" + killed.getName() + "§c has died by fire");
                e.setDeathMessage(null);
            }
            if (damage == EntityDamageEvent.DamageCause.MAGIC) {
                Bukkit.broadcastMessage("§a" + killed.getName() + "§c has died by magic");
                e.setDeathMessage(null);
            }
            if (damage == EntityDamageEvent.DamageCause.DROWNING) {
                Bukkit.broadcastMessage("§a" + killed.getName() + "§c thought it was a fish and died");
                e.setDeathMessage(null);
            }
            if (damage == EntityDamageEvent.DamageCause.WITHER) {
                Bukkit.broadcastMessage("§a" + killed.getName() + "§c has died by wither");
                e.setDeathMessage(null);
            }if (damage == EntityDamageEvent.DamageCause.POISON) {
                Bukkit.broadcastMessage("§a" + killed.getName() + "§c has died by poison");
                e.setDeathMessage(null);
            }
            if (damage == EntityDamageEvent.DamageCause.FALLING_BLOCK) {
                Bukkit.broadcastMessage("§a" + killed.getName() + "§c has died by falling block");
                e.setDeathMessage(null);
            }
            if (damage == EntityDamageEvent.DamageCause.LIGHTNING) {
                Bukkit.broadcastMessage("§a" + killed.getName() + "§c has died by lightning");
                e.setDeathMessage(null);
            }
            if (damage == EntityDamageEvent.DamageCause.PROJECTILE) {
                Bukkit.broadcastMessage("§a" + killed.getName() + "§c has died by projectile");
                e.setDeathMessage(null);
            }
            if (damage == EntityDamageEvent.DamageCause.VOID) {
                Bukkit.broadcastMessage("§a" + killed.getName() + "§c has died by void");
                e.setDeathMessage(null);
            }
            if (damage == EntityDamageEvent.DamageCause.SUICIDE) {
                Bukkit.broadcastMessage("§a" + killed.getName() + "§c has died by suicide");
                e.setDeathMessage(null);
            }
        }

        if(km.usingKit(killed)){
            km.removeKit(killed);
        }else{
            km.readyPlayer(killed);
        }


        e.getDrops().removeIf(k ->
                k != null && !(
                        k.getType() == Material.MUSHROOM_SOUP ||
                                k.getType() == Material.RED_MUSHROOM ||
                                k.getType() == Material.BROWN_MUSHROOM ||
                                k.getType() == Material.BOWL
                )
        );

        /*
            Delay para teleportar, pois senão os items
            são dropados no spawn.
         */
       new BukkitRunnable() {
            @Override
            public void run() {
                killed.teleport(loc);
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

}
