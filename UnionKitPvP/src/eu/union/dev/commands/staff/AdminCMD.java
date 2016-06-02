package eu.union.dev.commands.staff;

import eu.union.dev.api.Icon;
import eu.union.dev.engine.managers.KitManager;
import eu.union.dev.utils.globals.Messages;
import eu.union.dev.utils.globals.Perms;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;

public class AdminCMD implements CommandExecutor, Listener {

    public static ArrayList<Player> admin = new ArrayList<>();

    private Icon quickChange = new Icon(Material.MAGMA_CREAM, "§6QuickChange", "§7Quick change your admin mode");
    private Icon openInv = new Icon(Material.ANVIL, "§9Open Inventory", "§7Open the players right clicked inventory");
    private Icon info = new Icon(Material.BLAZE_ROD, "§bPlayer Info", "§7Check player informations");
    private Icon buildOn = new Icon(Material.GRASS, "§eBuild Mode", "§7Set your build mode to online");

    private int getAmount(Player p, Material m) {
        int amount = 0;
        ItemStack[] arrayOfItemStack;
        int j = (arrayOfItemStack = p.getInventory().getContents()).length;
        for (int i = 0; i < j; i++) {
            ItemStack item = arrayOfItemStack[i];
            if ((item != null) && (item.getType() == m) &&
                    (item.getAmount() > 0)) {
                amount += item.getAmount();
            }
        }
        return amount;
    }

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
                pi.clear();
                for (Player online : Bukkit.getOnlinePlayers()) {
                    online.showPlayer(p);
                }
            } else {
                admin.add(p);
                p.sendMessage(Messages.PREFIX.toString() + " §7Your §aadmin mode §7has been enabled!");
                p.setGameMode(GameMode.CREATIVE);
                for (Player online : Bukkit.getOnlinePlayers()) {
                    online.hidePlayer(p);
                }
                pi.clear();
                pi.setItem(0, quickChange.build());
                pi.setItem(2, openInv.build());
                pi.setItem(4, info.build());
                pi.setItem(6, buildOn.build());
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
    public void onAdminPickupItem(PlayerPickupItemEvent e) {
        Player p = e.getPlayer();
        if (admin.contains(p.getName())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onRightClickPlayer(PlayerInteractEntityEvent e) {
        if (e.getRightClicked() instanceof Player) {
            Player clicked = (Player) e.getRightClicked();
            Player player = e.getPlayer();
            KitManager km = KitManager.getManager();

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
                } else if (player.getItemInHand().getItemMeta().getDisplayName().contains(info.getName())) {
                    player.sendMessage("§7§m-------------------------------");
                    player.sendMessage("§9Health: §e" + clicked.getMaxHealth());
                    player.sendMessage("§9GameMode: §e" + clicked.getGameMode());
                    player.sendMessage("§9Is Flying: §e" + clicked.isFlying());
                    player.sendMessage("§9Soups: §e" + getAmount(clicked, Material.MUSHROOM_SOUP));
                    player.sendMessage("§9Using kit: §e" + km.getKitByPlayer(clicked).getName());
                    player.sendMessage("§7§m-------------------------------");
                }
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        ItemStack item = p.getItemInHand();

        if (item.getItemMeta().getDisplayName() == "§6QuickChange") {
            p.chat("/admin");
        } else if (item.getItemMeta().getDisplayName() == "§eBuild Mode") {
            p.chat("/build");
        }
    }

}
