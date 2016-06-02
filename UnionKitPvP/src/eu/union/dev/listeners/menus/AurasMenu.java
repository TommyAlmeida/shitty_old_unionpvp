package eu.union.dev.listeners.menus;

import eu.union.dev.api.Icon;
import eu.union.dev.utils.globals.Inv;
import eu.union.dev.utils.globals.Messages;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class AurasMenu implements Listener {

    void setItems(Player p) {
        Inventory inv = Inv.getInstance().menu;

    }

    @EventHandler
    public void onInvClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        ItemStack item = e.getCurrentItem();

        if (e.getInventory().getName().equalsIgnoreCase("Auras")) {
            if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§9")) {
                e.setCancelled(true);
            }

            if (e.getSlot() < 0) {
                return;
            }

        }
    }

}
