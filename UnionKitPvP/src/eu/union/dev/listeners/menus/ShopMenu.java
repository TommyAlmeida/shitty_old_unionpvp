package eu.union.dev.listeners.menus;

import eu.union.dev.api.Icon;
import eu.union.dev.engine.KPlayer;
import eu.union.dev.engine.layouts.KitLayout;
import eu.union.dev.engine.managers.KitManager;
import eu.union.dev.engine.managers.PlayerManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fentis on 03/06/2016.
 */
public class ShopMenu implements Listener{

    public void setItems(Player p, Inventory inv, int page){
        inv.clear();
        KitManager km = KitManager.getManager();
        int nextpage = page+1;
        int backpage = page-1;
        if (backpage == 0){
            backpage =1;
        }
        int slot = 0;
        {
            ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
            SkullMeta sm = (SkullMeta) item.getItemMeta();
            sm.setOwner(p.getName());
            sm.setDisplayName("§bYour Shop");
            item.setItemMeta(sm);
            inv.setItem(slot++, createItem(Material.CARPET, 1, (byte)0,"§7Back Page",desc("§5Page §d"+backpage)));
            inv.setItem(slot++, createItem(Material.STAINED_GLASS_PANE, 1, (byte)5,"§7"));
            inv.setItem(slot++, createItem(Material.STAINED_GLASS_PANE, 1, (byte)5,"§7"));
            inv.setItem(slot++, createItem(Material.STAINED_GLASS_PANE, 1, (byte)5,"§7"));
            inv.setItem(slot++, item);
            inv.setItem(slot++, createItem(Material.STAINED_GLASS_PANE, 1, (byte)5,"§7"));
            inv.setItem(slot++, createItem(Material.STAINED_GLASS_PANE, 1, (byte)5,"§7"));
            inv.setItem(slot++, createItem(Material.STAINED_GLASS_PANE, 1, (byte)5,"§7"));
            inv.setItem(slot++, createItem(Material.CARPET, 1, (byte)5,"§aNext Page",desc("§5Page §d"+nextpage)));
        }
        int kits = 0;
        for (int i = 0; i < km.getKits().size(); i++) {
            if (!p.hasPermission(km.getKits().get(i).getPermission())){
                if (kits <= 44 && page == 1) {
                    Icon icon = km.getKits().get(i).getIcon();
                    inv.setItem(slot++, createItem(KitLayout.getLayout().design(icon, km.getKits().get(i)),desc("§5Buy for:§6 "+km.getKits().get(i).getPrice())));
                }
                if (kits >= 45 && page == 2) {
                    Icon icon = km.getKits().get(i).getIcon();
                    inv.setItem(slot++, createItem(KitLayout.getLayout().design(icon, km.getKits().get(i)),desc("§5Buy for:§6 "+km.getKits().get(i).getPrice())));
                }
                kits++;
            }
        }
    }

    @EventHandler
    public void onInvClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        ItemStack item = e.getCurrentItem();
        if (e.getInventory().getName().equalsIgnoreCase("Shop")) {
            if (e.getSlot() < 0) {
                return;
            }

            String kit = item.getItemMeta().getDisplayName().replace("§7Kit » ", "").replace("§7", "").replace("§b", "").replace("§d", "").replace("§6", "").replace("§5", "");
            KitManager km = KitManager.getManager();
            if (km.getKitByName(kit) != null) {
                KPlayer kplayer = PlayerManager.getPlayer(p.getUniqueId());
                if (kplayer.getCoins() >= km.getKitByName(kit).getPrice()){
                    if (kplayer.getCoins()-km.getKitByName(kit).getPrice() == 0L){
                        kplayer.setCoins(0L);
                    }else{
                        kplayer.setCoins(kplayer.getCoins()-km.getKitByName(kit).getPrice());
                    }
                    PermissionsEx.getUser(p).addPermission(km.getKitByName(kit).getPermission());
                    p.sendMessage("§aCongratulations! You now got the " + kit + " kit!");
                    e.getView().close();
                }else{
                    p.sendMessage("§4You do not have enough §6Coins§4!");
                }
                e.setCancelled(true);
            }
            if (e.getSlot() == 0 || e.getSlot() == 8){
                String page = item.getItemMeta().getLore().get(0).replace("§5Page §d","");
                setItems(p,e.getClickedInventory(),Integer.parseInt(page));
            }
            if (e.getSlot() <=8){
                e.setCancelled(true);
            }
        }
    }

    public ItemStack createItem(Material m, int q, byte d, String name) {
        ItemStack i = new ItemStack(m, q, d);
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(name);
        i.setItemMeta(im);
        return i;
    }

    public ItemStack createItem(Material m, int q, byte d, String name, List<String> desc) {
        ItemStack i = new ItemStack(m, q, d);
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(name);
        im.setLore(desc);
        i.setItemMeta(im);
        return i;
    }

    public ItemStack createItem(ItemStack i, List<String> desc){
        ItemMeta im = i.getItemMeta();
        im.setLore(desc);
        i.setItemMeta(im);
        return i;
    }

    public List<String> desc(String... list){
        List<String> lista = new ArrayList<>();
        for (int i = 0; i < list.length; i++) {
            lista.add(list[i]);
        }
        return lista;
    }
}
