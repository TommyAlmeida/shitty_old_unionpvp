package eu.union.dev.kits.common;

import eu.union.dev.api.Ability;
import eu.union.dev.api.Icon;
import eu.union.dev.engine.Kit;
import eu.union.dev.engine.managers.KitManager;
import eu.union.dev.utils.globals.Perms;
import eu.union.dev.utils.globals.Util;
import eu.union.dev.utils.globals.Weapon;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.concurrent.TimeUnit;

/**
 * Created by Tomas on 19-06-2016.
 */
public class Pyro extends Kit implements Listener {

    public Pyro() {
        super("pyro", "unkit.pyro", Difficulty.LOW,
                Rarity.COMMON, 0, new Icon(Material.FIREBALL), Category.SOCIAL, 200L);
    }

    @Override
    public void applyKit(Player player) {
        Weapon.giveWeapon(player, Weapon.DEFAULT_SWORD);
        Weapon.giveWeapon(player, Weapon.PYRO_FIREBALL, 1);
    }

    Ability cooldown = new Ability(1,30, TimeUnit.SECONDS);

    @EventHandler
    public void onClick(PlayerInteractEvent e){
        Player p = e.getPlayer();
        KitManager km = KitManager.getManager();
        if (p.getItemInHand().getType() == Material.FIREBALL && km.getKitAmIUsing(p, "pyro")){
            e.setCancelled(true);
            p.updateInventory();
            if (cooldown.tryUse(p)){
                Fireball ball = (Fireball)p.launchProjectile(Fireball.class);
                ball.setIsIncendiary(true);
                ball.setYield(ball.getYield() * 1.5F);
            }else{
                Util.getInstance().sendCooldownMessage(p, cooldown, TimeUnit.SECONDS, true);
            }
        }
    }
}

