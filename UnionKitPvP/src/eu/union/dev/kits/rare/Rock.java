package eu.union.dev.kits.rare;

import eu.union.dev.api.Icon;
import eu.union.dev.engine.Kit;
import eu.union.dev.engine.managers.KitManager;
import eu.union.dev.utils.globals.Util;
import eu.union.dev.utils.globals.Weapon;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

/**
 * Created by Fentis on 16/06/2016.
 */
public class Rock extends Kit implements Listener{

    public Rock() {
        super("rock", "unkit.rock", Difficulty.LOW, Rarity.RARE, 3, new Icon(Material.COBBLESTONE), Category.PROTECTED, 1000);
    }

    @Override
    public void applyKit(Player player) {
        Weapon.giveWeapon(player,Weapon.DEFAULT_SWORD);
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e){
        if (e.getEntity() instanceof Player && e.getDamager() instanceof Player){
            Player p1 = (Player)e.getEntity();
            Player p2 = (Player)e.getDamager();
            KitManager km = KitManager.getManager();
            if (km.getKitAmIUsing(p1,"rock") && Util.getInstance().inPvP(p2)){
                if (e.getDamage() >=2.0){
                    e.setDamage(e.getDamage()-2.0);
                }
            }
            if (km.getKitAmIUsing(p2,"rock") && Util.getInstance().inPvP(p1)){
                e.setDamage(e.getDamage()+2.0);
            }
        }
    }
}
