package eu.union.dev.kits.rare;

import eu.union.dev.api.Icon;
import eu.union.dev.engine.Kit;
import eu.union.dev.engine.managers.KitManager;
import eu.union.dev.utils.Weapon;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

/**
 * Created by Fentis on 14/05/2016.
 */
public class Turtle extends Kit implements Listener{

    public Turtle(){ super("turtle", "unkit.turtle", Difficulty.MEDIUM, Rarity.RARE, 0, new Icon(Material.IRON_CHESTPLATE), Category.PROTECTED); }

    @Override
    public void applyKit(Player player) {
        Weapon.giveWeapon(player, Weapon.DEFAULT_SWORD);
    }

    @EventHandler
    public void ondamage(EntityDamageEvent e)
    {
        if (e.getEntity() instanceof Player){
            Player p = (Player)e.getEntity();
            KitManager km = KitManager.getManager();
            if (km.getKitAmIUsing(p, "turtle") &&
                    p.isSneaking()){
                e.setDamage(1.0D);
            }
        }
    }
    @EventHandler
    public void semdano(EntityDamageByEntityEvent e){
        if (e.getEntity() instanceof Player && e.getDamager() instanceof Player){
            Player d = (Player)e.getDamager();
            KitManager km = KitManager.getManager();
            if (km.getKitAmIUsing(d, "turtle") &&
                    d.isSneaking()){
                e.setDamage(0.0);//da o knock no player para facilitar a vida do turtle
            }
        }
    }
}
