package eu.union.dev.listeners;

import eu.union.dev.api.Icon;
import eu.union.dev.engine.KitManager;
import eu.union.dev.utils.Util;
import org.bukkit.Material;
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

        e.setQuitMessage(null);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        KitManager km = KitManager.getManager();

        e.setJoinMessage(null);
        km.readyPlayer(p);

        welcomeMessage(p);
        Util.buildJoinIcons(p);
    }


    void welcomeMessage(Player p){
        p.sendMessage("§m§7§l-------------------------------");
        p.sendMessage(Util.fixFontSize("§eUnionPvP",0));
        p.sendMessage(Util.fixFontSize("§bPowered by UnionNetwork",0));
        p.sendMessage(" ");
        p.sendMessage(Util.fixFontSize("§7Are you ready? if yes, go ahead and choose your kit.",0));
        p.sendMessage(" ");
        p.sendMessage(Util.fixFontSize("§aCommands:",0));
        p.sendMessage(Util.fixFontSize("§e/kits §7- to list all kits available",0));
        p.sendMessage(Util.fixFontSize("§e/kit <name> §7- to select your kit",0));
        p.sendMessage("§m§7§l--------------------------------");
    }

}
