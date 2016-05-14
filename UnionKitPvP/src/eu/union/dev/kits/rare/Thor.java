package eu.union.dev.kits.rare;

import eu.union.dev.PvPMain;
import eu.union.dev.engine.Kit;
import eu.union.dev.engine.managers.KitManager;
import eu.union.dev.utils.Weapon;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;

/**
 * Created by Fentis on 14/05/2016.
 */
public class Thor extends Kit implements Listener{

    public Thor(){ super("thor", "unkit.thor", Difficulty.LOW, Rarity.RARE, 0); }

    @Override
    public void applyKit(Player player) {
        Weapon.giveWeapon(player, Weapon.DEFAULT_SWORD);
        Weapon.giveWeapon(player, Weapon.THOR_HAMMER);
    }

    ArrayList<String> cd = new ArrayList<>();
    @EventHandler
    public void onclick(PlayerInteractEvent e){
        final Player p = e.getPlayer();
        KitManager km = KitManager.getManager();
        if (km.getKitAmIUsing(p, "thor") &&
                p.getItemInHand().getType() == Material.GOLD_AXE &&
                e.getAction() == Action.RIGHT_CLICK_BLOCK ){
            if (!cd.contains(p.getName())){
                cd.add(p.getName());
                int y = p.getWorld().getHighestBlockYAt(e.getClickedBlock().getLocation());
                Location loc = e.getClickedBlock().getLocation();
                loc.setY(y);
                Block b = loc.getBlock();
                b.getWorld().strikeLightning(b.getLocation());
                Bukkit.getScheduler().scheduleSyncDelayedTask(PvPMain.getInstance(), new Runnable() {
                    @Override
                    public void run() {
                        p.sendMessage("§aThe cooldown is over!");
                        cd.remove(p.getName());
                    }
                }, 5*20);
            }else{
                p.sendMessage("§cYou are in cooldown!");
            }
        }
    }
    @EventHandler
    public void ondamage(EntityDamageEvent e) {
        KitManager km = KitManager.getManager();
        if (e.getCause() == EntityDamageEvent.DamageCause.LIGHTNING &&
                e.getEntity() instanceof Player){
            if (km.getKitAmIUsing((Player)e.getEntity(), "thor")){
                e.setCancelled(true);
            }
        }
    }
}
