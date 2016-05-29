package eu.union.dev.kits.heroic;

import eu.union.dev.PvPMain;
import eu.union.dev.api.Icon;
import eu.union.dev.engine.Kit;
import eu.union.dev.engine.handlers.ShockwaveHandler;
import eu.union.dev.engine.managers.KitManager;
import eu.union.dev.utils.Weapon;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class Stomper extends Kit implements Listener {

    public Stomper() {
        super("stomper", "unkit.stomper", Difficulty.MEDIUM, Rarity.HEROIC, 0, new Icon(Material.IRON_BOOTS), Category.JUMPER);
    }

    @Override
    public void applyKit(Player player) {
        Weapon.giveWeapon(player, Weapon.DEFAULT_SWORD);
    }

    @EventHandler(priority= EventPriority.HIGH)
    public void onPlayerStomp(EntityDamageEvent e)
    {
        KitManager km = KitManager.getManager();

        if (!(e.getEntity() instanceof Player)) {
            return;
        }

        Player p = (Player)e.getEntity();
        if (e.getCause() == EntityDamageEvent.DamageCause.FALL)
        {
            if(km.getKitAmIUsing(p, "stomper")){
                for (Entity ent : p.getNearbyEntities(5.0D, 2.0D, 5.0D)) {
                    if ((ent instanceof Player))
                    {
                        Player plr = (Player)ent;
                        if (e.getDamage() <= 4.0D)
                        {
                            e.setCancelled(true);
                            return;
                        }
                        if (plr.isSneaking())
                        {
                            plr.damage(6.0D, p);
                            plr.sendMessage(prefix +  " §7You were stomped by: §b" + p.getName());
                        }
                        else
                        {
                            plr.damage(e.getDamage(), p);
                            plr.sendMessage(prefix + " §7You were stomped by: §b" + p.getName());
                        }
                    }
                }
                e.setDamage(5.0D);
                return;
            }
            return;
        }
    }
}
