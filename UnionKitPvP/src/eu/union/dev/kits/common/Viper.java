package eu.union.dev.kits.common;

import eu.union.dev.api.Icon;
import eu.union.dev.engine.Kit;
import eu.union.dev.engine.managers.KitManager;
import eu.union.dev.utils.Weapon;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

/**
 * Created by Fentis on 16/05/2016.
 */
public class Viper extends Kit implements Listener{

    public Viper(){ super("viper","unkit.viper",Difficulty.LOW,Rarity.COMMON,0, new Icon(Material.SPIDER_EYE)); }

    @Override
    public void applyKit(Player player) {
        Weapon.giveWeapon(player, Weapon.DEFAULT_SWORD);
    }

    @EventHandler
    public void ondamage(EntityDamageByEntityEvent e){
        if (e.getEntity() instanceof Player &&
                e.getDamager() instanceof Player){
            KitManager km = KitManager.getManager();
            if (km.getKitAmIUsing((Player)e.getDamager(), "viper")){
                if (new Random().nextInt(100) <= 15){
                    ((Player) e.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.POISON,(new Random().nextInt(5)+3)*20,0));
                }
            }
        }
    }
}
