package eu.union.dev.kits.rare;

import eu.union.dev.api.Ability;
import eu.union.dev.api.Icon;
import eu.union.dev.engine.Kit;
import eu.union.dev.engine.managers.KitManager;
import eu.union.dev.utils.globals.Weapon;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.concurrent.TimeUnit;

/**
 * Created by Fentis on 14/05/2016.
 */
public class Switcher extends Kit implements Listener {

    Ability cooldown = new Ability(1, 3, TimeUnit.SECONDS);

    public Switcher() {
        super("switcher", "unkit.switcher", Difficulty.MEDIUM, Rarity.RARE, 1, new Icon(Material.SNOW_BALL), Category.TELEPORT, 1000L);
    }

    @Override
    public void applyKit(Player player) {
        Weapon.giveWeapon(player, Weapon.DEFAULT_SWORD);
        Weapon.giveWeapon(player, Weapon.SWITCHER_SNOW_BALL, 1);
    }

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onclick(PlayerInteractEvent e) {
        final Player p = e.getPlayer();
        KitManager km = KitManager.getManager();
        if (p.getItemInHand().getType() == Material.SNOW_BALL) {
            if (km.getKitAmIUsing(p, "switcher")) {
                e.setCancelled(true);
                p.updateInventory();
                if (e.getAction() == Action.RIGHT_CLICK_AIR) {
                    if (cooldown.tryUse(p)) {
                        p.launchProjectile(Snowball.class);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
        if ((e.getEntity() instanceof Player)) {
            Player ph = (Player) e.getEntity();
            KitManager km = KitManager.getManager();
            if ((e.getDamager() instanceof Snowball)) {
                Snowball snowball = (Snowball) e.getDamager();
                if ((snowball.getShooter() instanceof Player)) {
                    Player ps = (Player) snowball.getShooter();
                    if (km.getKitAmIUsing(ps, "switcher")) {
                        Location psloc = ps.getLocation();
                        Location phloc = ph.getLocation();
                        ps.teleport(phloc);
                        ph.teleport(psloc);
                    }
                }
            }
        }
    }
}
