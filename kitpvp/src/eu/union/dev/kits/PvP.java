package eu.union.dev.kits;

import eu.union.dev.engine.Kit;
import eu.union.dev.utils.Lists;
import eu.union.dev.utils.Weapon;
import org.bukkit.entity.Player;

public class PvP extends Kit {

    public PvP() {
        super("pvp", "");
    }

    @Override
    public void applyKit(Player player) {
        Lists.kit.add(player);

        Weapon.giveWeapon(player,Weapon.DEFAULT_SWORD);
    }
}
