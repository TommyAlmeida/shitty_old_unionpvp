package eu.union.dev.kits.heroic;

import eu.union.dev.api.Ability;
import eu.union.dev.api.Icon;
import eu.union.dev.engine.Kit;
import eu.union.dev.engine.managers.KitManager;
import eu.union.dev.utils.globals.Util;
import eu.union.dev.utils.globals.Weapon;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by Fentis on 14/05/2016.
 */
public class Ninja extends Kit implements Listener{

    public Ninja(){ super("ninja", "unkit.ninja", Difficulty.LOW, Rarity.HEROIC, 0, new Icon(Material.COAL_BLOCK), Category.TELEPORT); }

    @Override
    public void applyKit(Player player) {
        Weapon.giveWeapon(player, Weapon.DEFAULT_SWORD);
    }

    HashMap<Player, Player> ninja = new HashMap<>();
    Ability cooldown = new Ability(1,15, TimeUnit.SECONDS);

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
                if (cooldown.tryUse(p)){
                    if (p.getLocation().distance(t.getLocation()) <30.0){
                        p.teleport(t);
                    }else{
                        p.sendMessage("§cThe player is too far!");
                    }
                }else{
                    Util.getInstance().sendCooldownMessage(p,cooldown,TimeUnit.SECONDS,true);
                }
            }
        }
    }
}
