package eu.union.dev.kits.beast;

import eu.union.dev.api.Icon;
import eu.union.dev.engine.Kit;
import eu.union.dev.engine.managers.KitManager;
import eu.union.dev.utils.GrapplerHook;
import eu.union.dev.utils.globals.Messages;
import eu.union.dev.utils.globals.Weapon;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerLeashEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import java.util.HashMap;

/**
 * Created by Fentis on 26/06/2016.
 */
public class Grappler extends Kit implements Listener{

    public Grappler() {
        super("grappler", "unkit.grappler", Difficulty.MEDIUM, Rarity.BEAST,8, new Icon(Material.LEASH),Category.GRAB, 1000);
    }

    @Override
    public void applyKit(Player player) {
        Weapon.giveWeapon(player,Weapon.DEFAULT_SWORD);
        Weapon.giveWeapon(player,Weapon.GRAPPLER_HOOK,1);
    }

    HashMap<Player, GrapplerHook> hooks = new HashMap<>();

    @EventHandler
    public void onSlot(PlayerItemHeldEvent e) {
        if (hooks.containsKey(e.getPlayer()))
        {
            ((GrapplerHook)hooks.get(e.getPlayer())).remove();
            hooks.remove(e.getPlayer());
        }
    }
    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        KitManager km = KitManager.getManager();
        if (hooks.containsKey(e.getPlayer()) &&
                e.getPlayer().getItemInHand().getType() != Material.LEASH &&
                km.getKitAmIUsing(e.getPlayer(),"grappler"))
        {
            ((GrapplerHook)hooks.get(e.getPlayer())).remove();
            hooks.remove(e.getPlayer());
        }
    }
    @EventHandler
    public void onLeash(PlayerLeashEntityEvent e)
    {
        Player p = e.getPlayer();
        KitManager km = KitManager.getManager();
        if (p.getItemInHand().getType() == Material.LEASH && km.getKitAmIUsing(p,"grappler"))
        {
            e.setCancelled(true);
            e.getPlayer().updateInventory();
            e.setCancelled(true);
            if (!hooks.containsKey(p)) {
                return;
            }
            if (!((GrapplerHook)hooks.get(p)).isHooked()) {
                return;
            }
            double d = ((GrapplerHook)hooks.get(p)).getBukkitEntity().getLocation().distance(p.getLocation());
            double t = d;
            double v_x = (1.0D + 0.07000000000000001D * t) * (((GrapplerHook)hooks.get(p)).getBukkitEntity().getLocation().getX() - p.getLocation().getX()) / t;
            double v_y = (1.0D + 0.03D * t) * (((GrapplerHook)hooks.get(p)).getBukkitEntity().getLocation().getY() - p.getLocation().getY()) / t;
            double v_z = (1.0D + 0.07000000000000001D * t) * (((GrapplerHook)hooks.get(p)).getBukkitEntity().getLocation().getZ() - p.getLocation().getZ()) / t;

            Vector v = p.getVelocity();
            v.setX(v_x);
            v.setY(v_y);
            v.setZ(v_z);
            p.setVelocity(v);
        }
    }

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        KitManager km = KitManager.getManager();
        if (p.getItemInHand().getType() == Material.LEASH && km.getKitAmIUsing(p,"grappler"))
        {
            e.setCancelled(true);
            if ((e.getAction() == Action.LEFT_CLICK_AIR) || (e.getAction() == Action.LEFT_CLICK_BLOCK))
            {
                if (hooks.containsKey(p)) {
                    ((GrapplerHook)hooks.get(p)).remove();
                }
                GrapplerHook nmsHook = new GrapplerHook(p.getWorld(), ((CraftPlayer)p).getHandle());
                nmsHook.spawn(p.getEyeLocation().add(p.getLocation().getDirection().getX(), p.getLocation().getDirection().getY(), p.getLocation().getDirection().getZ()));
                nmsHook.move(p.getLocation().getDirection().getX() * 5.0D, p.getLocation().getDirection().getY() * 5.0D, p.getLocation().getDirection().getZ() * 5.0D);
                hooks.put(p, nmsHook);
            }
            else
            {
                if (!hooks.containsKey(p)) {
                    return;
                }
                if (!((GrapplerHook)hooks.get(p)).isHooked())
                {
                    p.sendMessage(Messages.PREFIX+" §aYou do not hold anything");
                    return;
                }
                double d = ((GrapplerHook)hooks.get(p)).getBukkitEntity().getLocation().distance(p.getLocation());
                double t = d;
                double v_x = (1.0D + 0.07000000000000001D * t) * (((GrapplerHook)hooks.get(p)).getBukkitEntity().getLocation().getX() - p.getLocation().getX()) / t;
                double v_y = (1.0D + 0.03D * t) * (((GrapplerHook)hooks.get(p)).getBukkitEntity().getLocation().getY() - p.getLocation().getY()) / t;
                double v_z = (1.0D + 0.07000000000000001D * t) * (((GrapplerHook)hooks.get(p)).getBukkitEntity().getLocation().getZ() - p.getLocation().getZ()) / t;

                Vector v = p.getVelocity();
                v.setX(v_x);
                v.setY(v_y);
                v.setZ(v_z);
                p.setVelocity(v);
            }
        }
    }
}
