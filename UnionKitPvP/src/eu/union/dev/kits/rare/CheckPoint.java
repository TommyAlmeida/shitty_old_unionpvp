package eu.union.dev.kits.rare;

import eu.union.dev.api.Ability;
import eu.union.dev.api.Icon;
import eu.union.dev.engine.Kit;
import eu.union.dev.engine.managers.KitManager;
import eu.union.dev.utils.Util;
import eu.union.dev.utils.Weapon;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by Fentis on 19/05/2016.
 */
public class CheckPoint extends Kit implements Listener{

    public CheckPoint(){ super("checkpoint", "unkit.checkpoint", Difficulty.LOW, Rarity.RARE, 0, new Icon(Material.NETHER_FENCE), Category.TELEPORT); }

    @Override
    public void applyKit(Player player) {
        Weapon.giveWeapon(player, Weapon.DEFAULT_SWORD);
        Weapon.giveWeapon(player, Weapon.CHECKPOINT_FENCE,1);
        Weapon.giveWeapon(player, Weapon.CHECKPOINT_POT,2);
    }

    Ability cooldown = new Ability(1, 15, TimeUnit.SECONDS);
    HashMap<Player, Location> locs = new HashMap<>();
    @SuppressWarnings("deprecation")
    @EventHandler
    public void onclick(PlayerInteractEvent e){
        Player p = e.getPlayer();
        KitManager km = KitManager.getManager();
        if (km.getKitAmIUsing(p, "checkpoint")){
            if (p.getItemInHand().getType() == Material.NETHER_FENCE){
                locs.put(p, p.getLocation());
                p.sendMessage("§aPosition saved!");
                e.setCancelled(true);
                p.updateInventory();
            }
            if (p.getItemInHand().getType() == Material.FLOWER_POT_ITEM){
                e.setCancelled(true);
                p.updateInventory();
                if (locs.containsKey(p)){
                    if (p.getLocation().distance(locs.get(p)) <=50){
                        if (cooldown.tryUse(p)){
                            p.teleport(locs.get(p));
                        }else{
                            Util.getInstance().sendCooldownMessage(p,cooldown,TimeUnit.SECONDS,true);
                        }
                    }else{
                        p.sendMessage("§cTeleport only 50 blocks!");
                    }
                }
            }
        }
    }
}
