package eu.union.dev.kits.common;

import eu.union.dev.engine.Kit;
import eu.union.dev.utils.Perms;
import org.bukkit.entity.Player;

public class Poseidon extends Kit{

    public Poseidon() {
        super("poseidon", Perms.KIT_FREE.toString(), Difficulty.LOW,
                Rarity.COMMON, 0);
    }

    @Override
    public void applyKit(Player player) {

    }
}
