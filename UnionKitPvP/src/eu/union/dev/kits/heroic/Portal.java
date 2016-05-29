package eu.union.dev.kits.heroic;

import eu.union.dev.PvPMain;
import eu.union.dev.api.Ability;
import eu.union.dev.api.Icon;
import eu.union.dev.engine.Kit;
import eu.union.dev.engine.managers.KitManager;
import eu.union.dev.utils.Weapon;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;

/**
 * Created by Fentis on 26/05/2016.
 */
public class Portal extends Kit implements Listener{

    public Portal() {
        super("portal", "unkit.portal", Difficulty.MEDIUM, Rarity.HEROIC, 0, new Icon(Material.IRON_BARDING), Category.TELEPORT);
    }

    @Override
    public void applyKit(Player player) {
        Weapon.giveWeapon(player, Weapon.DEFAULT_SWORD);
        Weapon.giveWeapon(player, Weapon.PORTAL_BLUE,1);
        Weapon.giveWeapon(player, Weapon.PORTAL_ORANGE,2);
    }

    HashMap<Player, Location> blue = new HashMap<>();
    HashMap<Player, Location> orange = new HashMap<>();

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onclick(PlayerInteractEvent e){
        Player p = e.getPlayer();
        KitManager km = KitManager.getManager();
        if (km.getKitAmIUsing(p,"portal")){
            Block bloc = p.getTargetBlock((HashSet<Byte>)null, 100);
            if (p.getItemInHand().getType() == Material.DIAMOND_BARDING &&
                    (e.getAction() == Action.RIGHT_CLICK_BLOCK ||
                            e.getAction() == Action.RIGHT_CLICK_AIR)){
                e.setCancelled(true);
                p.updateInventory();
                if (bloc.getType() != Material.AIR && bloc.getRelative(BlockFace.UP).getType() == Material.AIR){
                    Block b = bloc.getRelative(BlockFace.UP);
                    if (blue.containsKey(p)){
                        p.sendBlockChange(blue.get(p), Material.AIR, (byte)0);
                    }
                    blue.put(p, b.getLocation().add(0.5, 0, 0.5));
                    Bukkit.getScheduler().scheduleSyncDelayedTask(PvPMain.getInstance(), new Runnable() {
                        public void run() {
                            p.sendBlockChange(b.getLocation(), Material.CARPET, (byte)11);
                        }
                    }, 1);
                }
            }
            if (p.getItemInHand().getType() == Material.GOLD_BARDING &&
                    (e.getAction() == Action.RIGHT_CLICK_BLOCK ||
                            e.getAction() == Action.RIGHT_CLICK_AIR)){
                e.setCancelled(true);
                p.updateInventory();
                if (bloc.getType() != Material.AIR && bloc.getRelative(BlockFace.UP).getType() == Material.AIR){
                    Block b = bloc.getRelative(BlockFace.UP);
                    if (orange.containsKey(p)){
                        p.sendBlockChange(orange.get(p), Material.AIR, (byte)0);
                    }
                    orange.put(p, b.getLocation().add(0.5, 0, 0.5));
                    Bukkit.getScheduler().scheduleSyncDelayedTask(PvPMain.getInstance(), new Runnable() {
                        public void run() {
                            p.sendBlockChange(b.getLocation(), Material.CARPET, (byte)1);
                        }
                    }, 1);
                }
            }
        }
    }
    Ability cooldown = new Ability(1, 3, TimeUnit.SECONDS);
    @EventHandler
    public void onmove(PlayerMoveEvent e){
        Player p = e.getPlayer();
        if (blue.containsKey(p) && orange.containsKey(p)){
          if (blue.get(p).distance(p.getLocation())<=1){
            if (cooldown.tryUse(p)){
              p.getWorld().playEffect(p.getLocation(), Effect.ENDER_SIGNAL,1);
              p.getWorld().playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 1);
              Location loc = orange.get(p);
              loc.setPitch(p.getLocation().getPitch());
              loc.setYaw(p.getLocation().getYaw());
              p.teleport(loc);
            }
          }
          if (orange.get(p).distance(p.getLocation())<=1){
            if (cooldown.tryUse(p)){
              p.getWorld().playEffect(p.getLocation(), Effect.ENDER_SIGNAL,1);
              p.getWorld().playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 1);
              Location loc = blue.get(p);
              loc.setPitch(p.getLocation().getPitch());
              loc.setYaw(p.getLocation().getYaw());
              p.teleport(loc);
            }
          }
        }
    }
    @EventHandler
    public void onquit(PlayerQuitEvent e){
        removePlayer(e.getPlayer());
    }
    @EventHandler
    public void ondeath(PlayerDeathEvent e){
        removePlayer(e.getEntity());
    }

    private void removePlayer(Player p) {
      if (blue.containsKey(p)){
        p.sendBlockChange(blue.get(p), Material.AIR, (byte)0);
        blue.remove(p);
      }
      if (orange.containsKey(p)){
        p.sendBlockChange(orange.get(p), Material.AIR, (byte)0);
        orange.remove(p);
      }
    }
}
