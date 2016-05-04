package eu.union.dev.listeners;

import eu.union.dev.PvPMain;
import eu.union.dev.engine.KPlayer;
import eu.union.dev.engine.managers.KitManager;
import eu.union.dev.engine.managers.PlayerManager;
import eu.union.dev.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class PlayerListeners implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        Player player = e.getPlayer();
        KitManager km = KitManager.getManager();

        km.getKits().remove(player);

        e.setQuitMessage(null);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        KitManager km = KitManager.getManager();

        e.setJoinMessage(null);
        PvPMain.getInstance().getSQL().createPlayerProfile(p.getUniqueId());

        km.readyPlayer(p);

        welcomeMessage(p);
        Util.getInstance().buildJoinIcons(p);
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
        Bukkit.broadcastMessage("§c" + kPlayer.getLevel() + " §8: " + prefix + " §r§7" + e.getPlayer().getName() + ": §f" + e.getMessage());
    }

}
