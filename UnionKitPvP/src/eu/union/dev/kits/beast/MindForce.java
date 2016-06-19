package eu.union.dev.kits.beast;

import eu.union.dev.api.Ability;
import eu.union.dev.api.Icon;
import eu.union.dev.engine.Kit;
import eu.union.dev.utils.globals.Weapon;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.concurrent.TimeUnit;

public class MindForce extends Kit implements Listener {

    Ability cooldown = new Ability(1, 15, TimeUnit.SECONDS);

    public MindForce() {
        super("mindforce", "unkit.mindforce", Difficulty.MEDIUM, Rarity.BEAST, 8, new Icon(Material.SKULL_ITEM), Category.SOCIAL, 900L);
    }

    @Override
    public void applyKit(Player player) {
        Weapon.giveWeapon(player, Weapon.DEFAULT_SWORD);
        Weapon.giveWeapon(player, Weapon.DEFAULT_SWORD);
    }
}
