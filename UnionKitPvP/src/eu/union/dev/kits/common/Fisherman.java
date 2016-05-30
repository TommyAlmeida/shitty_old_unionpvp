package eu.union.dev.kits.common;

import eu.union.dev.api.Icon;
import eu.union.dev.engine.Kit;
import eu.union.dev.engine.managers.KitManager;
import eu.union.dev.utils.globals.Weapon;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;

/**
 * Created by Fentis on 14/05/2016.
 */
public class Fisherman extends Kit implements Listener {

    public Fisherman() {
        super("fisherman", "unkit.fisherman", Difficulty.LOW, Rarity.COMMON, 0, new Icon(Material.FISHING_ROD),
                Category.CATCHER);
    }

    @Override
    public void applyKit(Player player) {
        Weapon.giveWeapon(player, Weapon.DEFAULT_SWORD);
        Weapon.giveWeapon(player, Weapon.FISHERMAN_ROD, 1);
    }

    @EventHandler
    public void onfishing(PlayerFishEvent e) {
        Player p = e.getPlayer();
        KitManager km = KitManager.getManager();
        if (e.getCaught() instanceof Player) {
            Player p2 = (Player) e.getCaught();
            if (km.getKitAmIUsing(p, "fisherman")) {
                p2.teleport(p.getLocation());
            }
        }
    }
}
