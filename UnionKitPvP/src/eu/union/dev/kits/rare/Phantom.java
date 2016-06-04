package eu.union.dev.kits.rare;

import eu.union.dev.PvPMain;
import eu.union.dev.api.Ability;
import eu.union.dev.api.Icon;
import eu.union.dev.engine.Kit;
import eu.union.dev.engine.managers.KitManager;
import eu.union.dev.utils.globals.Util;
import eu.union.dev.utils.globals.Weapon;
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

import java.util.concurrent.TimeUnit;

/**
 * Created by Fentis on 14/05/2016.
 */
public class Phantom extends Kit implements Listener {

    Ability cooldown = new Ability(1, 30, TimeUnit.SECONDS);

    ;

    public Phantom() {
        super("phantom", "unkit.phantom", Difficulty.LOW, Rarity.RARE, 7, new Icon(Material.FEATHER), Category.POTION, 1000L);
    }

    @Override
    public void applyKit(Player player) {
        Weapon.giveWeapon(player, Weapon.DEFAULT_SWORD);
        Weapon.giveWeapon(player, Weapon.PHANTOM_FEATHER, 1);
    }

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onclickinv(InventoryClickEvent e) {
        KitManager km = KitManager.getManager();
        if (km.getKitAmIUsing((Player) e.getWhoClicked(), "phantom") &&
                e.getCurrentItem() != null &&
                e.getCurrentItem().getTypeId() != 0 &&
                e.getCurrentItem().getType().name().contains("LEATHER_")) {
            e.setCancelled(true);
        }
    }

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onclick(PlayerInteractEvent e) {
        final Player p = e.getPlayer();
        KitManager km = KitManager.getManager();
        if (p.getItemInHand().getType() == Material.FEATHER &&
                km.getKitAmIUsing(p, "phantom")) {
            if (e.getAction() == Action.RIGHT_CLICK_AIR ||
                    e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (!cooldown.tryUse(p)) {
                    Util.getInstance().sendCooldownMessage(p, cooldown, TimeUnit.SECONDS, true);
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
                    Bukkit.getScheduler().runTaskLater(PvPMain.getInstance(), new Runnable() {
                                public void run() {
                                    p.setAllowFlight(false);
                                    p.setFlying(false);
                                    p.getInventory().setArmorContents(items);
                                    p.sendMessage("§bYou came back to life and you can not fly!");
                                }
                            }
                            , 5 * 20);
                }
            }
        }
    }

    ItemStack addcolor(Material m) {
        ItemStack i = new ItemStack(m);
        LeatherArmorMeta im = (LeatherArmorMeta) i.getItemMeta();
        im.setColor(Color.WHITE);
        i.setItemMeta(im);
        return i;
    }
}
