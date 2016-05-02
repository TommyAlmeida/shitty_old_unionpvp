package eu.union.dev.listeners.mechanics;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SoupListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if ((event.getAction() == Action.RIGHT_CLICK_AIR) || (event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            int heal = 7;
            int feed = 7;
            if (event.getPlayer().getItemInHand().getType() == Material.MUSHROOM_SOUP) {
                ItemStack bowl = new ItemStack(Material.BOWL, 1);
                ItemMeta meta = bowl.getItemMeta();
                if ((event.getPlayer().getHealth() < 20) && (event.getPlayer().getHealth() > 0)) {
                    if (event.getPlayer().getHealth() < 20 - heal + 1) {
                        event.getPlayer().getItemInHand().setType(Material.BOWL);
                        event.getPlayer().getItemInHand().setItemMeta(meta);
                        event.getPlayer().setItemInHand(bowl);
                        event.getPlayer().setHealth(event.getPlayer().getHealth() + heal);
                    } else if ((event.getPlayer().getHealth() < 20) && (event.getPlayer().getHealth() > 20 - heal)) {
                        event.getPlayer().setHealth(20);
                        event.getPlayer().getItemInHand().setType(Material.BOWL);
                        event.getPlayer().getItemInHand().setItemMeta(meta);
                        event.getPlayer().setItemInHand(bowl);
                    }
                } else if ((event.getPlayer().getHealth() == 20) && (event.getPlayer().getFoodLevel() < 20)) {
                    if (event.getPlayer().getFoodLevel() < 20 - feed + 1) {
                        event.getPlayer().setFoodLevel(event.getPlayer().getFoodLevel() + feed);
                        event.getPlayer().getItemInHand().setType(Material.BOWL);
                        event.getPlayer().getItemInHand().setItemMeta(meta);
                        event.getPlayer().setItemInHand(bowl);
                    } else if ((event.getPlayer().getFoodLevel() < 20) && (event.getPlayer().getFoodLevel() > 20 - feed)) {
                        event.getPlayer().setFoodLevel(20);
                        event.getPlayer().getItemInHand().setType(Material.BOWL);
                        event.getPlayer().getItemInHand().setItemMeta(meta);
                        event.getPlayer().setItemInHand(bowl);
                    }
                }
            }
        }
    }
}
