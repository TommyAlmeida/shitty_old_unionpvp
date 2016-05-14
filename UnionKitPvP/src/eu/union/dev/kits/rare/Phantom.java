package eu.union.dev.kits.rare;

import eu.union.dev.PvPMain;
import eu.union.dev.engine.Kit;
import eu.union.dev.engine.managers.KitManager;
import eu.union.dev.utils.Weapon;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.ArrayList;

/**
 * Created by Fentis on 14/05/2016.
 */
public class Phantom extends Kit implements Listener{

    public Phantom(){ super("phantom", "unkit.phantom", Difficulty.LOW, Rarity.RARE, 0); };

    @Override
    public void applyKit(Player player) {
        Weapon.giveWeapon(player, Weapon.DEFAULT_SWORD);
        Weapon.giveWeapon(player, Weapon.PHANTOM_FEATHER);
    }

    ArrayList<String> cd = new ArrayList<>();
    @SuppressWarnings("deprecation")
    @EventHandler
    public void onclickinv(InventoryClickEvent e) {
        KitManager km = KitManager.getManager();
        if (km.getKitAmIUsing((Player)e.getWhoClicked(), "phantom") &&
                e.getCurrentItem() != null &&
                e.getCurrentItem().getTypeId() != 0 &&
                e.getCurrentItem().getType().name().contains("LEATHER_")){
            e.setCancelled(true);
        }
    }
    @SuppressWarnings("deprecation")
    @EventHandler
    public void onclick(PlayerInteractEvent e)
    {
        final Player p = e.getPlayer();
        KitManager km = KitManager.getManager();
        if (p.getItemInHand().getType() == Material.FEATHER &&
                km.getKitAmIUsing(p, "phantom")){
            if (e.getAction() == Action.RIGHT_CLICK_AIR ||
                    e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (cd.contains(p.getName())) {
                    p.sendMessage("§cYou are in cooldown!");
                } else {
                    p.playSound(p.getLocation(), Sound.FIREWORK_LAUNCH, 1.0F, 1.0F);
                    p.setAllowFlight(true);
                    p.setFlying(true);
                    final ItemStack[] items = p.getInventory().getArmorContents();
                    p.getInventory().setHelmet(addcolor(Material.LEATHER_HELMET));
                    p.getInventory().setChestplate(addcolor(Material.LEATHER_CHESTPLATE));
                    p.getInventory().setLeggings(addcolor(Material.LEATHER_LEGGINGS));
                    p.getInventory().setBoots(addcolor(Material.LEATHER_BOOTS));
                    p.sendMessage("§aYou become a ghost and can fly 5s!");
                    p.updateInventory();
                    cd.add(p.getName());
                    Bukkit.getScheduler().runTaskLater(PvPMain.getInstance(), new Runnable()
                            {
                                public void run() {
                                    p.setAllowFlight(false);
                                    p.setFlying(false);
                                    p.getInventory().setArmorContents(items);
                                    p.sendMessage("§bYou came back to life and you can not fly!");
                                }
                            }
                            , 5*20);
                    Bukkit.getScheduler().runTaskLater(PvPMain.getInstance(), new Runnable()
                            {
                                public void run() {
                                    cd.remove(p.getName());
                                    p.sendMessage("§aThe cooldown is over!");
                                }
                            }
                            , 30*20L);
                }
            }
        }
    }
    ItemStack addcolor(Material m)
    {
        ItemStack i = new ItemStack(m);
        LeatherArmorMeta im = (LeatherArmorMeta) i.getItemMeta();
        im.setColor(Color.WHITE);
        i.setItemMeta(im);
        return i;
    }
}
