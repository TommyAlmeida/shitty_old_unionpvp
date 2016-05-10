package eu.union.dev.listeners.menus;

import eu.union.dev.api.Icon;
import eu.union.dev.engine.layouts.KitLayout;
import eu.union.dev.engine.managers.KitManager;
import eu.union.dev.utils.Inv;
import eu.union.dev.utils.Messages;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class MainMenu implements Listener {

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        ItemStack item = p.getItemInHand();

        if (item == null) {
            return;
        }

        if (!(item.getType() == Material.COMPASS)) {
            return;
        }

        if (item.getItemMeta() == null) {
            return;
        }

        if (!(item.getItemMeta().hasDisplayName())) {
            return;
        }

        if (!(item.getItemMeta().getDisplayName() == "§bMenu §7(Right-Click)")) {
            return;
        }

        e.setCancelled(true);
        setItems(p);
        p.openInventory(Inv.getInstance().menu);
    }

    void setItems(Player p) {
        Inventory inv = Inv.getInstance().menu;

        for(int i = 0; i < 27; i++){
            Icon spacer = new Icon(Material.THIN_GLASS, "§9");
            inv.setItem(i,spacer.build());
        }

        {
            Icon warps = new Icon(Material.COMPASS, "§7» §bWarps §7«");
            inv.setItem(11,warps.build());
        }

        {
            Icon warps = new Icon(Material.EXP_BOTTLE, "§7» §aShop §7«");
            inv.setItem(13,warps.build());
        }

        {
            Icon warps = new Icon(Material.REDSTONE, "§7» §cSettings §7«");
            inv.setItem(15,warps.build());
        }
    }

    @EventHandler
    public void onInvClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        ItemStack item = e.getCurrentItem();

        if (e.getInventory().getName().equalsIgnoreCase("Menu")) {
            if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§9")){
                e.setCancelled(true);
            }

            if (e.getSlot() < 0) {
                return;
            }

            switch (e.getSlot()){
                case 11:
                    e.setCancelled(true);
                    p.openInventory(Inv.getInstance().warps);
                    break;
                case 13:
                    e.setCancelled(true);
                    //p.openInventory(Inv.getInstance().shop);
                    p.sendMessage(Messages.PREFIX.toString() + " §cComing soon!");
                    e.getView().close();
                    break;
                case 15:
                    e.setCancelled(true);
                    p.openInventory(Inv.getInstance().config);
                    break;
            }
        }
    }

}
