package eu.union.dev.listeners.mechanics;

import eu.union.dev.PvPMain;
import eu.union.dev.engine.KPlayer;
import eu.union.dev.engine.managers.PlayerManager;
import eu.union.dev.engine.storage.ConfigManager;
import eu.union.dev.utils.globals.Messages;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class BlockListener implements Listener{
	
	/*
	 * BlockListener no Lava Challeng..
	 */
	

	public static ArrayList<String> lc = new ArrayList<>();
	@SuppressWarnings("unchecked")
	ArrayList<String> mecheu=new ArrayList();
	
	@EventHandler
	void onTocar(PlayerMoveEvent e){
		
		Player p = (Player) e.getPlayer();
		KPlayer jogador = PlayerManager.getPlayer(p.getUniqueId());

	    if ((!this.mecheu.contains(p.getName())) && 
	  	      (e.getTo().getBlock().getRelative(BlockFace.DOWN).getType() == Material.EMERALD_BLOCK)){
	    	
	    	if(lc.contains(p.getName())){
	    		p.sendMessage(Messages.PREFIX.toString()+" §7Congratulations!! You just completed Lava Challenge (Easy)");
	    		p.sendMessage(Messages.PREFIX.toString()+" §7You receive 100EXP");
	    		jogador.addCurrentEXP(100);
	            Location loc = ConfigManager.getInstance().getLocation("LavaChallenge");
	            p.teleport(loc);
	            mecheu.add(p.getName());
	            
	            // Certificar se ele não vai repetir o premio!!
	            
	            new BukkitRunnable() {					
					
					public void run() {					
						mecheu.remove(p.getName());
					}
				}.runTaskLater(PvPMain.getInstance(), 20L);
	    	}
	    }
	}
	    
	@EventHandler
	void onTocarMedium(PlayerMoveEvent e){
		
		Player p = (Player) e.getPlayer();
		KPlayer jogador = PlayerManager.getPlayer(p.getUniqueId());

	    if ((!this.mecheu.contains(p.getName())) && 
	  	      (e.getTo().getBlock().getRelative(BlockFace.DOWN).getType() == Material.GOLD_BLOCK)){
	    	
	    	if(lc.contains(p.getName())){
	    		p.sendMessage(Messages.PREFIX.toString()+" §7Congratulations!! You just completed Lava Challenge (Medium)");
	    		p.sendMessage(Messages.PREFIX.toString()+" §7You receive 250EXP");
	    		jogador.addCurrentEXP(250);
	            Location loc = ConfigManager.getInstance().getLocation("LavaChallenge");
	            p.teleport(loc);
	            mecheu.add(p.getName());
	            
	            // Certificar se ele não vai repetir o premio!!
	            
	            new BukkitRunnable() {					
					
					public void run() {					
						mecheu.remove(p.getName());
					}
				}.runTaskLater(PvPMain.getInstance(), 20L);
	    	}
	    }
	}
	    

	@EventHandler
	void onTocarDificil(PlayerMoveEvent e){
		
		Player p = (Player) e.getPlayer();
		KPlayer jogador = PlayerManager.getPlayer(p.getUniqueId());

	    if ((!this.mecheu.contains(p.getName())) && 
	  	      (e.getTo().getBlock().getRelative(BlockFace.DOWN).getType() == Material.REDSTONE_BLOCK)){
	    	
	    	if(lc.contains(p.getName())){
	    		p.sendMessage(Messages.PREFIX.toString()+" §7Congratulations!! You just completed Lava Challenge (Hard)");
	    		p.sendMessage(Messages.PREFIX.toString()+" §7You receive 500EXP");
	    		jogador.addCurrentEXP(500);
	            Location loc = ConfigManager.getInstance().getLocation("LavaChallenge");
	            p.teleport(loc);
	            mecheu.add(p.getName());
	            
	            // Certificar se ele não vai repetir o premio!!
	            
	            new BukkitRunnable() {					
					
					public void run() {					
						mecheu.remove(p.getName());
					}
				}.runTaskLater(PvPMain.getInstance(), 20L);
	    	}
	    }
	}
	    
	@EventHandler
	void onTocarExtreme(PlayerMoveEvent e){
		
		Player p = (Player) e.getPlayer();
		KPlayer jogador = PlayerManager.getPlayer(p.getUniqueId());

	    if ((!this.mecheu.contains(p.getName())) && 
	  	      (e.getTo().getBlock().getRelative(BlockFace.DOWN).getType() == Material.COAL_BLOCK)){
	    	
	    	if(lc.contains(p.getName())){
	    		p.sendMessage(Messages.PREFIX.toString()+" §7Congratulations!! You just completed Lava Challenge (Extreme)");
	    		p.sendMessage(Messages.PREFIX.toString()+" §7You receive 800EXP");
	    		jogador.addCurrentEXP(800);
	            Location loc = ConfigManager.getInstance().getLocation("LavaChallenge");
	            p.teleport(loc);
	            mecheu.add(p.getName());
	            
	            // Certificar se ele não vai repetir o premio!!
	            
	            new BukkitRunnable() {					
					
					public void run() {					
						mecheu.remove(p.getName());
					}
				}.runTaskLater(PvPMain.getInstance(), 20L);
	    	}
	    }
	}
	  



}