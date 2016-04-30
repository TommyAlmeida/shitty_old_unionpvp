package eu.union.dev.kits;

import eu.union.dev.engine.Kit;
import eu.union.dev.utils.Lists;
import eu.union.dev.utils.Weapon;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

public class Grandpa extends Kit {

    public Grandpa() {
        super("Grandpa", "unkit.grandpa");
    }

    @Override
    public void applyKit(Player player) {
        Lists.kit.add(player);

        Weapon.giveWeapon(player,Weapon.GRANDPA_STICK, Enchantment.KNOCKBACK, 2);
    }
}
