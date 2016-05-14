package eu.union.dev.kits.rare;

import eu.union.dev.PvPMain;
import eu.union.dev.engine.Kit;
import eu.union.dev.engine.managers.KitManager;
import eu.union.dev.utils.Weapon;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;

/**
 * Created by Fentis on 14/05/2016.
 */
public class Switcher extends Kit implements Listener{

    public Switcher(){ super("switcher", "unkit.switcher", Difficulty.MEDIUM, Rarity.RARE, 0); }
    @Override
    public void applyKit(Player player) {
        Weapon.giveWeapon(player, Weapon.DEFAULT_SWORD);
        Weapon.giveWeapon(player, Weapon.SWITCHER_SNOW_BALL);
    }

    ArrayList<String> cd = new ArrayList<>();
    @SuppressWarnings("deprecation")
    @EventHandler
    public void onclick(PlayerInteractEvent e){
        final Player p = e.getPlayer();
        KitManager km = KitManager.getManager();
        if (p.getItemInHand().getType() == Material.SNOW_BALL){
            if (km.getKitAmIUsing(p, "switcher")){
                e.setCancelled(true);
                p.updateInventory();
                if (e.getAction() == Action.RIGHT_CLICK_AIR){
                    if (!cd.contains(p.getName())){
                        p.launchProjectile(Snowball.class);
                        cd.add(p.getName());
                        Bukkit.getScheduler().scheduleSyncDelayedTask(PvPMain.getInstance(), new Runnable() {
                            @Override
                            public void run() {
                                cd.remove(p.getName());
                            }
                        }, 3*20);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e)
    {
        if ((e.getEntity() instanceof Player))
        {
            Player ph = (Player)e.getEntity();
            KitManager km = KitManager.getManager();
            if ((e.getDamager() instanceof Snowball))
            {
                Snowball snowball = (Snowball)e.getDamager();
                if ((snowball.getShooter() instanceof Player))
                {
                    Player ps = (Player)snowball.getShooter();
                    if (km.getKitAmIUsing(ps, "switcher"))
                    {
                        Location psloc = ps.getLocation();
                        Location phloc = ph.getLocation();
                        ps.teleport(phloc);
                        ph.teleport(psloc);
                    }
                }
            }
        }
    }
}
