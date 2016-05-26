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

import java.util.Random;

/**
 * Created by Fentis on 18/05/2016.
 */
public class Magma extends Kit implements Listener{

    public Magma(){super("magma","unkit.magma",Difficulty.LOW,Rarity.RARE,0, new Icon(Material.MAGMA_CREAM));}

    @Override
    public void applyKit(Player player) {
        Weapon.giveWeapon(player, Weapon.DEFAULT_SWORD);
    }

    @EventHandler
    public void onclick(EntityDamageByEntityEvent e){
        if (e.getEntity() instanceof Player &&
                e.getDamager() instanceof Player){
            Player magma = (Player)e.getEntity();
            Player damager = (Player)e.getDamager();
            KitManager km = KitManager.getManager();
            if (km.getKitAmIUsing(magma, "magma")){
                int porcentagem = new Random().nextInt(100);
                if (porcentagem <= 15){
                    damager.setFireTicks((new Random().nextInt(3)+3)*20);
                }
            }
        }
    }
}
