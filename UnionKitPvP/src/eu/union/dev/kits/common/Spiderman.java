package eu.union.dev.kits.common;

import eu.union.dev.PvPMain;
import eu.union.dev.api.Icon;
import eu.union.dev.engine.Kit;
import eu.union.dev.engine.managers.KitManager;
import eu.union.dev.utils.globals.Weapon;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

/**
 * Created by Fentis on 02/06/2016.
 */
public class Spiderman extends Kit implements Listener{

    public Spiderman() {
        super("spiderman", "unkit.spiderman", Difficulty.LOW, Rarity.COMMON, 3, new Icon(Material.WEB), Category.GRAB, 1000L);
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
            KitManager km = KitManager.getManager();
            if (p.isSneaking() && km.getKitAmIUsing(p,"spiderman")){
                p.setVelocity(new Vector(0, 0.5, 0));
            }
        }
    }
    @EventHandler
    public void canceldamagefall(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            KitManager km = KitManager.getManager();
            if (km.getKitAmIUsing((Player) e.getEntity(), "spiderman") &&
                    e.getCause() == EntityDamageEvent.DamageCause.FALL) {
                if (e.getDamage() > 4.0D) {
                    e.setDamage(0.0D);
                    Block b = e.getEntity().getLocation().getBlock();
                    ((Player)e.getEntity()).sendBlockChange(b.getLocation(),Material.WEB,(byte)0);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(PvPMain.getInstance(), new Runnable() {
                        @Override
                        public void run() {
                            ((Player)e.getEntity()).sendBlockChange(b.getLocation(),b.getType(),b.getData());
                        }
                    },3*20);
                }
            }
        }
    }
}
