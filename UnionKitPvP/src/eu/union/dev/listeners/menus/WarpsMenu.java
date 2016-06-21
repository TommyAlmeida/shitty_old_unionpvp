package eu.union.dev.listeners.menus;

import eu.union.dev.api.Icon;
import eu.union.dev.utils.globals.Inv;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class WarpsMenu implements Listener {

    public void setItems() {
        Inventory inv = Inv.getInstance().warps;
        ItemStack i = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 5);
        ItemMeta im = i.getItemMeta();
        im.setDisplayName("§f");
        i.setItemMeta(im);
        for (int j = 0; j < 27; j++) {
            inv.setItem(j, i);
        }

        {
            Icon umxum = new Icon(Material.GLASS, "§7» §cSoon §7«");
            inv.setItem(10, umxum.build());
        }

        {
            Icon fps = new Icon(Material.GLASS, "§7» §aFPS §7«");
            inv.setItem(12, fps.build());
        }

        {
            Icon lc = new Icon(Material.LAVA_BUCKET, "§7» §4Lava Challenge §7«");
            inv.setItem(14, lc.build());
        }

        {
            Icon click = new Icon(Material.GLASS, "§7» §cSoon §7«");
            inv.setItem(16, click.build());
        }
    }

    @EventHandler
    public void onInvClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        ItemStack item = e.getCurrentItem();
        if (e.getInventory().getName().equalsIgnoreCase("Warps")) {
            if (e.getSlot() < 0) {
                return;
            }

            if (item.getType() == Material.STAINED_GLASS_PANE) {
                e.setCancelled(true);
            }
            switch (e.getSlot()) {
                case 10:
                    e.setCancelled(true);
                    e.getView().close();
                    //Bukkit.dispatchCommand(p, "1v1");
                    break;
                case 12:
                    e.setCancelled(true);
                    e.getView().close();
                    Bukkit.dispatchCommand(p, "fps");
                    break;
                case 14:
                    e.setCancelled(true);
                    e.getView().close();
                    Bukkit.dispatchCommand(p, "lavachallenge");
                    break;
                case 16:
                    e.setCancelled(true);
                    e.getView().close();
                    //Bukkit.dispatchCommand(p, "click");
                    break;
            }
        }
    }
}
