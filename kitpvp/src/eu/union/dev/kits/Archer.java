package eu.union.dev.kits;

import eu.union.dev.engine.Kit;
import eu.union.dev.utils.Lists;
import eu.union.dev.utils.Util;
import eu.union.dev.utils.Weapon;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

/**
 * Created by Samuel on 30/04/2016.
 */

public class Archer extends Kit {

    public Archer() {
        super("Archer", "unkit.archer");
    }

    @Override
    public void applyKit(Player player) {
        Lists.kit.add(player);

        Weapon.giveWeapon(player,Weapon.DEFAULT_SWORD);
        Weapon.giveWeapon(player,Weapon.DEFAULT_BOW, Enchantment.ARROW_INFINITE, 1);
    }

}
