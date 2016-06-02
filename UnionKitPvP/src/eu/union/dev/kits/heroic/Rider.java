package eu.union.dev.kits.heroic;

import eu.union.dev.PvPMain;
import eu.union.dev.api.Ability;
import eu.union.dev.api.Icon;
import eu.union.dev.engine.Kit;
import eu.union.dev.engine.managers.KitManager;
import eu.union.dev.utils.globals.Util;
import eu.union.dev.utils.globals.Weapon;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.TimeUnit;

/**
 * Created by Fentis on 25/05/2016.
 */
public class Rider extends Kit implements Listener {

    Ability cooldown = new Ability(1, 30, TimeUnit.SECONDS);

    public Rider() {
        super("rider", "unkit.rider", Difficulty.LOW, Rarity.HEROIC, 4, new Icon(Material.SADDLE), Category.SPAWNER);
    }

    @Override
    public void applyKit(Player player) {
        Weapon.giveWeapon(player, Weapon.DEFAULT_SWORD);
        Weapon.giveWeapon(player, Weapon.RIDER_SADDLE, 1);
    }

    @EventHandler
    public void onclick(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        KitManager km = KitManager.getManager();
        if (km.getKitAmIUsing(p, "rider") &&
                p.getItemInHand().getType() == Material.SADDLE) {
            if (cooldown.tryUse(p)) {
                final Horse h = p.getWorld().spawn(p.getLocation(), Horse.class);
                h.setAdult();
                h.setColor(Horse.Color.BROWN);
                h.setDomestication(1);
                h.getInventory().setSaddle(new ItemStack(Material.SADDLE));
                h.setPassenger(p);
                Bukkit.getScheduler().scheduleSyncDelayedTask(PvPMain.getInstance(), new Runnable() {
                    @Override
                    public void run() {
                        h.eject();
                        h.remove();
                    }
                }, 10 * 20);
            } else {
                Util.getInstance().sendCooldownMessage(p, cooldown, TimeUnit.SECONDS, true);
            }
        }
    }
}
