package eu.union.dev.listeners.mechanics.signs;

import org.bukkit.Bukkit;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

public class SoupSign implements Listener {

    Inventory inv = Bukkit.createInventory(null, 54);

    @EventHandler
    public void onSignChange(SignChangeEvent e){
        if(e.getLine(0).equalsIgnoreCase("soup")){
            e.setLine(0, "§6[Refill]");
            e.setLine(1, "§eRight-Click");
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        if(e.getClickedBlock() instanceof Sign){
            Sign sign = (Sign) e.getClickedBlock();
            if(sign.getLine(0).equalsIgnoreCase("§6[Refill]")){

            }
        }
    }
}
