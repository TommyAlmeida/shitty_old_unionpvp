package eu.union.dev.listeners.menus;

import eu.union.dev.api.Icon;
import eu.union.dev.engine.Kit;
import eu.union.dev.engine.layouts.KitLayout;
import eu.union.dev.engine.managers.KitManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

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
        Inventory inv = Bukkit.createInventory(null, 6 * 9, "Kits");
        setItems(p, inv, "player", 1);
        p.openInventory(inv);
    }

    void setItems(Player p, Inventory inv, String type, int page) {
        KitManager km = KitManager.getManager();

        inv.clear();
        int slot = 0;
        int nextpage = page + 1;
        int backpage = page - 1;
        {
            ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
            SkullMeta sm = (SkullMeta) item.getItemMeta();
            sm.setOwner(p.getName());
            sm.setDisplayName("§aYour Kits");
            item.setItemMeta(sm);
            inv.setItem(slot++, new Icon(Material.CARPET, "§7Back Page").build());
            inv.setItem(slot++, item);
            inv.setItem(slot++, createItem(Material.STAINED_GLASS_PANE, 1, (byte) 4, "§eAll Kits"));
            inv.setItem(slot++, createItem(Material.STAINED_GLASS_PANE, 1, (byte) 7, "§7Common Kits"));
            inv.setItem(slot++, createItem(Material.STAINED_GLASS_PANE, 1, (byte) 3, "§bRare Kits"));
            inv.setItem(slot++, createItem(Material.STAINED_GLASS_PANE, 1, (byte) 2, "§dEpic Kits"));
            inv.setItem(slot++, createItem(Material.STAINED_GLASS_PANE, 1, (byte) 1, "§6Heroic Kits"));
            inv.setItem(slot++, createItem(Material.STAINED_GLASS_PANE, 1, (byte) 10, "§5Beast Kits"));
        }
        if (type.contains("all")) {
            inv.setItem(slot++, new Icon(Material.CARPET, "§aNext Page", "§5All Kits §6" + nextpage).build());
            inv.setItem(0, new Icon(Material.CARPET, "§7Back Page", "§5All Kits §6" + backpage).build());
            int kits = 0;
            for (int i = 0; i < km.getKits().size(); i++) {
                if (kits <= 44 && page == 1) {
                    Icon icon = km.getKits().get(i).getIcon();
                    inv.setItem(slot++, KitLayout.getLayout().design(icon, km.getKits().get(i)));
                }
                if (kits >= 45 && page == 2) {
                    Icon icon = km.getKits().get(i).getIcon();
                    inv.setItem(slot++, KitLayout.getLayout().design(icon, km.getKits().get(i)));
                }
                kits++;
            }
        }
        if (type.contains("player")) {
            inv.setItem(slot++, new Icon(Material.CARPET, "§aNext Page", "§5 §6" + nextpage).build());
            inv.setItem(0, new Icon(Material.CARPET, "§7Back Page", "§5Your Kits §6" + backpage).build());
            int kits = 0;
            for (int i = 0; i < km.getKits().size(); i++) {
                if (p.hasPermission(km.getKits().get(i).getPermission())) {
                    if (kits <= 44 && page == 1) {
                        Icon icon = km.getKits().get(i).getIcon();
                        inv.setItem(slot++, KitLayout.getLayout().design(icon, km.getKits().get(i)));
                    }
                    if (kits >= 45 && page == 2) {
                        Icon icon = km.getKits().get(i).getIcon();
                        inv.setItem(slot++, KitLayout.getLayout().design(icon, km.getKits().get(i)));
                    }
                    kits++;
                }
            }
        }
        if (type.contains("common")) {
            inv.setItem(slot++, new Icon(Material.CARPET, "§aNext Page", "§5Common Kits §6" + nextpage).build());
            inv.setItem(0, new Icon(Material.CARPET, "§7Back Page", "§5Common Kits §6" + backpage).build());
            int kits = 0;
            for (int i = 0; i < km.getKits().size(); i++) {
                if (km.getKits().get(i).getRarity() == Kit.Rarity.COMMON) {
                    if (kits <= 44 && page == 1) {
                        Icon icon = km.getKits().get(i).getIcon();
                        inv.setItem(slot++, KitLayout.getLayout().design(icon, km.getKits().get(i)));
                    }
                    if (kits >= 45 && page == 2) {
                        Icon icon = km.getKits().get(i).getIcon();
                        inv.setItem(slot++, KitLayout.getLayout().design(icon, km.getKits().get(i)));
                    }
                    kits++;
                }
            }
        }
        if (type.contains("rare")) {
            inv.setItem(slot++, new Icon(Material.CARPET, "§aNext Page", "§5Rare Kits §6" + nextpage).build());
            inv.setItem(0, new Icon(Material.CARPET, "§7Back Page", "§5Rare Kits §6" + backpage).build());
            int kits = 0;
            for (int i = 0; i < km.getKits().size(); i++) {
                if (km.getKits().get(i).getRarity() == Kit.Rarity.RARE) {
                    if (kits <= 44 && page == 1) {
                        Icon icon = km.getKits().get(i).getIcon();
                        inv.setItem(slot++, KitLayout.getLayout().design(icon, km.getKits().get(i)));
                    }
                    if (kits >= 45 && page == 2) {
                        Icon icon = km.getKits().get(i).getIcon();
                        inv.setItem(slot++, KitLayout.getLayout().design(icon, km.getKits().get(i)));
                    }
                    kits++;
                }
            }
        }
        if (type.contains("epic")) {
            inv.setItem(slot++, new Icon(Material.CARPET, "§aNext Page", "§5Epic Kits §6" + nextpage).build());
            inv.setItem(0, new Icon(Material.CARPET, "§7Back Page", "§5Epic Kits §6" + backpage).build());
            int kits = 0;
            for (int i = 0; i < km.getKits().size(); i++) {
                if (km.getKits().get(i).getRarity() == Kit.Rarity.EPIC) {
                    if (kits <= 44 && page == 1) {
                        Icon icon = km.getKits().get(i).getIcon();
                        inv.setItem(slot++, KitLayout.getLayout().design(icon, km.getKits().get(i)));
                    }
                    if (kits >= 45 && page == 2) {
                        Icon icon = km.getKits().get(i).getIcon();
                        inv.setItem(slot++, KitLayout.getLayout().design(icon, km.getKits().get(i)));
                    }
                    kits++;
                }
            }
        }
        if (type.contains("heroic")) {
            inv.setItem(slot++, new Icon(Material.CARPET, "§aNext Page", "§5Heroic Kits §6" + nextpage).build());
            inv.setItem(0, new Icon(Material.CARPET, "§7Back Page", "§5Heroic Kits §6" + backpage).build());
            int kits = 0;
            for (int i = 0; i < km.getKits().size(); i++) {
                if (km.getKits().get(i).getRarity() == Kit.Rarity.HEROIC) {
                    if (kits <= 44 && page == 1) {
                        Icon icon = km.getKits().get(i).getIcon();
                        inv.setItem(slot++, KitLayout.getLayout().design(icon, km.getKits().get(i)));
                    }
                    if (kits >= 45 && page == 2) {
                        Icon icon = km.getKits().get(i).getIcon();
                        inv.setItem(slot++, KitLayout.getLayout().design(icon, km.getKits().get(i)));
                    }
                    kits++;
                }
            }
        }
        if (type.contains("beast")) {
            inv.setItem(slot++, new Icon(Material.CARPET, "§aNext Page", "§5Beast Kits §6" + nextpage).build());
            inv.setItem(0, new Icon(Material.CARPET, "§7Back Page", "§5Beast Kits §6" + backpage).build());
            int kits = 0;
            for (int i = 0; i < km.getKits().size(); i++) {
                if (km.getKits().get(i).getRarity() == Kit.Rarity.BEAST) {
                    if (kits <= 44 && page == 1) {
                        Icon icon = km.getKits().get(i).getIcon();
                        inv.setItem(slot++, KitLayout.getLayout().design(icon, km.getKits().get(i)));
                    }
                    if (kits >= 45 && page == 2) {
                        Icon icon = km.getKits().get(i).getIcon();
                        inv.setItem(slot++, KitLayout.getLayout().design(icon, km.getKits().get(i)));
                    }
                    kits++;
                }
            }
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

            String kit = item.getItemMeta().getDisplayName().replace("§7Kit » ", "").replace("§7", "").replace("§b", "").replace("§d", "").replace("§6", "").replace("§5", "");

            if (KitManager.getManager().getKitByName(kit) != null) {
                offerKit(p, kit);
                e.getView().close();
                e.setCancelled(true);
            }

            switch (e.getSlot()) {
                case 0: //Menu Anterior
                    String nome = item.getItemMeta().getLore().get(0);
                    if (nome.contains("§5All Kits §6")) {
                        int page = Integer.parseInt(nome.replace("§5All Kits §6", ""));
                        if (page >= 1) {
                            setItems(p, e.getClickedInventory(), "all", page);
                        }
                    }
                    if (nome.contains("§5Your Kits §6")) {
                        int page = Integer.parseInt(nome.replace("§5Your Kits §6", ""));
                        if (page >= 1) {
                            setItems(p, e.getClickedInventory(), "player", page);
                        }
                    }
                    if (nome.contains("§5Common Kits §6")) {
                        int page = Integer.parseInt(nome.replace("§5Common Kits §6", ""));
                        if (page >= 1) {
                            setItems(p, e.getClickedInventory(), "common", page);
                        }
                    }
                    if (nome.contains("§5Rare Kits §6")) {
                        int page = Integer.parseInt(nome.replace("§5Rare Kits §6", ""));
                        if (page >= 1) {
                            setItems(p, e.getClickedInventory(), "rare", page);
                        }
                    }
                    if (nome.contains("§5Epic Kits §6")) {
                        int page = Integer.parseInt(nome.replace("§5Epic Kits §6", ""));
                        if (page >= 1) {
                            setItems(p, e.getClickedInventory(), "epic", page);
                        }
                    }
                    if (nome.contains("§5Heroic Kits §6")) {
                        int page = Integer.parseInt(nome.replace("§5Heroic Kits §6", ""));
                        if (page >= 1) {
                            setItems(p, e.getClickedInventory(), "heroic", page);
                        }
                    }
                    if (nome.contains("§5Beast Kits §6")) {
                        int page = Integer.parseInt(nome.replace("§5Beast Kits §6", ""));
                        if (page >= 1) {
                            setItems(p, e.getClickedInventory(), "beast", page);
                        }
                    }
                    e.setCancelled(true);
                    break;
                case 1: //Seus Kits
                    e.setCancelled(true);
                    setItems(p, e.getClickedInventory(), "player", 1);
                    break;
                case 2: //Todos os Kits
                    e.setCancelled(true);
                    setItems(p, e.getClickedInventory(), "all", 1);
                    break;
                case 3: //Kits Commons
                    e.setCancelled(true);
                    setItems(p, e.getClickedInventory(), "common", 1);
                    break;
                case 4: //Kits Rare
                    e.setCancelled(true);
                    setItems(p, e.getClickedInventory(), "rare", 1);
                    break;
                case 5: //Kits Epic
                    e.setCancelled(true);
                    setItems(p, e.getClickedInventory(), "epic", 1);
                    break;
                case 6: //Kits Heroic
                    e.setCancelled(true);
                    setItems(p, e.getClickedInventory(), "heroic", 1);
                    break;
                case 7: //Kits Beast
                    e.setCancelled(true);
                    setItems(p, e.getClickedInventory(), "beast", 1);
                    break;
                case 8: //Next Page
                    String name = item.getItemMeta().getLore().get(0);
                    if (name.contains("§5All Kits §6")) {
                        int page = Integer.parseInt(name.replace("§5All Kits §6", ""));
                        if (page <= 2) {
                            setItems(p, e.getClickedInventory(), "all", page);
                        }
                    }
                    if (name.contains("§5Your Kits §6")) {
                        int page = Integer.parseInt(name.replace("§5Your Kits §6", ""));
                        if (page <= 2) {
                            setItems(p, e.getClickedInventory(), "player", page);
                        }
                    }
                    if (name.contains("§5Common Kits §6")) {
                        int page = Integer.parseInt(name.replace("§5Common Kits §6", ""));
                        if (page <= 2) {
                            setItems(p, e.getClickedInventory(), "common", page);
                        }
                    }
                    if (name.contains("§5Rare Kits §6")) {
                        int page = Integer.parseInt(name.replace("§5Rare Kits §6", ""));
                        if (page <= 2) {
                            setItems(p, e.getClickedInventory(), "rare", page);
                        }
                    }
                    if (name.contains("§5Epic Kits §6")) {
                        int page = Integer.parseInt(name.replace("§5Epic Kits §6", ""));
                        if (page <= 2) {
                            setItems(p, e.getClickedInventory(), "epic", page);
                        }
                    }
                    if (name.contains("§5Heroic Kits §6")) {
                        int page = Integer.parseInt(name.replace("§5Heroic Kits §6", ""));
                        if (page <= 2) {
                            setItems(p, e.getClickedInventory(), "heroic", page);
                        }
                    }
                    if (name.contains("§5Beast Kits §6")) {
                        int page = Integer.parseInt(name.replace("§5Beast Kits §6", ""));
                        if (page <= 2) {
                            setItems(p, e.getClickedInventory(), "beast", page);
                        }
                    }
                    e.setCancelled(true);
                    break;
            }
        }
    }

    public void offerKit(Player p, String kit) {
        p.chat("/kit %kit".replace("%kit", kit.trim()));
    }

    public ItemStack createItem(Material m, int q, byte d, String name) {
        ItemStack i = new ItemStack(m, q, d);
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(name);
        i.setItemMeta(im);
        return i;
    }
}
