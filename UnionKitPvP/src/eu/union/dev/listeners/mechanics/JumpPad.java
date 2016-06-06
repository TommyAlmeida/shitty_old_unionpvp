package eu.union.dev.listeners.mechanics;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import java.util.ArrayList;

public class JumpPad implements Listener {

    private ArrayList<Player> players = new ArrayList<Player>();

    @EventHandler
    public void onJump(PlayerMoveEvent e) {
        Player p = e.getPlayer();

        if (e.getTo().getBlock().getType() == Material.STONE_PLATE ||
                e.getTo().getBlock().getType() == Material.CARPET) {
            Vector v = p.getLocation().getDirection().multiply(1).setY(1.0D);
            p.setVelocity(v);
            p.playSound(p.getLocation(), Sound.ENDERDRAGON_WINGS, 3.0F, 2.0F);
            p.getWorld().playEffect(p.getLocation(), Effect.EXPLOSION_LARGE, 4);
            players.add(p);
        }
        if (e.getTo().getBlock().getRelative(BlockFace.DOWN).getType() == Material.SPONGE) {
            Vector v = new Vector(0.0D, 3D, 0.0D);
            p.setVelocity(v);
            p.playSound(p.getLocation(), Sound.ENDERDRAGON_WINGS, 3.0F, 2.0F);
            p.getWorld().playEffect(p.getLocation(), Effect.EXPLOSION_LARGE, 4);
            players.add(p);
        }
    }

    @EventHandler
    public void onFallDamage(EntityDamageEvent e) {
        Player p = (Player) e.getEntity();
        if (e.getEntity() instanceof Player && e.getCause() == EntityDamageEvent.DamageCause.FALL) {
            if (players.contains(p)) {
                if(!e.isCancelled())
                    e.setCancelled(true);
                players.remove(e.getEntity());
                players.remove(e.getEntity());
                players.remove(e.getEntity());
                players.remove(e.getEntity());
                players.remove(e.getEntity());
            }
        }
    }
}
