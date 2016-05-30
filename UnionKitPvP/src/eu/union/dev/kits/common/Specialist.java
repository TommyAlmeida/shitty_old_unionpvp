package eu.union.dev.kits.common;

import eu.union.dev.api.Icon;
import eu.union.dev.engine.Kit;
import eu.union.dev.engine.managers.KitManager;
import eu.union.dev.utils.globals.Perms;
import eu.union.dev.utils.globals.Weapon;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class Specialist extends Kit implements Listener {

    public Specialist() {
        super("specialist", Perms.KIT_FREE.toString(), Difficulty.LOW,
                Rarity.COMMON, 0, new Icon(Material.ENCHANTED_BOOK), Category.SOCIAL);
    }

    @Override
    public void applyKit(Player player) {
        Weapon.giveWeapon(player, Weapon.DEFAULT_SWORD);
        Weapon.giveWeapon(player, Weapon.SPECIALIST_BOOK, 1);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        KitManager km = KitManager.getManager();

        if (km.getKitAmIUsing(p, "specialist")) {
            if (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR && p.getItemInHand().getType() == Material.ENCHANTED_BOOK) {
                p.openEnchanting(p.getLocation(), true);
            }
        }
    }

    @EventHandler
    public void onKill(EntityDeathEvent e) {
        KitManager km = KitManager.getManager();

        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();

            if (p.getKiller() instanceof Player) {
                Player p2 = e.getEntity().getKiller();
                if (km.getKitAmIUsing(p, "specialist")) {
                    p2.setLevel(p2.getLevel() + 1);
                    p2.getInventory().addItem(new ItemStack(Material.INK_SACK, 1, (short) 4));
                }
            }
        }
    }


}
