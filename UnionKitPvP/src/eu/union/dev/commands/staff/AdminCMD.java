package eu.union.dev.commands.staff;

import eu.union.dev.api.Icon;
import eu.union.dev.utils.Messages;
import eu.union.dev.utils.Perms;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;

public class AdminCMD implements CommandExecutor, Listener {

    public static ArrayList<Player> admin = new ArrayList<>();

    Icon quickChange = new Icon(Material.MAGMA_CREAM, "§6QuickChange", "§7Quick change your admin mode");
    Icon openInv = new Icon(Material.ANVIL, "§9Open Inventory", "§7Open the players right clicked inventory");
    Icon ff = new Icon(Material.STICK, "§bCheck ff", "§7Check if player have ff");
    Icon buildOn = new Icon(Material.GRASS, "§eBuild Mode", "§7Set your build mode to online");

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) {
            return true;
        }

        /**
         * | 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 |
         */
        Player p = (Player) commandSender;
        PlayerInventory pi = p.getInventory();

        if (Perms.isStaff(p)) {
            if (admin.contains(p)) {
                admin.remove(p);
                p.sendMessage(Messages.PREFIX.toString() + " §7You are §cno longer §7in §aadmin mode!");
            } else {
                admin.add(p);
                p.sendMessage(Messages.PREFIX.toString() + " §7Your §aadmin mode §7has been enabled!");
                pi.clear();
                pi.setItem(2, quickChange.build());
                pi.setItem(4, openInv.build());
                pi.setItem(6, ff.build());
                pi.setItem(8, buildOn.build());
            }
        }

        return false;
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent e) {
        if (admin.contains(e.getEntity())) {
            e.setCancelled(true);
        } else {
            e.setCancelled(false);
        }
    }

    @EventHandler
    public void onRightClickPlayer(PlayerInteractEntityEvent e) {
        if (e.getRightClicked() instanceof Player) {
            Player clicked = (Player) e.getRightClicked();
            Player player = e.getPlayer();

            if (admin.contains(player)) {

                if (player.getItemInHand() == null) {
                    return;
                }

                if (player.getItemInHand().getItemMeta() == null) {
                    return;
                }

                if (!player.getItemInHand().hasItemMeta()) {
                    return;
                }

                if (player.getItemInHand().getItemMeta().getDisplayName().contains(openInv.getName())) {
                    player.openInventory(clicked.getInventory());
                }

                if (player.getItemInHand().getItemMeta().getDisplayName().contains(ff.getName())) {
                    player.sendMessage(Messages.PREFIX.toString() + " §cSoon");
                }
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        PlayerInventory pi = p.getInventory();
        ItemStack item = p.getItemInHand();

        if (item == null) {
            return;
        }

        if (!(item.getType() == Material.COMPASS)) {
            return;
        }

        if (item.getItemMeta() == null) {
            return;
        }

        if (!(item.getItemMeta().hasDisplayName())) {
            return;
        }

        if (item.getItemMeta().getDisplayName() == quickChange.getName()) {
            if (admin.contains(p)) {
                admin.remove(p);
                p.sendMessage(Messages.PREFIX.toString() + " §7You are §cno longer §7in §aadmin mode!");
            } else {
                admin.add(p);
                p.sendMessage(Messages.PREFIX.toString() + " §7Your §aadmin mode §7has been enabled!");
                pi.clear();
                pi.setItem(2, quickChange.build());
                pi.setItem(4, openInv.build());
                pi.setItem(6, ff.build());
                pi.setItem(8, buildOn.build());
            }
        }else if (item.getItemMeta().getDisplayName() == buildOn.getName()) {
            Bukkit.dispatchCommand(p,"build");
        }
    }

}
