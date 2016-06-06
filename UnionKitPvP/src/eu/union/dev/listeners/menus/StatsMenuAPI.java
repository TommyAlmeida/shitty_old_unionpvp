package eu.union.dev.listeners.menus;

import eu.union.dev.api.Icon;
import eu.union.dev.engine.KPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;

public class StatsMenuAPI implements Listener{

	/*
	 * This call was created just to use the StatsMenu Inventory but you can use for other thing
	 */
	
	    public static void setItems(Player p, KPlayer vitima){
	    

	   	    Inventory inv = Bukkit.createInventory(null, 9*3, "Check Stats");

	        
	        for(int i = 0; i < 27; i++){
	            Icon spacer = new Icon(Material.STAINED_GLASS_PANE, "§a");
	            inv.setItem(i,spacer.build());
	        }

	        {
	            Icon icon = new Icon(Material.STONE_SWORD, "§9Kills", "§f" + vitima.getKills());
	            inv.setItem(10,icon.build());
	        }

	        {
	            Icon icon = new Icon(Material.LEATHER_HELMET, "§9KDR", "§f" + vitima.getKills());
	            inv.setItem(11,icon.build());
	        }

	        {
	            Icon icon = new Icon(Material.EXP_BOTTLE, "§9Level", "§f" + vitima.getLevel());
	            inv.setItem(12,icon.build());
	        }

	        {
	            Icon icon = new Icon(Material.EMERALD, "§9Coins", "§f" + vitima.getCoins());
	            inv.setItem(13,icon.build());
	        }

	        {
	            Icon icon = new Icon(Material.REDSTONE, "§9Deaths", "§f" + vitima.getDeaths());
	            inv.setItem(14,icon.build());
	        }

	        {
	            Icon icon = new Icon(Material.GOLD_AXE, "§9Streaks &o&cSOON");
	            inv.setItem(15,icon.build());
	        }

	        {
	            Icon icon = new Icon(Material.CHAINMAIL_CHESTPLATE, "§9Assists &o&cSOON");
	            inv.setItem(16,icon.build());
	        }
	        p.openInventory(inv);
	    }

	 
}
