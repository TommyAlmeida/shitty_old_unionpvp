package eu.union.dev.listeners.menus;

import eu.union.dev.api.Icon;
import eu.union.dev.engine.KPlayer;
import eu.union.dev.engine.managers.PlayerManager;
import eu.union.dev.utils.Inv;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class StatsMenu implements Listener{

    private Inventory inv = Bukkit.createInventory(null, 9*3);

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        ItemStack item = p.getItemInHand();

        if (item == null) {
            return;
        }

        if (!(item.getType() == Material.SKULL_ITEM)) {
            return;
        }

        if (item.getItemMeta() == null) {
            return;
        }

        if (!(item.getItemMeta().hasDisplayName())) {
            return;
        }

        if (!(item.getItemMeta().getDisplayName() == "§9Stats §7(Right-Click)")) {
            return;
        }

        e.setCancelled(true);
        setItems(p);
        p.openInventory(inv);
    }


    private void setItems(Player p){
        KPlayer kplayer = PlayerManager.getPlayer(p.getUniqueId());

        {
            Icon icon = new Icon(Material.STONE_SWORD, "§eKills", "§" + kplayer.getKills());
            inv.setItem();
        }

        {
            Icon icon = new Icon(Material.LEATHER_HELMET, "§eKDR", "§r" + kplayer.getKills());
        }

        {
            Icon icon = new Icon(Material.EXP_BOTTLE, "§eLevel", "§r" + kplayer.getLevel());
        }

        {
            Icon icon = new Icon(Material.EMERALD, "§eCoins", "§r" + kplayer.getCoins());
        }

        {
            Icon icon = new Icon(Material.REDSTONE, "§eDeaths", "§r" + kplayer.getDeaths());
        }
    }

    @EventHandler
    public void onInvClick(InventoryClickEvent e){

    }
}
