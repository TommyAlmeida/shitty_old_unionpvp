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
            Icon icon = new Icon(Material.IRON_BOOTS);
            inv.setItem(3, KitLayout.getLayout().design(icon, km.getKitByName("stomper")));
        }

        {
            Icon icon = new Icon(Material.MAGMA_CREAM);
            inv.setItem(4, KitLayout.getLayout().design(icon, km.getKitByName("pulsar")));
        }

        {
            Icon icon = new Icon(Material.ENDER_PORTAL_FRAME);
            inv.setItem(5, KitLayout.getLayout().design(icon, km.getKitByName("endermage")));
        }

        {
            Icon icon = new Icon(Material.FISHING_ROD);
            inv.setItem(6, KitLayout.getLayout().design(icon, km.getKitByName("fisherman")));
        }

        {
            Icon icon = new Icon(Material.SNOW_BALL);
            inv.setItem(7, KitLayout.getLayout().design(icon, km.getKitByName("switcher")));
        }

        {
            Icon icon = new Icon(Material.FIREWORK);
            inv.setItem(8, KitLayout.getLayout().design(icon, km.getKitByName("kangaroo")));
        }

        {
            Icon icon = new Icon(Material.IRON_CHESTPLATE);
            inv.setItem(9, KitLayout.getLayout().design(icon, km.getKitByName("turtle")));
        }

        {
            Icon icon = new Icon(Material.COAL_BLOCK);
            inv.setItem(10, KitLayout.getLayout().design(icon, km.getKitByName("ninja")));
        }

        {
            Icon icon = new Icon(Material.ANVIL);
            inv.setItem(11, KitLayout.getLayout().design(icon, km.getKitByName("anchor")));
        }

        {
            Icon icon = new Icon(Material.REDSTONE_TORCH_ON);
            inv.setItem(12, KitLayout.getLayout().design(icon, km.getKitByName("flash")));
        }

        {
            Icon icon = new Icon(Material.FEATHER);
            inv.setItem(13, KitLayout.getLayout().design(icon, km.getKitByName("phantom")));
        }

        {
            Icon icon = new Icon(Material.GOLD_AXE);
            inv.setItem(14, KitLayout.getLayout().design(icon, km.getKitByName("thor")));
        }

        {
            Icon icon = new Icon(Material.SPIDER_EYE);
            inv.setItem(15, KitLayout.getLayout().design(icon, km.getKitByName("viper")));
        }

        {
            Icon icon = new Icon(Material.WATCH);
            inv.setItem(16, KitLayout.getLayout().design(icon, km.getKitByName("timelord")));
        }

        {
            Icon icon = new Icon(Material.ENCHANTED_BOOK);
            inv.setItem(17, KitLayout.getLayout().design(icon, km.getKitByName("specialist")));
        }

        {
            Icon icon = new Icon(Material.SLIME_BALL);
            inv.setItem(18, KitLayout.getLayout().design(icon, km.getKitByName("hulk")));
        }

        {
            Icon icon = new Icon(Material.BLAZE_ROD);
            inv.setItem(19, KitLayout.getLayout().design(icon, km.getKitByName("monk")));
        }

        {
            Icon icon = new Icon(Material.PAPER);
            inv.setItem(20, KitLayout.getLayout().design(icon, km.getKitByName("jumpfall")));
        }

        {
            Icon icon = new Icon(Material.MAGMA_CREAM);
            inv.setItem(21, KitLayout.getLayout().design(icon, km.getKitByName("magma")));
        }

        {
            Icon icon = new Icon(Material.SLIME_BLOCK);
            inv.setItem(22, KitLayout.getLayout().design(icon, km.getKitByName("repulsion")));
        }

        {
            Icon icon = new Icon(Material.BLAZE_POWDER);
            inv.setItem(23,KitLayout.getLayout().design(icon, km.getKitByName("fireboost")));
        }

        {
            Icon icon = new Icon(Material.NETHER_FENCE);
            inv.setItem(24,KitLayout.getLayout().design(icon, km.getKitByName("checkpoint")));
        }

        {
            Icon icon = new Icon(Material.SUGAR);
            inv.setItem(25,KitLayout.getLayout().design(icon, km.getKitByName("spectre")));
        }

        {
            Icon icon = new Icon(Material.BARRIER);
            inv.setItem(26, KitLayout.getLayout().design(icon, km.getKitByName("madman")));
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
                    e.setCancelled(true);
                    break;
                case 1: //Kit Archer
                    offerKit(p, "archer");
                    e.getView().close();
                    e.setCancelled(true);
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
                case 5: //Kit Endermage
                    offerKit(p, "endermage");
                    e.getView().close();
                    e.setCancelled(true);
                    break;
                case 6: //Kit Fisherman
                    offerKit(p, "fisherman");
                    e.getView().close();
                    e.setCancelled(true);
                    break;
                case 7: //Kit Switcher
                    offerKit(p, "switcher");
                    e.getView().close();
                    e.setCancelled(true);
                    break;
                case 8: //Kit Kangaroo
                    offerKit(p, "kangaroo");
                    e.getView().close();
                    e.setCancelled(true);
                    break;
                case 9: //Kit Turtle
                    offerKit(p, "turtle");
                    e.getView().close();
                    e.setCancelled(true);
                    break;
                case 10: //Kit Ninja
                    offerKit(p, "ninja");
                    e.getView().close();
                    e.setCancelled(true);
                    break;
                case 11: //Kit Anchor
                    offerKit(p, "anchor");
                    e.getView().close();
                    e.setCancelled(true);
                    break;
                case 12: //Kit Flash
                    offerKit(p, "flash");
                    e.getView().close();
                    e.setCancelled(true);
                    break;
                case 13: //Kit Phantom
                    offerKit(p, "phantom");
                    e.getView().close();
                    e.setCancelled(true);
                    break;
                case 14: //Kit Thor
                    offerKit(p, "thor");
                    e.getView().close();
                    e.setCancelled(true);
                    break;
                case 15: //Kit Viper
                    offerKit(p, "viper");
                    e.getView().close();
                    e.setCancelled(true);
                    break;
                case 16: //Kit TimeLord
                    offerKit(p, "timelord");
                    e.getView().close();
                    e.setCancelled(true);
                    break;
                case 17: //Kit Specialist
                    offerKit(p, "specialist");
                    e.getView().close();
                    e.setCancelled(true);
                    break;
                case 18: //Kit Hulk
                    offerKit(p, "hulk");
                    e.getView().close();
                    e.setCancelled(true);
                    break;
                case 19: //Kit Monk
                    offerKit(p, "monk");
                    e.getView().close();
                    e.setCancelled(true);
                    break;
                case 20: //Kit JumpFall
                    offerKit(p, "jumpfall");
                    e.getView().close();
                    e.setCancelled(true);
                    break;
                case 21: //Kit Magma
                    offerKit(p, "magma");
                    e.getView().close();
                    e.setCancelled(true);
                    break;
                case 22: //Kit Repulsion
                    offerKit(p, "repulsion");
                    e.getView().close();
                    e.setCancelled(true);
                    break;
                case 23: //Kit FireBoost
                    offerKit(p, "fireboost");
                    e.getView().close();
                    e.setCancelled(true);
                    break;
                case 24: //Kit CheckPoint
                    offerKit(p, "checkpoint");
                    e.getView().close();
                    e.setCancelled(true);
                    break;
                case 25: //Kit Spectre
                    offerKit(p, "spectre");
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
