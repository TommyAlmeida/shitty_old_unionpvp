package eu.union.dev.listeners.mechanics;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import eu.union.dev.engine.managers.KitManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;

/**
 * Created by Fentis on 19/05/2016.
 */
public class GiveKitInArea implements Listener{

    @EventHandler
    public void onmove(PlayerMoveEvent e){
        Player p = e.getPlayer();
        KitManager km = KitManager.getManager();
        if (km.getKitByPlayer(p) == null && getWorldGuard() != null){
            ApplicableRegionSet set = getWorldGuard().getRegionManager(p.getWorld()).getApplicableRegions(p.getLocation());
            for (ProtectedRegion region : set){
                if (region.getId().equalsIgnoreCase("givekit")){
                    Bukkit.dispatchCommand(p, "kit pvp");
                }
            }
        }
    }
    private WorldGuardPlugin getWorldGuard() {
        Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");
        if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
            return null;
        }
        return (WorldGuardPlugin) plugin;
    }
}
