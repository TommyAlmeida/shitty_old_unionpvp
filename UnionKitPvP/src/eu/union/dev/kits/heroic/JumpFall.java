package eu.union.dev.kits.heroic;

import eu.union.dev.PvPMain;
import eu.union.dev.api.Ability;
import eu.union.dev.api.Icon;
import eu.union.dev.engine.Kit;
import eu.union.dev.engine.managers.KitManager;
import eu.union.dev.utils.Util;
import eu.union.dev.utils.Weapon;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.concurrent.TimeUnit;


public class JumpFall extends Kit implements Listener{

    public JumpFall() {super("jumpfall","unkit.jumpfall",Difficulty.MEDIUM,Rarity.HEROIC,0, new Icon(Material.PAPER));}

    @Override
    public void applyKit(Player player) {
        Weapon.giveWeapon(player, Weapon.DEFAULT_SWORD);
        Weapon.giveWeapon(player, Weapon.JUMPFALL_PAPER, 1);
    }

    Ability cooldown = new Ability(1,30, TimeUnit.SECONDS);
    @EventHandler
    public void onclick(PlayerInteractEvent e){
        final Player p = e.getPlayer();
        KitManager km = KitManager.getManager();
        if (km.getKitAmIUsing(p, "jumpfall") && p.getItemInHand().getType() == Material.PAPER){
            if (cooldown.tryUse(p)){
                p.setVelocity(new Vector(0,10,0));
                new BukkitRunnable() {
                    int i = 0;
                    @Override
                    public void run() {
                        if (i==3){
                            p.setAllowFlight(true);
                            p.setFlying(true);
                        }
                        if (i==6){
                            p.setAllowFlight(false);
                            p.setFlying(false);
                            cancel();
                        }
                        i++;
                    }
                }.runTaskTimer(PvPMain.getInstance(), 0, 20);
            }else{
                Util.getInstance().sendCooldownMessage(p,cooldown,TimeUnit.SECONDS,true);
            }
        }
    }

    @EventHandler
    public void ondamagefall(EntityDamageEvent e){
        if (e.getEntity() instanceof Player) {
            Player p = (Player)e.getEntity();
            KitManager km = KitManager.getManager();
            if (km.getKitAmIUsing(p, "jumpfall") &&
                    e.getCause() == EntityDamageEvent.DamageCause.FALL){
                if (e.getDamage() > 7.0D){
                    e.setDamage(4.0D);
                    for (Entity en : p.getNearbyEntities(3.0, 3.0, 3.0)){
                        if (en instanceof Player){
                            if (!((Player)en).isSneaking()){
                                en.setVelocity(new Vector(0,10,0));
                            }
                        }
                    }
                }
            }
        }
    }
}
