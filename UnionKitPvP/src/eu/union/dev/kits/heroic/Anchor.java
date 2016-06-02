package eu.union.dev.kits.heroic;

import eu.union.dev.PvPMain;
import eu.union.dev.api.Icon;
import eu.union.dev.engine.Kit;
import eu.union.dev.engine.managers.KitManager;
import eu.union.dev.utils.globals.Weapon;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.Vector;

/**
 * Created by Fentis on 14/05/2016.
 */
public class Anchor extends Kit implements Listener {

    public Anchor() {
        super("anchor", "unkit.anchor", Difficulty.LOW, Rarity.HEROIC, 2, new Icon(Material.ANVIL), Category.PROTECTED);
    }

    @Override
    public void applyKit(Player player) {
        Weapon.giveWeapon(player, Weapon.DEFAULT_SWORD);
    }

    @EventHandler
    public void ondamage(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
            final Player d = (Player) e.getDamager();
            final Player p = (Player) e.getEntity();
            KitManager km = KitManager.getManager();
            if (km.getKitAmIUsing(p, "anchor")) {
                Bukkit.getScheduler().scheduleSyncDelayedTask(PvPMain.getInstance(), new Runnable() {
                    @Override
                    public void run() {
                        p.setVelocity(new Vector());
                        d.playSound(d.getLocation(), Sound.ANVIL_LAND, 0.1F, 1.0F);
                    }
                }, 1);
            }
            if (km.getKitAmIUsing(d, "anchor")) {
                Bukkit.getScheduler().scheduleSyncDelayedTask(PvPMain.getInstance(), new Runnable() {
                    @Override
                    public void run() {
                        p.setVelocity(new Vector());
                        p.playSound(p.getLocation(), Sound.ANVIL_LAND, 0.1F, 1.0F);
                    }
                }, 1);
            }
        }
    }
}
