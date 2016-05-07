package eu.union.dev.kits.common;

import eu.union.dev.engine.Kit;
import eu.union.dev.utils.Perms;
import eu.union.dev.utils.Weapon;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class Grandpa extends Kit {

    public Grandpa() {
        super("grandpa", Perms.KIT_FREE.toString(), Difficulty.LOW,
                Rarity.COMMON, 0
        );
    }

    @Override
    public void applyKit(Player player) {
        Weapon.giveWeapon(player,Weapon.DEFAULT_SWORD);
        Weapon.giveWeapon(player,Weapon.GRANDPA_STICK, Enchantment.KNOCKBACK, 1);
    }
}
