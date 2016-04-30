package eu.union.dev.listeners;

import eu.union.dev.engine.KitManager;
import eu.union.dev.utils.Lists;
import eu.union.dev.utils.Util;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListeners implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        Player player = e.getPlayer();
        KitManager km = KitManager.getManager();

        km.getKits().remove(player);
        Lists.kit.remove(player);

        e.setQuitMessage(null);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();

        e.setJoinMessage(null);

        p.sendMessage("§m§7§l-------------------------------------------------");
        p.sendMessage(Util.fixFontSize("§eUnionPvP",31/2));
        p.sendMessage(Util.fixFontSize("§bPowered by UnionNetwork",31/2));
        p.sendMessage(" ");
        p.sendMessage(Util.fixFontSize("§7Are you ready? if yes, go ahead and choose your kit.",31/2));
        p.sendMessage(" ");
        p.sendMessage(Util.fixFontSize("§aCommands:",31/2));
        p.sendMessage(Util.fixFontSize("§e/kits §7- to list all kits available",31/2));
        p.sendMessage(Util.fixFontSize("§e/kit <name> §7- to select your kit",31/2));
        p.sendMessage("§m§7§l-------------------------------------------------");
    }
}
