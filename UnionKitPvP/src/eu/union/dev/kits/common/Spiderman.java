package eu.union.dev.kits.common;

import eu.union.dev.api.Icon;
import eu.union.dev.engine.Kit;
import eu.union.dev.utils.globals.Weapon;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

/**
 * Created by Fentis on 02/06/2016.
 */
public class Spiderman extends Kit implements Listener{

    public Spiderman() {
        super("Spiderman", "unkit.spiderman", Difficulty.LOW, Rarity.COMMON, 3, new Icon(Material.WEB), Category.GRAB);
    }

    @Override
    public void applyKit(Player player) {
        Weapon.giveWeapon(player, Weapon.DEFAULT_SWORD);
    }

    @EventHandler
    public void onmove(PlayerMoveEvent e){
        Player p = e.getPlayer();
        if (p.getLocation().getBlock().getRelative(BlockFace.EAST).getType() != Material.AIR ||
                p.getLocation().getBlock().getRelative(BlockFace.WEST).getType() != Material.AIR ||
                p.getLocation().getBlock().getRelative(BlockFace.NORTH).getType() != Material.AIR ||
                p.getLocation().getBlock().getRelative(BlockFace.SOUTH).getType() != Material.AIR){
            if (p.isSneaking()){
                p.setVelocity(new Vector(0, 0.5, 0));
            }
        }
    }
}
