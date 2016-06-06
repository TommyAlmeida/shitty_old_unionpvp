package eu.union.dev.kits.rare;

import eu.union.dev.PvPMain;
import eu.union.dev.api.Icon;
import eu.union.dev.engine.Kit;
import eu.union.dev.engine.managers.KitManager;
import eu.union.dev.utils.globals.Util;
import eu.union.dev.utils.globals.Weapon;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class Simba extends Kit{

    /*
    * Created by Owen
    */
	public Simba() {
		super("simba", "unkit.simba", Difficulty.MEDIUM, Rarity.RARE, 6, new Icon(Material.DIAMOND_HELMET), Category.PROTECTED, 1000L);
	}

	@Override
	public void applyKit(Player player) {
	
		Weapon.giveWeapon(player, Weapon.DEFAULT_SWORD);
		
	}
	
	public void onLook(){
		
		for(Player p: Bukkit.getOnlinePlayers()){
			KitManager kit = new KitManager();
			if(kit.getKitAmIUsing(p, "simba")) {
				
				for(Entity around: p.getNearbyEntities(4.0D, 4.0D, 4.0D)){
					
					if(around instanceof Player){
						
						Player p2 = (Player)around;
						
						if (Util.getInstance().inPvP(p2)){
							// Start Looking if someone is near....
							p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 900, 0));
							p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 900, 0));
							p.removePotionEffect(PotionEffectType.WEAKNESS);
						}
						
					}else{
						
						p.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 900 ,1));
					}
				}
			}
		}
	}
	public void StartCheck(){
		
		new BukkitRunnable() {
			
			
			public void run() {
							
				onLook();
			}
		}.runTaskTimer(PvPMain.getInstance(), 0, 20);
	}
		
}