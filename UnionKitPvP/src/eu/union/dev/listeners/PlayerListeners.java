package eu.union.dev.listeners;

import eu.union.dev.PvPMain;
import eu.union.dev.engine.KPlayer;
import eu.union.dev.engine.managers.KitManager;
import eu.union.dev.engine.managers.PlayerManager;
import eu.union.dev.utils.Messages;
import eu.union.dev.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class PlayerListeners implements Listener {

    KitManager km = KitManager.getManager();

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        Player player = e.getPlayer();
        KitManager km = KitManager.getManager();

        if (km.usingKit(player))
            km.removeKit(player);

        e.setQuitMessage(null);
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


        welcomeMessage(p);
        Util.getInstance().buildJoinIcons(p);
    }

    @EventHandler
    public void onDeath(EntityDeathEvent e){
        if (!(e.getEntity() instanceof Player))
            return;

        Player player = (Player) e.getEntity();

        if (km.usingKit(player)) {
            km.removeKit(player);
            e.getDrops().clear();
            e.setDroppedExp(0);
        }
    }

    void welcomeMessage(Player p){
        p.sendMessage("§m§7§l--------------------");
        p.sendMessage("§eUnionPvP");
        p.sendMessage("§bPowered by UnionNetwork");
        p.sendMessage(" ");
        p.sendMessage("§7Are you ready? if yes, go ahead and choose your kit.");
        p.sendMessage(" ");
        p.sendMessage("§aCommands:");
        p.sendMessage("§e/kits §7- to list all kits available");
        p.sendMessage("§e/kit <name> §7- to select your kit");
        p.sendMessage("§m§7§l-------------------");
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e){
        String prefix = PermissionsEx.getUser(e.getPlayer()).getGroups()[0].getPrefix();
        KPlayer kPlayer = PlayerManager.getPlayer(e.getPlayer().getUniqueId());

        e.setCancelled(true);
        Bukkit.broadcastMessage("§8" + kPlayer.getLevel() + " " + prefix + " §r§7" + e.getPlayer().getName() + ": §f" + e.getMessage());
    }

    @EventHandler
    void onDrop(final PlayerDropItemEvent event) {

        Player player = event.getPlayer();
        ItemStack i = event.getItemDrop().getItemStack();

        if (!km.usingKit(player))
            return;

        if (i.getType() != Material.MUSHROOM_SOUP && i.getType() != Material.BOWL) {
            player.sendMessage(Messages.PREFIX.toString() + " §7cYou cannot drop this item");
            event.setCancelled(true);
        } else {
            Bukkit.getScheduler().runTaskLater(PvPMain.getInstance(), new Runnable() {
                @Override
                public void run() {
                    event.getItemDrop().remove();
                }
            }, 8L);
        }

    }

}
