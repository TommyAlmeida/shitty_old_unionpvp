package eu.union.dev.kits.common;

import eu.union.dev.api.Ability;
import eu.union.dev.api.Icon;
import eu.union.dev.engine.Kit;
import eu.union.dev.engine.managers.KitManager;
import eu.union.dev.utils.globals.Util;
import eu.union.dev.utils.globals.Weapon;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.concurrent.TimeUnit;

/**
 * Created by Fentis on 23/05/2016.
 */
public class Spectre extends Kit implements Listener {

    Ability cooldown = new Ability(1, 20, TimeUnit.SECONDS);

    public Spectre() {
        super("spectre", "unkit.spectre", Difficulty.LOW, Rarity.COMMON, 0, new Icon(Material.SUGAR),
                Category.POTION);
    }

    @Override
    public void applyKit(Player player) {
        Weapon.giveWeapon(player, Weapon.DEFAULT_SWORD);
        Weapon.giveWeapon(player, Weapon.SPECTRE_SUGAR, 1);
    }

    @EventHandler
    public void onclick(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        KitManager km = KitManager.getManager();
        if (p.getItemInHand().getType() == Material.SUGAR && km.getKitAmIUsing(p, "spectre")) {
            if (cooldown.tryUse(p)) {
                p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 5 * 20, 0), true);
                p.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 5 * 20, 0), true);
                p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 5 * 20, 0), true);
            } else {
                Util.getInstance().sendCooldownMessage(p, cooldown, TimeUnit.SECONDS, true);
            }
        }
    }
}
