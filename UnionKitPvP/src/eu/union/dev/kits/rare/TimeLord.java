package eu.union.dev.kits.rare;

import eu.union.dev.PvPMain;
import eu.union.dev.api.Ability;
import eu.union.dev.engine.Kit;
import eu.union.dev.engine.managers.KitManager;
import eu.union.dev.utils.Util;
import eu.union.dev.utils.Weapon;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Created by Fentis on 16/05/2016.
 */
public class TimeLord extends Kit implements Listener{

    public TimeLord(){super("timelord","unkit.timelord",Difficulty.LOW,Rarity.RARE,0);}

    @Override
    public void applyKit(Player player) {
        Weapon.giveWeapon(player, Weapon.DEFAULT_SWORD);
        Weapon.giveWeapon(player, Weapon.TIMELORD_CLOCK);
    }

    Ability cooldown = new Ability(1, 15, TimeUnit.SECONDS);
    ArrayList<String> congelados = new ArrayList<>();
    @EventHandler
    public void onclick(PlayerInteractEvent e){
        Player p = e.getPlayer();
        KitManager km = KitManager.getManager();
        if (p.getItemInHand().getType() == Material.WATCH &&
                km.getKitAmIUsing(p, "timelord")){
            if (cooldown.tryUse(p)){
                for (Entity en : p.getNearbyEntities(5.0, 5.0, 5.0)){
                    if (en instanceof Player){
                        final Player p2 = (Player)en;
                        congelados.add(p2.getName());
                        p2.getWorld().playEffect(p2.getLocation(), Effect.SNOWBALL_BREAK, 10);
                        p2.getWorld().playSound(p2.getLocation(), Sound.WITHER_SHOOT, 10.0F, 1.0F);
                        Bukkit.getScheduler().scheduleSyncDelayedTask(PvPMain.getInstance(), new Runnable() {
                            @Override
                            public void run() {
                                congelados.remove(p2.getName());
                            }
                        }, 5*20);
                    }
                }
            }else{
                Util.getInstance().sendCooldownMessage(p,cooldown,TimeUnit.SECONDS,true);
            }
        }
    }
    @EventHandler
    public void onmove(PlayerMoveEvent e){
        Player p = e.getPlayer();
        if (congelados.contains(p.getName())){
            e.setTo(p.getLocation());
        }
    }
}
