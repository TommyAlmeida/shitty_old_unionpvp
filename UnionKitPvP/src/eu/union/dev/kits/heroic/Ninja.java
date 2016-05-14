package eu.union.dev.kits.heroic;

import eu.union.dev.PvPMain;
import eu.union.dev.engine.Kit;
import eu.union.dev.engine.managers.KitManager;
import eu.union.dev.utils.Weapon;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Fentis on 14/05/2016.
 */
public class Ninja extends Kit implements Listener{

    public Ninja(){ super("ninja", "unkit.ninja", Difficulty.LOW, Rarity.HEROIC, 0); }

    @Override
    public void applyKit(Player player) {
        Weapon.giveWeapon(player, Weapon.DEFAULT_SWORD);
    }

    HashMap<Player, Player> ninja = new HashMap<>();
    ArrayList<String> cd = new ArrayList<>();

    @EventHandler
    public void hitplayer(EntityDamageByEntityEvent e)
    {
        if (e.getDamager() instanceof Player && e.getEntity() instanceof Player){
            final Player p = (Player)e.getDamager();
            Player d = (Player)e.getEntity();
            KitManager km = KitManager.getManager();
            if (km.getKitAmIUsing(p, "ninja")){
                ninja.put(p, d);
            }
        }
    }
    @EventHandler
    public void onsneak(PlayerToggleSneakEvent e){
        final Player p = e.getPlayer();
        KitManager km = KitManager.getManager();
        if (km.getKitAmIUsing(p, "ninja") &&
                ninja.containsKey(p)){
            Player t = ninja.get(p);
            if (t!=null && !t.isDead()){
                if (!cd.contains(p.getName())){
                    if (p.getLocation().distance(t.getLocation()) <30.0){
                        cd.add(p.getName());
                        p.teleport(t);
                        Bukkit.getScheduler().scheduleSyncDelayedTask(PvPMain.getInstance(), new Runnable() {
                            @Override
                            public void run() {
                                cd.remove(p.getName());
                                p.sendMessage("§aThe cooldown is over!");
                            }
                        }, 15*20);
                    }else{
                        p.sendMessage("§cThe player is too far!");
                    }
                }else{
                    p.sendMessage("§cYou are in cooldown!");
                }
            }
        }
    }
}
