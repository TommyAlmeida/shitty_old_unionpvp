package eu.union.dev.listeners.menus;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Bat;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import eu.union.dev.PvPMain;
import eu.union.dev.api.Icon;
import eu.union.dev.commands.staff.AdminCMD;

public class MenuAdmin implements Listener{
	
	@SuppressWarnings("unused")
	public static void openInventoryAdmin(Player p){
		
		Inventory inv = Bukkit.getServer().createInventory(p, InventoryType.FURNACE, "§a§lADMIN");
	
		Icon ff = new Icon(Material.MONSTER_EGG, "§eTest Killaura", "§7Spawn a bat..");
		Icon nofall = new Icon(Material.ANVIL, "§eTest Nofall", null);
		inv.setItem(0, new ItemStack(Material.STAINED_GLASS_PANE));
		inv.setItem(1, new ItemStack(Material.STAINED_GLASS_PANE));
		inv.setItem(2, new ItemStack(Material.STAINED_GLASS_PANE));
		inv.setItem(3, new ItemStack(Material.MONSTER_EGG));
		inv.setItem(4, new ItemStack(Material.ANVIL));
		inv.setItem(5, new ItemStack(Material.STAINED_GLASS_PANE));
		inv.setItem(6, new ItemStack(Material.STAINED_GLASS_PANE));
		inv.setItem(7, new ItemStack(Material.STAINED_GLASS_PANE));
		inv.setItem(8, new ItemStack(Material.STAINED_GLASS_PANE));
		
		p.openInventory(inv);
	}
	
	@EventHandler
	void ClickNofall(InventoryClickEvent e){
	
		Player p = (Player)e.getWhoClicked();

		
        if(e.getInventory().getName().equalsIgnoreCase("Â§aÂ§lADMIN")){
        	
        	e.setCancelled(true);
        	if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§eTest Nofall")){
            	
        		Player p1 = (Player)AdminCMD.affected.get(p);
        		p1.setVelocity(new Vector(0, 4,0));
        		new BukkitRunnable() {
					
					
					public void run() {
						
						
		        		p1.setVelocity(new Vector(0, -4,0));
						
					}
				}.runTaskLater(PvPMain.getInstance(), 10L);
        	}
        	
        	if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§eTest Killaura")){
        		
        		
        		Player p1 = (Player)AdminCMD.affected.get(p);
        		
        		p.closeInventory();
        		e.setCancelled(true);
        		
        		new BukkitRunnable() {
					
					
					public void run() {
						
						
					    p1.getLocation().getWorld().spawnEntity(p.getLocation().add(0.0D, 0.2D , 1.0D), EntityType.BAT);
						
					}
				}.runTaskLater(PvPMain.getInstance(), 10L);
        	}
        }

	
	}

}
