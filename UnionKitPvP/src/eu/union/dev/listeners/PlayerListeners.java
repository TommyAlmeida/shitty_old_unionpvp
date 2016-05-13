package eu.union.dev.listeners;

import eu.union.dev.PvPMain;
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

        km.readyPlayer(p);

        Location loc = ConfigManager.getInstance().getLocation("Spawn");
        p.teleport(loc);

        welcomeMessage(p);
        Util.getInstance().buildJoinIcons(p);

    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e){
        Location loc = ConfigManager.getInstance().getLocation("Spawn");
        KitManager km = KitManager.getManager();

        if (km.usingKit(e.getPlayer())) {
            km.removeKit(e.getPlayer());
        }


        e.setRespawnLocation(loc);
        Util.getInstance().buildJoinIcons(e.getPlayer());
    }


    void welcomeMessage(Player p){
        p.sendMessage("§m§7§l--------------------");
        p.sendMessage(Util.getInstance().center("§eUnionPvP", 25));
        p.sendMessage(Util.getInstance().center("§bPowered by UnionNetwork",20));
        p.sendMessage(" ");
        p.sendMessage("§7Are you ready? if yes, go ahead and choose your kit.");
        p.sendMessage(" ");
        p.sendMessage("§m§7§l-------------------");
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

        if (i.getType() != Material.MUSHROOM_SOUP && i.getType() != Material.BOWL) {
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
        KPlayer kPlayer_killer = PlayerManager.getPlayer(killer.getUniqueId());
        Random rand = new Random();
        int coins = rand.nextInt(7);

        if(kPlayer_killed == null || kPlayer_killer == null){
            killed.sendMessage(Messages.PREFIX.toString() + " §cReconnect to the server please.");
            killer.sendMessage(Messages.PREFIX.toString() + " §cReconnect to the server please.");
            return;
        }else{
            kPlayer_killed.addDeaths(1);
            kPlayer_killer.addKills(1);

            if(coins <= 0){
                coins++;
            }

            kPlayer_killer.addCoins(coins);
        }


        e.setDeathMessage(null);

        Bukkit.broadcastMessage("§a" + killer.getDisplayName() + " §chas been slained by §b" + killed.getDisplayName());
        killer.playSound(killer.getLocation(), Sound.ORB_PICKUP, 10f, 10f);
        killer.sendMessage("§6+%coins coins".replace("%coins",String.valueOf(coins)));

        if (km.usingKit(killed)) {
            km.removeKit(killed);
            e.getDrops().clear();
            e.setDroppedExp(0);
        }

        /*Location loc = ConfigManager.getInstance().getLocation("Spawn");
        killed.teleport(loc);*/
    }

}
