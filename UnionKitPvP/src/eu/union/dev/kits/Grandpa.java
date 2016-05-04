package eu.union.dev.kits;

import eu.union.dev.engine.Kit;
import eu.union.dev.utils.Perms;
import eu.union.dev.utils.Weapon;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

public class Grandpa extends Kit {

    public Grandpa() {
        super("grandpa", Perms.KIT_FREE.toString(), Difficulty.LOW,
                Rarity.COMMON, 0,
                "Do you wanna become a angry grandpa? \n",
                "grab your cane and lets kick some buts."
        );
    }

    @Override
    public void applyKit(Player player) {
        Weapon.giveWeapon(player,Weapon.GRANDPA_STICK, Enchantment.KNOCKBACK, 2);
    }
}
