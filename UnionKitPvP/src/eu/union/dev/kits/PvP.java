package eu.union.dev.kits;

import eu.union.dev.engine.Kit;
import eu.union.dev.utils.Perms;
import eu.union.dev.utils.Weapon;
import org.bukkit.entity.Player;

public class PvP extends Kit {

    public PvP() {
        super("pvp", Perms.KIT_FREE.toString(), Difficulty.LOW,
                Rarity.COMMON, 0);
    }

    @Override
    public void applyKit(Player player) {
        Weapon.giveWeapon(player,Weapon.DEFAULT_SWORD);
    }
}
