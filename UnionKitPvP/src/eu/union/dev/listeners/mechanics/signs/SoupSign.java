package eu.union.dev.listeners.mechanics.signs;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class SoupSign implements Listener {

    @EventHandler
    public void onSignChange(SignChangeEvent e){
        if(e.getLine(0).equalsIgnoreCase("refill")){
            e.setLine(0, "§6[Refill]");
            e.setLine(1, "§eRight-Click");
        }else if(e.getLine(0).equalsIgnoreCase("soup")){
            e.setLine(0, "§6[Soup]");
            e.setLine(1, "§eRight-Click");
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        if (!(e.getAction() == Action.RIGHT_CLICK_BLOCK)) return;

        if(e.getClickedBlock().getState() instanceof Sign){
            Sign sign = (Sign) e.getClickedBlock();
            if(sign.getLine(0).equalsIgnoreCase("§6[Refill]")){
                Inventory inv = Bukkit.createInventory(null, 9, "Refill");
                for(int i = 0; i < inv.getSize(); i++){
                    inv.setItem(i, new ItemStack(Material.BROWN_MUSHROOM));
                    inv.setItem(i, new ItemStack(Material.RED_MUSHROOM));
                    inv.setItem(i, new ItemStack(Material.BOWL));
                }
            }else if(sign.getLine(0).equalsIgnoreCase("§6[Soup]")){
                Inventory inv = Bukkit.createInventory(null, 54, "Soups");
                for(int i = 0; i < inv.getSize(); i++){
                    inv.setItem(i, new ItemStack(Material.MUSHROOM_SOUP));
                }
            }
        }
    }
}
