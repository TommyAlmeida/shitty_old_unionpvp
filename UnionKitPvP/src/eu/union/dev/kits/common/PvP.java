package eu.union.dev.kits.common;

import eu.union.dev.api.Icon;
import eu.union.dev.engine.Kit;
import eu.union.dev.utils.globals.Perms;
import eu.union.dev.utils.globals.Weapon;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

public class PvP extends Kit {

    public PvP() {
        super("pvp", Perms.KIT_FREE.toString(), Difficulty.LOW,
                Rarity.COMMON, 0, new Icon(Material.WOOD_SWORD), Category.SWORDS);
    }

    @Override
    public void applyKit(Player player) {
        Weapon.giveWeapon(player, Weapon.DEFAULT_SWORD, Enchantment.DAMAGE_ALL,1);
    }
}
