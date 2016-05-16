package eu.union.dev.kits.heroic;

import eu.union.dev.engine.Kit;
import eu.union.dev.engine.managers.KitManager;
import eu.union.dev.utils.Weapon;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import java.util.ArrayList;

/**
 * Created by Fentis on 14/05/2016.
 */
public class Kangaroo extends Kit implements Listener{

    public Kangaroo(){ super("kangaroo", "unkit.kangaroo", Difficulty.MEDIUM, Rarity.HEROIC, 0); }

    @Override
    public void applyKit(Player player) {
        Weapon.giveWeapon(player, Weapon.DEFAULT_SWORD);
        Weapon.giveWeapon(player, Weapon.KANGAROO_FIREWORK, 1);
    }

    ArrayList<String> cd = new ArrayList<>();
    @EventHandler
    public void onclick(PlayerInteractEvent e)
    {
        Player p = e.getPlayer();
        KitManager km = KitManager.getManager();
        if (p.getItemInHand().getType() == Material.FIREWORK)
        {
            if (km.getKitAmIUsing(p, "kangaroo"))
            {
                e.setCancelled(true);
                Vector v = p.getEyeLocation().getDirection();
                if (!cd.contains(p.getName()))
                {
                    cd.add(p.getName());
                    if (!p.isSneaking())
                    {
                        p.setFallDistance(-1.0F);
                        v.multiply(0.5F);
                        v.setY(1.0D);
                        p.setVelocity(v);
                    }
                    else
                    {
                        p.setFallDistance(-1.0F);
                        v.multiply(1.5F);
                        v.setY(0.5D);
                        p.setVelocity(v);
                    }
                }
            }
        }
    }

    @EventHandler
    public void removecd(PlayerMoveEvent event)
    {
        Player p = event.getPlayer();
        if (cd.contains(p.getName()))
        {
            Block b = p.getLocation().getBlock();
            if ((b.getType() != Material.AIR) ||
                    (b.getRelative(BlockFace.DOWN).getType() != Material.AIR)){
                cd.remove(p.getName());
            }
        }
    }

    @EventHandler
    public void canceldamagefall(EntityDamageEvent e)
    {
        if (e.getEntity() instanceof Player) {
            KitManager km = KitManager.getManager();
            if (km.getKitAmIUsing((Player)e.getEntity(), "kangaroo") &&
                    e.getCause() == EntityDamageEvent.DamageCause.FALL){
                if (e.getDamage() > 7.0D){
                    e.setDamage(7.0D);
                }
            }
        }
    }
}
