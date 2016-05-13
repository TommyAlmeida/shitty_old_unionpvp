package eu.union.dev.kits.common;

import eu.union.dev.engine.Kit;
import eu.union.dev.utils.Perms;
import eu.union.dev.utils.Weapon;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

public class Archer extends Kit {

    public Archer() {
        super("archer", Perms.KIT_FREE.toString(), Difficulty.PRO,
                Rarity.HEROIC, 0, "§7teste", "§7teste"
        );
    }

    @Override
    public void applyKit(Player player) {
        Weapon.giveWeapon(player,Weapon.DEFAULT_BOW);
    }

}
