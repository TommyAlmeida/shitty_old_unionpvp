package eu.union.dev.kits;

import eu.union.dev.engine.Kit;
import eu.union.dev.utils.Perms;
import eu.union.dev.utils.Weapon;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

public class Archer extends Kit {

    public Archer() {
        super("archer", Perms.KIT_FREE.toString(), Difficulty.LOW,
                "You always wanted to be an marksman?",
                "this is your oportunity."
        );
    }

    @Override
    public void applyKit(Player player) {
        Weapon.giveWeapon(player,Weapon.DEFAULT_SWORD);
        Weapon.giveWeapon(player,Weapon.DEFAULT_BOW, Enchantment.ARROW_INFINITE, 1);
    }

}
