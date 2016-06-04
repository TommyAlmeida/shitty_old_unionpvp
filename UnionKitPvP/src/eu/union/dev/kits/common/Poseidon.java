package eu.union.dev.kits.common;

import eu.union.dev.api.Icon;
import eu.union.dev.engine.Kit;
import eu.union.dev.engine.managers.KitManager;
import eu.union.dev.utils.globals.Weapon;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Poseidon extends Kit implements Listener {

    public Poseidon() {
        super("poseidon", "unkit.poseidon", Difficulty.LOW,
                Rarity.COMMON, 6, new Icon(Material.RAW_FISH), Category.POTION, 1000L);
    }

    @Override
    public void applyKit(Player player) {
        Weapon.giveWeapon(player, Weapon.DEFAULT_SWORD);
    }

    @EventHandler
    public void onmove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        KitManager km = KitManager.getManager();
        if (km.getKitAmIUsing(p, "poseidon")) {
            if (p.getLocation().getBlock().getType() == Material.WATER ||
                    p.getLocation().getBlock().getType() == Material.STATIONARY_WATER) {
                p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 3 * 20, 0));
                p.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 3 * 20, 0));
            }
        }
    }
}
