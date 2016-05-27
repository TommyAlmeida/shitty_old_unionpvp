package eu.union.dev.kits.common;

import eu.union.dev.api.Icon;
import eu.union.dev.engine.Kit;
import eu.union.dev.engine.managers.KitManager;
import eu.union.dev.utils.Weapon;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

/**
 * Created by Fentis on 26/05/2016.
 */
public class Fireman extends Kit implements Listener{

    public Fireman() {
        super("fireman", "unkit.fireman", Difficulty.LOW, Rarity.COMMON, 0, new Icon(Material.WATER_BUCKET));
    }

    @Override
    public void applyKit(Player player) {
        Weapon.giveWeapon(player, Weapon.DEFAULT_SWORD);
    }

    @EventHandler
    public void ondamage(EntityDamageEvent e){
        if (e.getEntity() instanceof Player){
            KitManager km = KitManager.getManager();
            if (km.getKitAmIUsing((Player)e.getEntity(),"fireman")){
                if (e.getCause() == EntityDamageEvent.DamageCause.LAVA ||
                        e.getCause() == EntityDamageEvent.DamageCause.FIRE ||
                        e.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK){
                    e.setCancelled(true);
                }
            }
        }
    }
}
