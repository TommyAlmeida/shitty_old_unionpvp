package eu.union.dev.kits.heroic;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import com.sk89q.worldedit.util.Location;

import eu.union.dev.PvPMain;
import eu.union.dev.api.Ability;
import eu.union.dev.engine.Kit;
import eu.union.dev.engine.managers.KitManager;
import eu.union.dev.utils.Util;
import eu.union.dev.utils.Weapon;


public class IceCube extends Kit implements Listener{

	
	
	/**
	 * Created by Owen 19-5-2016
	 */
	public IceCube() {
		super("icecube", "unkit.icecube", Difficulty.HARD, Rarity.HEROIC, 0);
	
	}


	ArrayList<String> repepete = new ArrayList();

	public void applyKit(Player player) {
	
		Weapon.giveWeapon(player, Weapon.DEFAULT_SWORD);
		Weapon.giveWeapon(player, Weapon.ICECUBE_ITEM);		
	}
	
	@SuppressWarnings("deprecation")
	public void startParticule(Player c){
		
		repepete.add(c.getName());
		
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(PvPMain.getInstance(), new Runnable() {
			public void run() {
				
				if(repepete.contains(c.getName())) {
				
		            c.getLocation().getWorld().playEffect(c.getLocation().add(2.0D, 0.0D, 0.0D), Effect.STEP_SOUND, Material.ICE);
		            c.getLocation().getWorld().playEffect(c.getLocation().add(2.0D, 2.0D, 0.0D), Effect.MAGIC_CRIT, Material.ICE);
		            c.getLocation().getWorld().playEffect(c.getLocation().add(0.0D, 0.0D, 2.0D), Effect.STEP_SOUND, Material.ICE);
		            c.getLocation().getWorld().playEffect(c.getLocation().add(0.0D, 2.0D, 2.0D), Effect.STEP_SOUND, Material.ICE);
				}
			}
		}, 0L, 10L);
		Bukkit.getServer().getScheduler().scheduleAsyncDelayedTask(PvPMain.getInstance(), new Runnable() {
			public void run() {
	            c.getLocation().getWorld().playEffect(c.getLocation().add(2.0D, 0.0D, 0.0D), Effect.STEP_SOUND, Material.ICE);
	            c.getLocation().getWorld().playEffect(c.getLocation().add(2.0D, 2.0D, 0.0D), Effect.MAGIC_CRIT, Material.ICE);
	            c.getLocation().getWorld().playEffect(c.getLocation().add(0.0D, 0.0D, 2.0D), Effect.STEP_SOUND, Material.ICE);
	            c.getLocation().getWorld().playEffect(c.getLocation().add(0.0D, 2.0D, 2.0D), Effect.STEP_SOUND, Material.ICE);
			}
		}, 80L);
		
	}
	public void cubeCreate(Player c){
		
	      c.getLocation().add(0.0D, 1.0D, 1.0D).getBlock().setType(Material.ICE);
	      c.getLocation().add(1.0D, 1.0D, 0.0D).getBlock().setType(Material.ICE);
	      c.getLocation().add(1.0D, 0.0D, 0.0D).getBlock().setType(Material.ICE);
	      c.getLocation().add(0.0D, 0.0D, 1.0D).getBlock().setType(Material.ICE);
	      c.getLocation().add(0.0D, 0.0D, -1.0D).getBlock().setType(Material.ICE);
	      c.getLocation().add(-1.0D, 0.0D, 0.0D).getBlock().setType(Material.ICE); 
	      c.getLocation().add(0.0D, 2.0D, 1.0D).getBlock().setType(Material.ICE);
	      c.getLocation().add(1.0D, 2.0D, 0.0D).getBlock().setType(Material.ICE); 
	      c.getLocation().add(0.0D, 2.0D, -1.0D).getBlock().setType(Material.ICE);
	      c.getLocation().add(-1.0D, 2.0D, 0.0D).getBlock().setType(Material.ICE); 
	      
	      c.getLocation().add(-1.0D, 2.0D, 1.0D).getBlock().setType(Material.ICE); 
	      c.getLocation().add(-1.0D, 1.0D, 1.0D).getBlock().setType(Material.ICE);
	      c.getLocation().add(-1.0D, 0.0D, 1.0D).getBlock().setType(Material.ICE); 
	      
	      c.getLocation().add(1.0D, 2.0D, -1.0D).getBlock().setType(Material.ICE); 
	      c.getLocation().add(1.0D, 1.0D, -1.0D).getBlock().setType(Material.ICE);
	      c.getLocation().add(1.0D, 0.0D, -1.0D).getBlock().setType(Material.ICE);
	      
	      c.getLocation().add(1.0D, 2.0D, 1.0D).getBlock().setType(Material.ICE); 
	      c.getLocation().add(1.0D, 1.0D, 1.0D).getBlock().setType(Material.ICE);
	      c.getLocation().add(1.0D, 0.0D, 1.0D).getBlock().setType(Material.ICE);
	      
	      c.getLocation().add(-1.0D, 2.0D, -1.0D).getBlock().setType(Material.ICE); 
	      c.getLocation().add(-1.0D, 1.0D, -1.0D).getBlock().setType(Material.ICE);
	      c.getLocation().add(-1.0D, 0.0D, -1.0D).getBlock().setType(Material.ICE);
	      
	      c.getLocation().add(0.0D, 2.0D, 0.0D).getBlock().setType(Material.ICE);
	      
	      c.getLocation().add(-1.0D, 1.0D, 0.0D).getBlock().setType(Material.ICE);
	      c.getLocation().add(0.0D, 1.0D, -1.0D).getBlock().setType(Material.ICE); 
      
	      c.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 124, 3));
	      c.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 124, 4));
      
	      c.sendMessage("§aAAAAAAAAAAAAGH!...");
    
	      Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(PvPMain.getInstance(), new Runnable() {
			public void run() {
			
			      c.getLocation().add(0.0D, 1.0D, 1.0D).getBlock().setType(Material.AIR);
			      c.getLocation().add(1.0D, 1.0D, 0.0D).getBlock().setType(Material.AIR);
			      c.getLocation().add(1.0D, 0.0D, 0.0D).getBlock().setType(Material.AIR);
			      c.getLocation().add(0.0D, 0.0D, 1.0D).getBlock().setType(Material.AIR);
			      c.getLocation().add(0.0D, 0.0D, -1.0D).getBlock().setType(Material.AIR);
			      c.getLocation().add(-1.0D, 0.0D, 0.0D).getBlock().setType(Material.AIR); 
			      c.getLocation().add(0.0D, 2.0D, 1.0D).getBlock().setType(Material.AIR);
			      c.getLocation().add(1.0D, 2.0D, 0.0D).getBlock().setType(Material.AIR); 
			      c.getLocation().add(0.0D, 2.0D, -1.0D).getBlock().setType(Material.AIR);
			      c.getLocation().add(-1.0D, 2.0D, 0.0D).getBlock().setType(Material.AIR); 
			      
			      c.getLocation().add(-1.0D, 2.0D, 1.0D).getBlock().setType(Material.AIR); 
			      c.getLocation().add(-1.0D, 1.0D, 1.0D).getBlock().setType(Material.AIR);
			      c.getLocation().add(-1.0D, 0.0D, 1.0D).getBlock().setType(Material.AIR); 
			      
			      c.getLocation().add(1.0D, 2.0D, -1.0D).getBlock().setType(Material.AIR); 
			      c.getLocation().add(1.0D, 1.0D, -1.0D).getBlock().setType(Material.AIR);
			      c.getLocation().add(1.0D, 0.0D, -1.0D).getBlock().setType(Material.AIR);
			      
			      c.getLocation().add(1.0D, 2.0D, 1.0D).getBlock().setType(Material.AIR); 
			      c.getLocation().add(1.0D, 1.0D, 1.0D).getBlock().setType(Material.AIR);
			      c.getLocation().add(1.0D, 0.0D, 1.0D).getBlock().setType(Material.AIR);
			      
			      c.getLocation().add(-1.0D, 2.0D, -1.0D).getBlock().setType(Material.AIR); 
			      c.getLocation().add(-1.0D, 1.0D, -1.0D).getBlock().setType(Material.AIR);
			      c.getLocation().add(-1.0D, 0.0D, -1.0D).getBlock().setType(Material.AIR);
			      
			      c.getLocation().add(0.0D, 2.0D, 0.0D).getBlock().setType(Material.AIR);
			      
			      c.getLocation().add(-1.0D, 1.0D, 0.0D).getBlock().setType(Material.AIR); // VUDRI
			      c.getLocation().add(0.0D, 1.0D, -1.0D).getBlock().setType(Material.AIR);
			}
		}, 100L);
		
	}
	  @EventHandler
	  public void dano(EntityDamageByEntityEvent e)
	  {
	    if (((e.getEntity() instanceof Player)&&((e.getDamager() instanceof Player))&&((e.getDamager() instanceof Snowball))))
	    {
	      Player c = (Player)e.getEntity();
	      
	      Snowball s = (Snowball)e.getDamager();
	      Player p1 = (Player) e.getDamager();
	      KitManager km = KitManager.getManager();
	      if (km.getKitAmIUsing(p1, "icecube")) {
	
	    	  
	          e.setDamage(e.getDamage() + 3.0D);
	      
	        // CRIAR UMA ARENA DE GELO
	        
	          cubeCreate(c);
	          startParticule(c);

	      }
	    }
	  }
	 
	@EventHandler
	public void onInteragir(PlayerInteractEvent e) {
		
		Player p = (Player) e.getPlayer();
		
		Ability cd = new Ability(1, 12, TimeUnit.SECONDS);
        KitManager km = KitManager.getManager();
        
        if (p.getItemInHand().getType() == Material.PACKED_ICE && km.getKitAmIUsing(p, "icecube")){
        	
        	if(cd.tryUse(p)) {
        		
        		Vector velo2 = new Vector(0.3, 0.5, 0.3);
                ((Snowball)p.launchProjectile(Snowball.class)).setVelocity(velo2);
                
        		
        	}else{
        		
        		Util.getInstance().sendCooldownMessage(p, cd, TimeUnit.SECONDS, true);
        		
        	}
        }
	}

}
