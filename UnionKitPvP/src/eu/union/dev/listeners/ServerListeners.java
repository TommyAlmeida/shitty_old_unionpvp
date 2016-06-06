package eu.union.dev.listeners;

import eu.union.dev.PvPMain;
import eu.union.dev.commands.staff.BuildCMD;
import eu.union.dev.utils.globals.Perms;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.player.PlayerAchievementAwardedEvent;
import org.bukkit.event.player.PlayerChatTabCompleteEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Random;

public class ServerListeners implements Listener {

    /**
     * On player click chest refills with soups
     *
     * @param e
     */
    @EventHandler
    public void refillChest(PlayerInteractEvent e) {
        Action a = e.getAction();
        ArrayList<ItemStack> items = new ArrayList<>();
        Random rand = new Random();

        if (!(a == Action.RIGHT_CLICK_BLOCK)) return;

        if (e.getClickedBlock().getState() instanceof Chest) {
            Chest c = (Chest) e.getClickedBlock().getState();

            Inventory cinv = c.getInventory();

            for (ItemStack item : cinv.getContents()) {
                if (item != null) {
                    return;
                }
            }

            for (int i = 0; i < cinv.getSize(); i++) {
                cinv.setItem(i, new ItemStack(Material.MUSHROOM_SOUP));
            }

            Bukkit.getScheduler().scheduleSyncDelayedTask(PvPMain.getInstance(), new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < cinv.getSize(); i++) {
                        if(cinv.getItem(i).getType() == Material.AIR){
                            cinv.setItem(i, new ItemStack(Material.MUSHROOM_SOUP));
                        }
                    }
                }
            },20);
        }
    }

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onTabComplete(PlayerChatTabCompleteEvent e) {
        e.getTabCompletions().clear();
    }

    @EventHandler
    public void onBlockMelt(BlockFadeEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onBlockIgnite(BlockBurnEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if (Perms.isStaff(e.getPlayer())) {
            if (BuildCMD.build.contains(e.getPlayer())) {
                e.setCancelled(false);
            } else {
                e.setCancelled(true);
            }
        } else {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent e) {
        for (Block b : e.blockList()) {
            final BlockState state = b.getState();

            b.setType(Material.AIR);

            int delay = 20;
            if ((b.getType() == Material.SAND) || (b.getType() == Material.GRAVEL)) {
                delay++;
            }
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(PvPMain.getInstance(), new Runnable() {
                public void run() {
                    state.update(true, false);
                }
            }, delay);
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        if (Perms.isStaff(e.getPlayer())) {
            if (BuildCMD.build.contains(e.getPlayer())) {
                e.setCancelled(false);
            } else {
                e.setCancelled(true);
            }
        } else {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onQuest(PlayerAchievementAwardedEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onFood(FoodLevelChangeEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void noDrop(ItemSpawnEvent e) {
        final Item drop = e.getEntity();
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(PvPMain.getInstance(), new Runnable() {
            public void run() {
                drop.getWorld().playEffect(drop.getLocation(), Effect.SMOKE, 4);
                drop.remove();
            }
        }, 20*4);
    }


    public void onCrop(PlayerInteractEvent event)
    {
        if(event.getAction() == Action.PHYSICAL)
        {
            Block block = event.getClickedBlock();

            if(block == null)
                return;

            int blockType = block.getTypeId();

            if(blockType == Material.SOIL.getId())
            {
                event.setUseInteractedBlock(org.bukkit.event.Event.Result.DENY);
                event.setCancelled(true);

                block.setTypeId(blockType);
                block.setData(block.getData());
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onSignChange(SignChangeEvent e) {
        if (e.getLine(0).equalsIgnoreCase("[Soup]")) {
            e.setLine(0, "[Soup]");
            e.setLine(1, "§6Click here");
        }else if (e.getLine(0).equalsIgnoreCase("[Refill]")) {
            e.setLine(0, "[Refill]");
            e.setLine(1, "§6Click here");
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (!(e.getAction() == Action.RIGHT_CLICK_BLOCK)) return;
        if (e.getClickedBlock().getState() instanceof Sign) {
            Sign s = (Sign) e.getClickedBlock().getState();
            if (s.getLine(0).equalsIgnoreCase("[Soup]")) {
                Inventory inv = Bukkit.createInventory(null, 54, "Soups");
                for(int i = 0; i < inv.getSize(); i++){
                    inv.setItem(i, new ItemStack(Material.MUSHROOM_SOUP));
                }

                e.getPlayer().openInventory(inv);
            }else if (s.getLine(0).equalsIgnoreCase("[Refill]")) {
                Inventory inv = Bukkit.createInventory(null, 9, "Refill");
                inv.setItem(0, new ItemStack(Material.RED_MUSHROOM,64));
                inv.setItem(1, new ItemStack(Material.BROWN_MUSHROOM,64));
                inv.setItem(2, new ItemStack(Material.BOWL,64));
                e.getPlayer().openInventory(inv);
            }
        }
    }
}
