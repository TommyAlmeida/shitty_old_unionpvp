package eu.union.dev.listeners.menus;

import eu.union.dev.api.Icon;
import eu.union.dev.engine.managers.KitManager;
import eu.union.dev.engine.layouts.KitLayout;
import eu.union.dev.utils.Inv;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class KitMenu implements Listener {

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        ItemStack item = p.getItemInHand();

        if (item == null) {
            return;
        }

        if (!(item.getType() == Material.NETHER_STAR)) {
            return;
        }

        if (item.getItemMeta() == null) {
            return;
        }

        if (!(item.getItemMeta().hasDisplayName())) {
            return;
        }

        if (!(item.getItemMeta().getDisplayName() == "§aKits §7(Right-Click)")) {
            return;
        }

        e.setCancelled(true);
        setItems(p);
        p.openInventory(Inv.getInstance().kits);
    }

    void setItems(Player p) {
        KitManager km = KitManager.getManager();

        Inventory inv = Inv.getInstance().kits;

        {
            Icon icon = new Icon(Material.WOOD_SWORD, "");
            inv.setItem(0, KitLayout.getLayout().design(icon, km.getKitByName("pvp")));
        }

        {
            Icon icon = new Icon(Material.STICK);
            inv.setItem(1, KitLayout.getLayout().design(icon, km.getKitByName("grandpa")));
        }

        {
            Icon icon = new Icon(Material.BOW);
            inv.setItem(2, KitLayout.getLayout().design(icon, km.getKitByName("archer")));
        }

        {
            Icon icon = new Icon(Material.ANVIL);
            inv.setItem(3, KitLayout.getLayout().design(icon, km.getKitByName("stomper")));
        }

        {
            Icon icon = new Icon(Material.MAGMA_CREAM);
            inv.setItem(4, KitLayout.getLayout().design(icon, km.getKitByName("pulsar")));
        }

    }

    @EventHandler
    public void onInvClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        ItemStack item = e.getCurrentItem();

        if (e.getInventory().getName().equalsIgnoreCase("Kits")) {
            if (e.getSlot() < 0) {
                return;
            }

            switch (e.getSlot()) {
                case 0: //Kit PvP
                    offerKit(p, "pvp");
                    e.getView().close();
                    break;
                case 1: //Kit Archer
                    offerKit(p, "archer");
                    e.getView().close();
                    break;
                case 2: //Kit GrandPa
                    offerKit(p, "grandpa");
                    e.getView().close();
                    e.setCancelled(true);
                    break;
                case 3: //Kit Stomper
                    offerKit(p, "stomper");
                    e.getView().close();
                    e.setCancelled(true);
                    break;
                case 4: //Kit Pulsar
                    offerKit(p, "pulsar");
                    e.getView().close();
                    e.setCancelled(true);
                    break;
            }
        }
    }

    public void offerKit(Player p, String kit) {
        p.chat("/kit %kit".replace("%kit", kit.trim()));
    }
}
