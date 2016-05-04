package eu.union.dev.listeners.menus;

import eu.union.dev.api.Icon;
import eu.union.dev.engine.managers.KitManager;
import eu.union.dev.engine.layouts.KitLayout;
import eu.union.dev.utils.Inv;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class KitMenu implements Listener {

    @EventHandler
    public void onClick(PlayerInteractEvent e){
        Player p = e.getPlayer();
        ItemStack item = p.getItemInHand();

        if(item == null){
            return;
        }

        if(!(item.getType() == Material.NETHER_STAR)){
            return;
        }

        if(item.getItemMeta() == null){
            return;
        }

        if(!(item.getItemMeta().hasDisplayName())){
            return;
        }

        if(!(item.getItemMeta().getDisplayName() == "§aKits §7(Right-Click)")){
            return;
        }

        e.setCancelled(true);
        setItems(p);
        p.openInventory(Inv.getInstance().kits);
    }

    void setItems(Player p){
        KitManager km = KitManager.getManager();

        Inventory inv = Inv.getInstance().kits;

        {
            Icon icon = new Icon(Material.WOOD_SWORD, "");
            inv.setItem(0, KitLayout.getLayout().design(icon, km.getKitByName("pvp")));
        }

        {
            Icon icon = new Icon(Material.WOOD_SWORD);
            inv.setItem(0, KitLayout.getLayout().design(icon, km.getKitByName("pvp")));
        }

        {
            Icon icon = new Icon(Material.STICK);
            inv.setItem(0, KitLayout.getLayout().design(icon, km.getKitByName("pvp")));
        }

    }

    @EventHandler
    public void onInvClick(InventoryClickEvent e){
        Player p = (Player) e.getWhoClicked();
        ItemStack item = e.getCurrentItem();
        KitManager km = KitManager.getManager();

        if(e.getInventory().equals("Kits")){
            if(e.getSlot() < 0){
                return;
            }

            switch(e.getSlot()){
                case 0: //Kit PvP
                    km.getKitByName("pvp");
                    e.setCancelled(true);
                    break;
                case 1: //Kit Archer
                    km.getKitByName("archer");
                    e.setCancelled(true);
                    break;
                case 2: //Kit GrandPa
                    km.getKitByName("grandpa");
                    e.setCancelled(true);
                    break;
            }
        }
    }
}
