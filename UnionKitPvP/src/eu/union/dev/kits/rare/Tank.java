package eu.union.dev.kits.rare;

import eu.union.dev.api.Icon;
import eu.union.dev.engine.Kit;
import eu.union.dev.engine.managers.KitManager;
import eu.union.dev.utils.globals.Util;
import eu.union.dev.utils.globals.Weapon;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

/**
 * Created by Fentis on 26/05/2016.
 */
public class Tank extends Kit implements Listener {

    public Tank() {
        super("tank", "unkit.tank", Difficulty.LOW, Rarity.RARE, 0, new Icon(Material.IRON_BLOCK), Category.SWORDS);
    }

    @Override
    public void applyKit(Player player) {
        Weapon.giveWeapon(player, Weapon.DEFAULT_SWORD);
    }

    @EventHandler
    public void ondeath(PlayerDeathEvent e) {
        Player p = e.getEntity();
        KitManager km = KitManager.getManager();
        if (km.getKitAmIUsing(p, "tank")) {
            for (Entity en : p.getNearbyEntities(5, 5, 5)) {
                if (en instanceof Player) {
                    if (Util.getInstance().inPvP(((Player) en))) {
                        ((Player) en).damage(10.0, p);
                    }
                }
            }
            p.getWorld().playEffect(p.getLocation(), Effect.EXPLOSION_LARGE, 10);
            p.getWorld().playSound(p.getLocation(), Sound.EXPLODE, 1, 1);
        }
    }
}
