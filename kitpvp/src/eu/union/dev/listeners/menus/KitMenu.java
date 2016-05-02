package eu.union.dev.listeners.menus;

import eu.union.dev.api.Icon;
import eu.union.dev.utils.Inv;
import eu.union.dev.utils.Messages;
import org.bukkit.Bukkit;
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

        if(!(item.getItemMeta().getDisplayName() == Messages.ICON_KITS.toString())){
            return;
        }

        e.setCancelled(true);
        p.openInventory(Inv.getInstance().kits);
    }

    void setItems(Player p){
        Inventory inv = Inv.getInstance().kits;

        {
            Icon icon = new Icon(Material.WOOD_SWORD, "§ePvP", "§7Teste", "§7Teste");
            inv.setItem(0, icon.build());
        }
    }

    @EventHandler
    public void onInvClick(InventoryClickEvent e){
        Player p = (Player) e.getWhoClicked();
        ItemStack item = e.getCurrentItem();

        if(e.getInventory().equals("Kits")){
            if(e.getSlot() < 0){
                return;
            }

            switch(e.getSlot()){
                case 0: //Kit PvP
                    Bukkit.dispatchCommand(p, "kit pvp");
                    e.setCancelled(true);
                    break;
                case 1: //Kit Archer
                    Bukkit.dispatchCommand(p, "kit archer");
                    e.setCancelled(true);
                    break;
                case 2: //Kit GrandPa
                    Bukkit.dispatchCommand(p, "kit grandpa");
                    e.setCancelled(true);
                    break;
            }
        }
    }
}
