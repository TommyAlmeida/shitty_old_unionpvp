package eu.union.dev.kits.rare;

import eu.union.dev.api.Ability;
import eu.union.dev.api.Packets;
import eu.union.dev.engine.Kit;
import eu.union.dev.engine.managers.KitManager;
import eu.union.dev.utils.Weapon;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.concurrent.TimeUnit;

/**
 * Created by Fentis on 26/05/2016.
 */
public class Balestra extends Kit implements Listener{

    public Balestra() {
        super("balestra", "unkit.balestra", Difficulty.PRO, Rarity.RARE, 0);
    }

    @Override
    public void applyKit(Player player) {
        Weapon.giveWeapon(player, Weapon.DEFAULT_SWORD);
        Weapon.giveWeapon(player, Weapon.DEFAULT_BOW,1);
    }

    Ability cooldown = new Ability(1, 3, TimeUnit.SECONDS);
    @EventHandler
    public void onclick(PlayerInteractEvent e){
        Player p = e.getPlayer();
        KitManager km = KitManager.getManager();
        if (km.getKitAmIUsing(p, "balestra")){
            if (p.getItemInHand().getType() == Material.BOW){
                e.setCancelled(true);
                if (cooldown.tryUse(p)){
                    Arrow a = p.shootArrow();
                    a.setCritical(true);
                    a.setVelocity(a.getVelocity().multiply(2));
                }else{
                    Packets.getAPI().sendActionBar(p,"§a§lRELOADING...");
                }
            }
        }
    }
}
