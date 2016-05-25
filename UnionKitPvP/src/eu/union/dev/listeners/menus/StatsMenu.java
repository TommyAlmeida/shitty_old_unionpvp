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

    private Inventory inv = Bukkit.createInventory(null, 9*3, "Stats");

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

        for(int i = 0; i < 27; i++){
            Icon spacer = new Icon(Material.STAINED_GLASS_PANE, "§a");
            inv.setItem(i,spacer.build());
        }

        {
            Icon icon = new Icon(Material.STONE_SWORD, "§9Kills", "§f" + kplayer.getKills());
            inv.setItem(10,icon.build());
        }

        {
            Icon icon = new Icon(Material.LEATHER_HELMET, "§9KDR", "§f" + kplayer.getKills());
            inv.setItem(11,icon.build());
        }

        {
            Icon icon = new Icon(Material.EXP_BOTTLE, "§9Level", "§f" + kplayer.getLevel());
            inv.setItem(12,icon.build());
        }

        {
            Icon icon = new Icon(Material.EMERALD, "§9Coins", "§f" + kplayer.getCoins());
            inv.setItem(13,icon.build());
        }

        {
            Icon icon = new Icon(Material.REDSTONE, "§9Deaths", "§f" + kplayer.getDeaths());
            inv.setItem(14,icon.build());
        }

        {
            Icon icon = new Icon(Material.GOLD_AXE, "§9Streaks §o§cSOON");
            inv.setItem(15,icon.build());
        }

        {
            Icon icon = new Icon(Material.CHAINMAIL_CHESTPLATE, "§9Assists §o§cSOON");
            inv.setItem(16,icon.build());
        }
    }

    @EventHandler
    public void onInvClick(InventoryClickEvent e){
        if(e.getInventory().getName() == "Stats"){
            if(e.getSlot() < 0){
                return;
            }

            if(e.getCurrentItem().getItemMeta().getDisplayName() == "§a"){
                e.setCancelled(true);
            }

            switch(e.getSlot()){
                case 10:
                    e.setCancelled(true);
                    break;
                case 11:
                    e.setCancelled(true);
                    break;
                case 12:
                    e.setCancelled(true);
                    break;
                case 13:
                    e.setCancelled(true);
                    break;
                case 14:
                    e.setCancelled(true);
                    break;
                case 15:
                    e.setCancelled(true);
                    break;
                case 16:
                    e.setCancelled(true);
                    break;
            }
        }
    }
}
