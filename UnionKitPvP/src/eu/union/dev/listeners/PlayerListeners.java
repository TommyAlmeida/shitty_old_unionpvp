package eu.union.dev.listeners;

import eu.union.dev.PvPMain;
import eu.union.dev.api.Packets;
import eu.union.dev.engine.KPlayer;
import eu.union.dev.engine.managers.KitManager;
import eu.union.dev.engine.managers.PlayerManager;
import eu.union.dev.engine.storage.ConfigManager;
import eu.union.dev.kits.common.PvP;
import eu.union.dev.utils.Messages;
import eu.union.dev.utils.Util;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.util.Random;

public class PlayerListeners implements Listener {

    KitManager km = KitManager.getManager();

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        Player player = e.getPlayer();
        KitManager km = KitManager.getManager();
        KPlayer kplayer = PlayerManager.getPlayer(player.getUniqueId());

        if (km.usingKit(player))
            km.removeKit(player);

        if(kplayer != null){
            PvPMain.getInstance().getSQL().updatePlayerProfileSQL(kplayer);
        }else {
            System.out.println("Inexisting PlayerProfile for this Player");
        }

        e.setQuitMessage(null);
        Bukkit.broadcastMessage("§7[§c-§7] §7" + player.getDisplayName());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
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
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e){
        String prefix = PermissionsEx.getUser(e.getPlayer()).getGroups()[0].getPrefix();
        KPlayer kPlayer = PlayerManager.getPlayer(e.getPlayer().getUniqueId());

        e.setCancelled(true);
        Bukkit.broadcastMessage(prefix + " §r§7" + e.getPlayer().getName() + ": §f" + e.getMessage());
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
        } else {
            event.getItemDrop().remove();
        }

    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e){
        Player killed = e.getEntity();
        Player killer = e.getEntity().getKiller();

        KPlayer kPlayer_killed = PlayerManager.getPlayer(killed.getUniqueId());

        if (killer != null) {
            KPlayer kPlayer_killer = PlayerManager.getPlayer(killer.getUniqueId());

            Random rand = new Random();
            int coins = rand.nextInt(7);
            int exp = rand.nextInt(16);

            if(kPlayer_killed == null || kPlayer_killer == null){
                killed.sendMessage(Messages.PREFIX.toString() + " §cReconnect please.");
                killer.sendMessage(Messages.PREFIX.toString() + " §cReconnect please.");
                return;
            }else{
                kPlayer_killed.addDeaths(1);
                kPlayer_killer.addKills(1);

                if(coins <= 0 || exp <= 0){
                    coins++;
                    exp++;
                }

                kPlayer_killer.addCoins(coins);
                kPlayer_killer.addEXP(exp);
            }

            e.setDeathMessage(null);

            Bukkit.broadcastMessage("§a" + killed.getDisplayName() + " §chas been slained by §b" + killer.getDisplayName());
            killer.playSound(killer.getLocation(), Sound.ORB_PICKUP, 10f, 10f);

            killer.sendMessage("§e(+" + coins +  " coins) §a(+" + exp + " EXP) §cFor killing: §b" + killed.getDisplayName());

            killed.sendMessage("§cYou have been killed by §b" + killer.getDisplayName());
        }

        /**
         * Remove todos os items do chão
         */
        e.getDrops().clear();

        if (km.usingKit(killed)) {
            km.removeKit(killed);
            e.setDroppedExp(0);
        } else {
            km.readyPlayer(killed);
        }

        /*
            Dropar somente Sopas, Cogumelos e potes.
         */
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
                Util.getInstance().buildJoinIcons(killed);
                Location loc = ConfigManager.getInstance().getLocation("Spawn");
                killed.teleport(loc);
            }
        }.runTaskLater(PvPMain.getInstance(), 5);
    }

    @EventHandler
    public void ondamage(EntityDamageByEntityEvent e){
        if (e.getEntity() instanceof Player){
            Player p = (Player)e.getEntity();
            if (!Util.getInstance().inPvP(p)){
                e.setCancelled(true);
            }
        }
        if (e.getDamager() instanceof Player){
            Player p = (Player)e.getDamager();
            if (!Util.getInstance().inPvP(p)){
                e.setCancelled(true);
            }
        }
    }
}
