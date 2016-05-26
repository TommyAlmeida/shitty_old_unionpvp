package eu.union.dev.listeners.menus;

import eu.union.dev.api.Icon;
import eu.union.dev.engine.Kit;
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
        setItems(p, "player");
        p.openInventory(Inv.getInstance().kits);
    }

    void setItems(Player p, String type) {
        KitManager km = KitManager.getManager();

        Inventory inv = Inv.getInstance().kits;
        inv.clear();
        int slot = 0;
        {
            ItemStack item = new ItemStack(Material.SKULL_ITEM,1,(short)3);
            SkullMeta sm = (SkullMeta)item.getItemMeta();
            sm.setOwner(p.getName());
            sm.setDisplayName("§aYour Kits");
            item.setItemMeta(sm);
            inv.setItem(slot++, new Icon(Material.CARPET, "§7Back Page").build());
            inv.setItem(slot++, item);
            inv.setItem(slot++, createItem(Material.STAINED_GLASS_PANE,1,(byte)4,"§eAll Kits"));
            inv.setItem(slot++, createItem(Material.STAINED_GLASS_PANE,1,(byte)7,"§7Common Kits"));
            inv.setItem(slot++, createItem(Material.STAINED_GLASS_PANE,1,(byte)3,"§bRare Kits"));
            inv.setItem(slot++, createItem(Material.STAINED_GLASS_PANE,1,(byte)2,"§dEpic Kits"));
            inv.setItem(slot++, createItem(Material.STAINED_GLASS_PANE,1,(byte)1,"§6Heroic Kits"));
            inv.setItem(slot++, createItem(Material.STAINED_GLASS_PANE,1,(byte)10,"§5Beast Kits"));
            inv.setItem(8, new Icon(Material.CARPET, "§aNext Page").build());
        }
        if (type.equalsIgnoreCase("all")){
            for (int i = 0; i < km.getKits().size(); i++) {
                Icon icon = km.getKits().get(i).getIcon();
                inv.setItem(slot++,KitLayout.getLayout().design(icon, km.getKits().get(i)));
            }
        }
        if (type.equalsIgnoreCase("player")){
            for (int i = 0; i < km.getKits().size(); i++) {
                if (p.hasPermission(km.getKits().get(i).getPermission())){
                    Icon icon = km.getKits().get(i).getIcon();
                    inv.setItem(slot++,KitLayout.getLayout().design(icon, km.getKits().get(i)));
                }
            }
        }
        if (type.equalsIgnoreCase("common")){
            for (int i = 0; i < km.getKits().size(); i++) {
                if (km.getKits().get(i).getRarity() == Kit.Rarity.COMMON){
                    Icon icon = km.getKits().get(i).getIcon();
                    inv.setItem(slot++,KitLayout.getLayout().design(icon, km.getKits().get(i)));
                }
            }
        }
        if (type.equalsIgnoreCase("rare")){
            for (int i = 0; i < km.getKits().size(); i++) {
                if (km.getKits().get(i).getRarity() == Kit.Rarity.RARE){
                    Icon icon = km.getKits().get(i).getIcon();
                    inv.setItem(slot++,KitLayout.getLayout().design(icon, km.getKits().get(i)));
                }
            }
        }
        if (type.equalsIgnoreCase("epic")){
            for (int i = 0; i < km.getKits().size(); i++) {
                if (km.getKits().get(i).getRarity() == Kit.Rarity.EPIC){
                    Icon icon = km.getKits().get(i).getIcon();
                    inv.setItem(slot++,KitLayout.getLayout().design(icon, km.getKits().get(i)));
                }
            }
        }
        if (type.equalsIgnoreCase("heroic")){
            for (int i = 0; i < km.getKits().size(); i++) {
                if (km.getKits().get(i).getRarity() == Kit.Rarity.HEROIC){
                    Icon icon = km.getKits().get(i).getIcon();
                    inv.setItem(slot++,KitLayout.getLayout().design(icon, km.getKits().get(i)));
                }
            }
        }
        if (type.equalsIgnoreCase("beast")){
            for (int i = 0; i < km.getKits().size(); i++) {
                if (km.getKits().get(i).getRarity() == Kit.Rarity.BEAST){
                    Icon icon = km.getKits().get(i).getIcon();
                    inv.setItem(slot++,KitLayout.getLayout().design(icon, km.getKits().get(i)));
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
            String kit = item.getItemMeta().getDisplayName().replace("§7","").replace("§b","").replace("§d","").replace("§6","").replace("§5","");
            if (KitManager.getManager().getKitByName(kit) != null){
                offerKit(p, kit);
                e.getView().close();
                e.setCancelled(true);
            }
            switch (e.getSlot()) {
                case 0: //Menu Anterior
                    //Voltar ao menu anterior de kits
                    e.getView().close();
                    e.setCancelled(true);
                    break;
                case 1: //Seus Kits
                    e.getView().close();
                    e.setCancelled(true);
                    setItems(p,"player");
                    break;
                case 2: //Todos os Kits
                    e.getView().close();
                    e.setCancelled(true);
                    setItems(p,"all");
                    break;
                case 3: //Kits Commons
                    e.getView().close();
                    e.setCancelled(true);
                    setItems(p,"common");
                    break;
                case 4: //Kits Rare
                    e.getView().close();
                    e.setCancelled(true);
                    setItems(p,"rare");
                    break;
                case 5: //Kits Epic
                    e.getView().close();
                    e.setCancelled(true);
                    setItems(p,"epic");
                    break;
                case 6: //Kits Heroic
                    e.getView().close();
                    e.setCancelled(true);
                    setItems(p,"heroic");
                    break;
                case 7: //Kits Beast
                    e.getView().close();
                    e.setCancelled(true);
                    setItems(p,"beast");
                    break;
                case 8: //Next Page
                    //Abrir o menu para a proxima pagina
                    e.getView().close();
                    e.setCancelled(true);
                    break;
            }
        }
    }

    public void offerKit(Player p, String kit) {
        p.chat("/kit %kit".replace("%kit", kit.trim()));
    }

    public ItemStack createItem(Material m, int q, byte d, String name){
        ItemStack i = new ItemStack(m,q,d);
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(name);
        i.setItemMeta(im);
        return i;
    }
}
