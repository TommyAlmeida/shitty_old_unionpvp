package eu.union.dev.kits.beast;

import eu.union.dev.PvPMain;
import eu.union.dev.api.Ability;
import eu.union.dev.api.Icon;
import eu.union.dev.api.Packets;
import eu.union.dev.engine.Kit;
import eu.union.dev.engine.managers.KitManager;
import eu.union.dev.utils.ParticleEffect;
import eu.union.dev.utils.globals.Util;
import eu.union.dev.utils.globals.Weapon;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by Fentis on 18/05/2016.
 */
public class FireBoost extends Kit implements Listener {

    Ability cooldown = new Ability(1, 15, TimeUnit.SECONDS);

    public FireBoost() {
        super("fireboost", "unkit.fireboost", Difficulty.MEDIUM, Rarity.BEAST, 8, new Icon(Material.BLAZE_POWDER), Category.CHARGER, 1000L);
    }

    @Override
    public void applyKit(Player player) {
        Weapon.giveWeapon(player, Weapon.DEFAULT_SWORD);
    }

    @EventHandler
    public void onclick(PlayerInteractEvent e) {
        final Player p = e.getPlayer();
        KitManager km = KitManager.getManager();
        if (km.getKitAmIUsing(p, "fireboost") &&
                p.getItemInHand().getType().toString().contains("_SWORD") &&
                p.isSneaking()) {
            if (cooldown.tryUse(p)) {
                new BukkitRunnable() {
                    int i = 0;

                    @Override
                    public void run() {
                        i++;
                        msg(p, "§bPower", i, 5);
                        if (i <= 5) {
                            if (!p.getItemInHand().getType().toString().contains("_SWORD") || !p.isBlocking() || !p.isSneaking()) {
                                cancel();
                                p.setVelocity(new Vector(0.0, (i + 0.5), 0.0));
                            } else {
                                p.playNote(p.getLocation(), (byte) 0, (byte) (i + 10));
                            }
                        } else {
                            if (!p.getItemInHand().getType().toString().contains("_SWORD") || !p.isBlocking() || !p.isSneaking()) {
                                cancel();
                                p.setVelocity(new Vector(0.0, 5.5, 0.0));
                            }
                        }
                        if (i >= 60) {
                            cancel();
                            p.setVelocity(new Vector(0.0, 5.5, 0.0));
                        }
                    }
                }.runTaskTimer(PvPMain.getInstance(), 20, 20);
            } else {
                Util.getInstance().sendCooldownMessage(p, cooldown, TimeUnit.SECONDS, true);
            }
        }
    }

    @EventHandler
    public void ondamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            KitManager km = KitManager.getManager();
            if (km.getKitAmIUsing(p, "fireboost") &&
                    e.getCause() == EntityDamageEvent.DamageCause.FALL) {
                if (e.getDamage() >= 3.5) {
                    e.setDamage(3.5);
                    particles(p.getLocation());
                    for (Entity en : p.getNearbyEntities(10, 5, 10)) {
                        if (en instanceof Player) {
                            Vector vec = en.getLocation().toVector().subtract(p.getLocation().toVector()).normalize();
                            en.setVelocity(vec.multiply(2));
                            en.setFireTicks((new Random().nextInt(3) + 7) * 20);
                        }
                    }
                }
            }
        }
    }

    public void msg(Player p, String mensagem, int time, int maxtime) {
        String msg = mensagem + ": §a";
        char type = '▊';
        if (time >= maxtime) {
            time = maxtime;
        }
        for (int i = 0; i < time; i++) {
            msg = msg + type;
        }
        msg = msg + "§c";
        for (int i = 0; i < maxtime - time; i++) {
            msg = msg + type;
        }
        Packets.getAPI().sendActionBar(p, msg);
    }

    public void particles(final Location loc) {
        new BukkitRunnable() {
            double t = Math.PI / 4;

            public void run() {
                t = t + 0.1 * Math.PI;
                for (double theta = 0; theta <= 2 * Math.PI; theta = theta + Math.PI / 32) {
                    double x = t * Math.cos(theta);
                    double y = 0.1;
                    double z = t * Math.sin(theta);
                    loc.add(x, y, z);
                    ParticleEffect.FLAME.display(0, 0, 0, 1, 0, loc, 10);
                    loc.subtract(x, y, z);
                    theta = theta + Math.PI / 64;
                }
                if (t > 10) {
                    this.cancel();
                }
            }

        }.runTaskTimer(PvPMain.getInstance(), 0, 1);
    }
}
