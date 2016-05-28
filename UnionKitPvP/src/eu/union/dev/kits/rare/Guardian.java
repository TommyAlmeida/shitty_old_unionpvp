package eu.union.dev.kits.rare;

import eu.union.dev.PvPMain;
import eu.union.dev.api.Ability;
import eu.union.dev.api.Icon;
import eu.union.dev.engine.Kit;
import eu.union.dev.engine.managers.KitManager;
import eu.union.dev.utils.Weapon;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.concurrent.TimeUnit;

/**
 * Created by Fentis on 28/05/2016.
 */
public class Guardian extends Kit implements Listener{

    public Guardian() {
        super("guardian", "guardian.unkit", Difficulty.LOW, Rarity.RARE, 0, new Icon(Material.PRISMARINE_SHARD));
    }

    @Override
    public void applyKit(Player player) {
        Weapon.giveWeapon(player, Weapon.DEFAULT_SWORD);

    }

    Ability cooldown = new Ability(1, 60, TimeUnit.SECONDS);
    @EventHandler
    public void onclick(PlayerInteractEvent e){
        final Player p = e.getPlayer();
        KitManager km = KitManager.getManager();
        if (km.getKitAmIUsing(p,"guardian") && p.getItemInHand().getType() == Material.PRISMARINE_SHARD){
            if (cooldown.tryUse(p)){
                ArmorStand as = p.getWorld().spawn(p.getLocation(), ArmorStand.class);
                final org.bukkit.entity.Guardian g = p.getWorld().spawn(p.getLocation(), org.bukkit.entity.Guardian.class);
                as.setBasePlate(false);
                as.setVisible(false);
                as.setSmall(false);
                as.setPassenger(g);
                g.setMaxHealth(100.0);
                g.setHealth(100.0);
                g.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 60*20, 10), true);
                Bukkit.getScheduler().scheduleSyncDelayedTask(PvPMain.getInstance(), new Runnable() {
                    @Override
                    public void run() {
                        as.eject();
                        as.remove();
                        g.remove();
                    }
                }, 30*20);
            }else{
                p.sendMessage("CD");
            }
        }
    }
    @EventHandler
    public void ontarget(EntityTargetEvent e){
        if (e.getEntity() instanceof org.bukkit.entity.Guardian &&
                e.getTarget() instanceof Player){
            KitManager km = KitManager.getManager();
            if (km.getKitAmIUsing((Player)e.getTarget(),"guardian")){
                e.setCancelled(true);
            }
        }
    }
}
