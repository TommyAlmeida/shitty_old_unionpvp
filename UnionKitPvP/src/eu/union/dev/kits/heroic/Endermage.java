package eu.union.dev.kits.heroic;

import eu.union.dev.PvPMain;
import eu.union.dev.api.Icon;
import eu.union.dev.engine.Kit;
import eu.union.dev.engine.managers.KitManager;
import eu.union.dev.utils.globals.Weapon;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class Endermage extends Kit implements Listener {

    ArrayList<String> cd = new ArrayList<>();

    public Endermage() {
        super("endermage", "unkit.endermage", Difficulty.MEDIUM, Rarity.HEROIC, 5, new Icon(Material.ENDER_PORTAL_FRAME), Category.SOCIAL, 1000L);
    }

    @Override
    public void applyKit(Player p) {
        Weapon.giveWeapon(p, Weapon.DEFAULT_SWORD);
        Weapon.giveWeapon(p, Weapon.ENDERMAGE_PORTAL, 1);
    }

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onclick(PlayerInteractEvent e) {
        final Player p = e.getPlayer();
        KitManager km = KitManager.getManager();
        if (km.getKitAmIUsing(p, "endermage")) {
            if (p.getItemInHand().getType() == Material.ENDER_PORTAL_FRAME &&
                    e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                e.setCancelled(true);
                p.updateInventory();
                if (!cd.contains(p.getName())) {
                    final Location bl = e.getClickedBlock().getLocation();
                    final List<Player> players = new ArrayList<>();
                    for (Player p2 : p.getWorld().getPlayers()) {
                        if ((Math.abs(bl.getX() - p2.getLocation().getX()) < 3.0D) &&
                                (Math.abs(bl.getZ() - p2.getLocation().getZ()) < 3.0D) &&
                                (Math.abs(bl.getY() - p2.getLocation().getY()) >= 5.0D) &&
                                !p2.canSee(p)) {
                            players.add(p2);
                            p2.setNoDamageTicks(5 * 20);
                            p2.teleport(bl.add(0.5D, 1.0D, 0.5D));
                            p2.playSound(p2.getLocation(), Sound.ENDERMAN_TELEPORT, 1.0F, 1.0F);
                            p2.sendMessage("§aYou were pulled by " + p.getName() + "! You have 5 seconds of invincibility!");
                        }
                    }
                    if (!players.isEmpty()) {
                        p.setNoDamageTicks(5 * 20);
                        p.teleport(bl);
                        p.getWorld().playEffect(p.getLocation(), Effect.ENDER_SIGNAL, 10);
                        p.playSound(bl, Sound.ENDERMAN_TELEPORT, 1.0F, 1.0F);
                        p.sendMessage("§aYou pulled someone and are now with 5 second of invincibility!");
                    } else {
                        cd.add(p.getName());
                        new BukkitRunnable() {
                            int i = 0;
                            boolean status = true;

                            @Override
                            public void run() {
                                if (i == 5) {
                                    cd.remove(p.getName());
                                    p.sendMessage("§aThe cooldown is over!");
                                    cancel();
                                }
                                i++;
                                if (status) {
                                    for (Player p2 : p.getWorld().getPlayers()) {
                                        if ((Math.abs(bl.getX() - p2.getLocation().getX()) < 3.0D) &&
                                                (Math.abs(bl.getZ() - p2.getLocation().getZ()) < 3.0D) &&
                                                (Math.abs(bl.getY() - p2.getLocation().getY()) >= 5.0D) &&
                                                p2.canSee(p)) {
                                            status = false;
                                            players.add(p2);
                                            p2.setNoDamageTicks(5 * 20);
                                            p2.teleport(bl.add(0.5D, 1.0D, 0.5D));
                                            p2.playSound(p2.getLocation(), Sound.ENDERMAN_TELEPORT, 1.0F, 1.0F);
                                            p2.sendMessage("§aYou were pulled by " + p.getName() + "! You have 5 seconds of invincibility!");
                                        }
                                    }
                                    if (!players.isEmpty()) {
                                        p.setNoDamageTicks(5 * 20);
                                        p.teleport(bl);
                                        p.getWorld().playEffect(p.getLocation(), Effect.ENDER_SIGNAL, 10);
                                        p.playSound(bl, Sound.ENDERMAN_TELEPORT, 1.0F, 1.0F);
                                        p.sendMessage("§aYou pulled someone and are now with 5 second of invincibility!");
                                    }
                                } else {
                                    cd.remove(p.getName());
                                    p.sendMessage("§aThe cooldown is over!");
                                    cancel();
                                }
                            }
                        }.runTaskTimer(PvPMain.getInstance(), 0, 20);
                    }
                } else {
                    p.sendMessage("§cYou are in cooldown!");
                }
            }
        }
    }
}
