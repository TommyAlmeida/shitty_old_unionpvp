package eu.union.dev.commands.staff;

import eu.union.dev.api.Icon;
import eu.union.dev.engine.Kit;
import eu.union.dev.engine.managers.KitManager;
import eu.union.dev.utils.globals.Messages;
import eu.union.dev.utils.globals.Perms;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;

public class AdminCMD implements CommandExecutor, Listener {

    private ArrayList<Player> admin = new ArrayList<>();

    Icon openInv = new Icon(Material.ANVIL, "§cOpen Inventory");

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            return true; //Retorna
        }

        Player player = (Player) sender;

        if(Perms.isStaff(player)){
            if(admin.contains(player)){
                for(Player online : Bukkit.getOnlinePlayers()){
                    online.showPlayer(player);
                }
                player.sendMessage(Messages.PREFIX.toString() + " §7You are no longer in §cadmin mode");
                admin.remove(player);
            }else{
                for(Player online : Bukkit.getOnlinePlayers()){
                    online.hidePlayer(player);
                }
                player.sendMessage(Messages.PREFIX.toString() + " §7You are now in §aadmin mode");
                setItems(player.getInventory());
                admin.add(player);
            }
        }
        return true;
    }

    private void setItems(PlayerInventory pi){
        pi.setItem(0, openInv.build());
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e){
        if(admin.contains(e.getEntity())){
            e.setCancelled(true);
        }else{
            e.setCancelled(false);
        }
    }

    @EventHandler
    public void onPickup(PlayerPickupItemEvent e){
        if(admin.contains(e.getPlayer())){
            e.setCancelled(true);
        }else{
            e.setCancelled(false);
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEntityEvent e){
        Player p = e.getPlayer();
        Player clicked = (Player) e.getRightClicked();

        ItemStack item = p.getItemInHand();

        if (item == null) {
            return;
        }

        if (!(item.getType() == openInv.getMaterial())) {
            return;
        }

        if (item.getItemMeta() == null) {
            return;
        }

        if (!(item.getItemMeta().hasDisplayName())) {
            return;
        }

        if (!(item.getItemMeta().getDisplayName() == openInv.getName())) {
            return;
        }

        if(admin.contains(p)){
            p.openInventory(clicked.getInventory());
        }
    }
}
