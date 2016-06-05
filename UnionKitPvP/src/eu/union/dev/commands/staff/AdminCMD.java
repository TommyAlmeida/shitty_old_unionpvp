package eu.union.dev.commands.staff;

import eu.union.dev.PvPMain;
import eu.union.dev.engine.KPlayer;
import eu.union.dev.engine.managers.PlayerManager;
import eu.union.dev.listeners.menus.MenuAdmin;
import eu.union.dev.listeners.menus.StatsMenuAPI;
import eu.union.dev.utils.globals.Messages;
import eu.union.dev.utils.globals.Perms;
import eu.union.dev.utils.globals.Weapon;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;

public class AdminCMD implements Listener, CommandExecutor {

    public static ArrayList<Player> admin = new ArrayList<>();
    public static HashMap<Player, Player> affected = new HashMap<>();

    public void onLeave(Player p) {
        p.setGameMode(GameMode.SURVIVAL);
        affected.remove(p);
        admin.remove(p);
        p.getInventory().clear();

        for (Player all : Bukkit.getOnlinePlayers()) {
            all.showPlayer(p);
        }

    }

    public void onEnter(Player p) {
        admin.add(p);

        p.setGameMode(GameMode.CREATIVE);
        p.getInventory().clear();
        Weapon.giveWeapon(p, Weapon.MENU_ADMIN, 6);
        Weapon.giveWeapon(p, Weapon.SWITCH_ADMIN, 2);
        Weapon.giveWeapon(p, Weapon.STATUS_ADMIN, 4);

        for (Player all : Bukkit.getOnlinePlayers()) {

            if (Perms.isStaff(all)) {
                all.showPlayer(p);
            } else {
                all.hidePlayer(p);
            }
        }
    }

    public boolean onCommand(CommandSender sender, Command cmd, String Label, String[] args) {

        if (cmd.getName().equalsIgnoreCase("admin")) {

            if (!(sender instanceof Player)) {
                return true;
            }

            Player p = (Player) sender;

            if (Perms.isStaff(p)) {
                if (!admin.contains(p)) {
                    onEnter(p);
                    p.sendMessage(Messages.PREFIX + "§7 You now are in Administration Mode!");

                } else {
                    onLeave(p);
                    p.sendMessage(Messages.PREFIX + " §7You leave the Administration Mode!");
                }
            }


        }
        return false;
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e){
        if(e.getEntity() instanceof Player){
            if(admin.contains(e.getEntity())){
                e.setCancelled(true);
            }else{
                e.setCancelled(false);
            }
        }
    }

    @EventHandler
    void drops(PlayerDropItemEvent e) {
        Player p = e.getPlayer();

        if (p.getItemInHand().getType() == Material.SLIME_BALL && p.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§eSwitch Admin§7 (Right Click)")) {
            if (admin.contains(p)) {
                e.setCancelled(true);
            }
        }
        if (p.getItemInHand().getType() == Material.BLAZE_POWDER && p.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§eMenu Admin§7 (Right Click Player)")) {
            if (admin.contains(p)) {
                e.setCancelled(true);
            }
        }
        if (p.getItemInHand().getType() == Material.SKULL_ITEM && p.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§eStatus Player§7 (Right Click Player)")) {
            if (admin.contains(p)) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    void pegar(PlayerPickupItemEvent e) {
        Player p = e.getPlayer();

        if (admin.contains(p)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    void interagirEntityMenu(PlayerInteractEntityEvent e) {
        if (e.getRightClicked() instanceof Player) {

            if (e.getRightClicked() == null) {
                return;
            }

            Player p = e.getPlayer();
            Player vitima = (Player) e.getRightClicked();
            if (vitima != null && p.getItemInHand().getType() == Material.BLAZE_POWDER 
            && p.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§eMenu Admin§7 (Right Click Player)")) {
                if (admin.contains(p)) {
                    affected.put(p, vitima);
                    MenuAdmin.openInventoryAdmin(p);
                }
            }
        }
    }

    @EventHandler
    void interagirEntity(PlayerInteractEntityEvent e) {
        if (e.getRightClicked() instanceof Player) {

            Player p = e.getPlayer();
            Player vitima = (Player) e.getRightClicked();

       	     if(p.getItemInHand().getType() == Material.AIR){
				if(admin.contains(p)){
					p.openInventory(vitima.getInventory());
				}
			}
        }
    }

    @EventHandler
    void interagirEntityStatus(PlayerInteractEntityEvent e) {
        if (e.getRightClicked() instanceof Player) {


            Player p = e.getPlayer();
            Player vitima = (Player) e.getRightClicked();

            if (p.getItemInHand().getType() == Material.SKULL_ITEM && p.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§eStatus Player§7 (Right Click Player)")) {

                if (admin.contains(p)) {
                    KPlayer vitima2 = PlayerManager.getPlayer(vitima.getUniqueId());
                    StatsMenuAPI.setItems(p, vitima2);
                }
            }
        }
    }

    @EventHandler
    void interagirSwti(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (p.getItemInHand().getType() == Material.SLIME_BALL && p.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§eSwitch Admin§7 (Right Click)")) {
            if (admin.contains(p)) {
                onLeave(p);

                new BukkitRunnable() {

                    public void run() {
                        onEnter(p);
                    }
                }.runTaskLater(PvPMain.getInstance(), 16L);
            }
        }
    }
}
