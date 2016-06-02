package eu.union.dev.kits.rare;

import eu.union.dev.api.Icon;
import eu.union.dev.engine.Kit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class Pikachu extends Kit implements Listener{

    public Pikachu() {
        super("Pikachu", "unsoon", Difficulty.LOW, Rarity.RARE, 0,new Icon(Material.BEACON), Category.LONG_DISTANCE);
    }

    @Override
    public void applyKit(Player player) {
    }
}
