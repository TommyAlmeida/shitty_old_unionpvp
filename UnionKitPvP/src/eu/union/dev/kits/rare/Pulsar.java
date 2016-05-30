package eu.union.dev.kits.rare;

import eu.union.dev.PvPMain;
import eu.union.dev.api.Ability;
import eu.union.dev.api.Icon;
import eu.union.dev.engine.Kit;
import eu.union.dev.utils.globals.Util;
import eu.union.dev.utils.globals.Weapon;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Pulsar extends Kit implements Listener {


    public Ability cooldown = new Ability(1, 15, TimeUnit.SECONDS);
    private ArrayList<Player> fall = new ArrayList<>();
    public Pulsar() {
        super("pulsar", "unkit.pulsar", Difficulty.MEDIUM, Rarity.BEAST, 0, new Icon(Material.MAGMA_CREAM), Category.SPAWNER);
    }

    @Override
    public void applyKit(Player player) {
        Weapon.giveWeapon(player, Weapon.DEFAULT_SWORD);
        Weapon.giveWeapon(player, Weapon.PULSAR_SHOCK, 1);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        ItemStack item = p.getItemInHand();

        if (item == null) {
            return;
        }

        if (!(item.getType() == Material.MAGMA_CREAM)) {
            return;
        }

        if (item.getItemMeta() == null) {
            return;
        }

        if (!(item.getItemMeta().hasDisplayName())) {
            return;
        }

        if (!(item.getItemMeta().getDisplayName() == Weapon.PULSAR_SHOCK.getName())) {
            return;
        }

        if (cooldown.tryUse(p)) {
            for (Entity e : p.getNearbyEntities(5, 5, 5)) {
                if (e instanceof Player) {
                    e.setVelocity(new Vector(0, 2, 0));
                    e.getWorld().strikeLightning(e.getLocation());
                    ((Player) e).damage(6.0D);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(PvPMain.getInstance(), new Runnable() {
                        @Override
                        public void run() {
                            ((Player) e).damage(7.0D);
                        }
                    }, 20 * 2);
                    e.sendMessage(prefix + " §7Shi**, you have been pulsed by: §e" + p.getDisplayName());
                    fall.add((Player) e);
                }
            }
        } else {
            Util.getInstance().sendCooldownMessage(p, cooldown, TimeUnit.SECONDS, true);
        }
    }

    @EventHandler
    public void onFallDamage(EntityDamageEvent e) {
        Player p = (Player) e.getEntity();

        if (e.getEntity() instanceof Player) {
            if (e.getCause() == EntityDamageEvent.DamageCause.FALL) {
                if (fall.contains(p)) {
                    e.setCancelled(true);
                    for (int i = 0; i < 10; i++) {
                        fall.remove(p);
                    }
                }
            }
        }
    }

}
