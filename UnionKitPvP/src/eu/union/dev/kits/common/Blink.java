package eu.union.dev.kits.common;

import eu.union.dev.PvPMain;
import eu.union.dev.api.Icon;
import eu.union.dev.api.Packets;
import eu.union.dev.engine.Kit;
import eu.union.dev.engine.managers.KitManager;
import eu.union.dev.utils.globals.Util;
import eu.union.dev.utils.globals.Weapon;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;

/**
 * Created by Fentis on 01/06/2016.
 */
public class Blink extends Kit implements Listener{

    public Blink() {
        super("blink", "unkit.blink", Difficulty.LOW, Rarity.COMMON, 0,new Icon(Material.NETHER_STAR), Category.TELEPORT);
    }

    @Override
    public void applyKit(Player player) {
        Weapon.giveWeapon(player, Weapon.DEFAULT_SWORD);
        Weapon.giveWeapon(player, Weapon.BLINK_STAR,1);
    }

    HashMap<Player, Integer> blink = new HashMap<>();
    ArrayList<Player> cd = new ArrayList<>();
    @SuppressWarnings("deprecation")
    @EventHandler
    public void onclick(PlayerInteractEvent e){
        Player p = e.getPlayer();
        KitManager km = KitManager.getManager();
        if (km.getKitAmIUsing(p,"blink") &&
                p.getItemInHand().getType() == Material.NETHER_STAR &&
                e.getAction() == Action.RIGHT_CLICK_AIR){
            if (!cd.contains(p)){
                Block b = p.getTargetBlock((HashSet<Byte>)null, 5);
                if (blink.containsKey(p)){
                    if (blink.get(p) <=2){
                        blink.put(p, blink.get(p)+1);
                        if (b.getType() != Material.AIR){
                            p.sendBlockChange(b.getLocation(), Material.LEAVES, (byte)0);
                            Bukkit.getScheduler().scheduleSyncDelayedTask(PvPMain.getInstance(), new Runnable() {
                                public void run() {
                                    p.sendBlockChange(b.getLocation(), b.getType(), b.getData());
                                }
                            }, 3*20);
                        }else{
                            b.setType(Material.LEAVES);
                            Bukkit.getScheduler().scheduleSyncDelayedTask(PvPMain.getInstance(), new Runnable() {
                                public void run() {
                                    b.setType(Material.AIR);
                                }
                            }, 3*20);
                        }
                        Location loc = b.getRelative(BlockFace.UP).getLocation().add(0.5, 0, 0.5);
                        loc.setPitch(p.getLocation().getPitch());
                        loc.setYaw(p.getLocation().getYaw());
                        p.teleport(loc);
                    }

                    if (blink.get(p)==3){
                        cd.add(p);
                        Bukkit.getScheduler().scheduleSyncDelayedTask(PvPMain.getInstance(), new Runnable() {
                            @Override
                            public void run() {
                                cd.remove(p);
                                blink.remove(p);
                            }
                        }, 30*20);
                    }
                }else{
                    blink.put(p, 1);
                    if (b.getType() != Material.AIR){
                        p.sendBlockChange(b.getLocation(), Material.LEAVES, (byte)0);
                        Bukkit.getScheduler().scheduleSyncDelayedTask(PvPMain.getInstance(), new Runnable() {
                            public void run() {
                                p.sendBlockChange(b.getLocation(), b.getType(), b.getData());
                            }
                        }, 3*20);
                    }else{
                        b.setType(Material.LEAVES);
                        Bukkit.getScheduler().scheduleSyncDelayedTask(PvPMain.getInstance(), new Runnable() {
                            public void run() {
                                b.setType(Material.AIR);
                            }
                        }, 3*20);
                    }
                    Location loc = b.getRelative(BlockFace.UP).getLocation().add(0.5, 0, 0.5);
                    loc.setPitch(p.getLocation().getPitch());
                    loc.setYaw(p.getLocation().getYaw());
                    p.teleport(loc);
                }
            }else{
                Packets.getAPI().sendActionBar(p,"§aIn Cooldown!");
            }
        }
    }
}
