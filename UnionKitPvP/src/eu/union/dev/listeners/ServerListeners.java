package eu.union.dev.listeners;

import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.player.PlayerChatTabCompleteEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ServerListeners implements Listener {

    /**
     * On player click chest refills with soups
     * @param e
     */
    @EventHandler
    public void refillChest(PlayerInteractEvent e){
        Action a = e.getAction();

        if(!(a == Action.RIGHT_CLICK_BLOCK)) return;

        if(e.getClickedBlock().getState() instanceof Chest){
            Chest c = (Chest) e.getClickedBlock().getState();

            Inventory cinv = c.getInventory();

            for(ItemStack item : cinv.getContents()){
                if(item != null){
                    return;
                }
            }

            cinv.addItem(new ItemStack(Material.MUSHROOM_SOUP));
        }
    }

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent e){
        e.setCancelled(true);
    }

    @EventHandler
    public void onMobSpawn(CreatureSpawnEvent e){
        e.setCancelled(true);
    }


    @EventHandler
    public void onTabComplete(PlayerChatTabCompleteEvent e){
        e.getTabCompletions().clear();
    }

    @EventHandler
    public void onBlockMelt(BlockFadeEvent e){
        e.setCancelled(true);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e){
        e.setCancelled(true);
    }
}
