package eu.union.dev.kits.epic;

import eu.union.dev.PvPMain;
import eu.union.dev.api.Ability;
import eu.union.dev.api.Icon;
import eu.union.dev.engine.Kit;
import eu.union.dev.engine.managers.KitManager;
import eu.union.dev.utils.ParticleEffect;
import eu.union.dev.utils.globals.Weapon;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.concurrent.TimeUnit;

/**
 * Created by Fentis on 02/06/2016.
 */
public class IronHook extends Kit implements Listener{

    public IronHook() {
        super("ironhook", "unkit.ironhook", Difficulty.PRO, Rarity.EPIC, 5, new Icon(Material.TRIPWIRE_HOOK), Category.LONG_DISTANCE);
    }

    @Override
    public void applyKit(Player player) {
        Weapon.giveWeapon(player, Weapon.DEFAULT_SWORD);
        Weapon.giveWeapon(player, Weapon.IRON_HOOK,1);
    }

    Ability cooldown = new Ability(1,10, TimeUnit.SECONDS);
    @EventHandler
    public void onclick(PlayerInteractEvent e){
        Player p = e.getPlayer();
        KitManager km = KitManager.getManager();
        if (p.getItemInHand().getType() == Material.TRIPWIRE_HOOK &&
                km.getKitAmIUsing(p,"ironhook")){
            e.setCancelled(true);
            if (cooldown.tryUse(p)){
                Item item = p.getWorld().dropItem(p.getLocation().add(0,2,0), new ItemStack(Material.TRIPWIRE_HOOK));
                item.setPickupDelay(20*20);
                item.setVelocity(p.getEyeLocation().getDirection().multiply(3));
                new BukkitRunnable() {
                    boolean status = true;
                    int i = 0;
                    @Override
                    public void run() {
                        i++;
                        p.getWorld().playSound(item.getLocation(), Sound.FIRE_IGNITE, 3.0F, 1.0F);
                        ParticleEffect.CRIT.display(0, 0, 0, 1, 0, item.getLocation(), 20);
                        for (Entity en : item.getNearbyEntities(1.5, 2.0, 1.5)){
                            if (en instanceof Player){
                                status = false;
                                ((Player)en).damage(2.5,p);
                                Vector vec = en.getLocation().toVector().subtract(p.getLocation().toVector()).normalize();
                                en.setVelocity(vec.multiply(-en.getLocation().distance(p.getLocation())/6).setY(0.5));
                                item.remove();
                                p.playSound(p.getLocation(), Sound.BAT_HURT, 1.0F, 1.0F);
                            }
                        }
                        if (!status || i >= 5*20 || item.getLocation().add(0, -0.2, 0).getBlock().getType() != Material.AIR){
                            item.remove();
                            cancel();
                        }
                    }
                }.runTaskTimer(PvPMain.getInstance(), 0, 1);
            }
        }
    }
}
