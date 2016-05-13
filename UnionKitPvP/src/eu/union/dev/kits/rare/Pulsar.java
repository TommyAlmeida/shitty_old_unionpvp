package eu.union.dev.kits.rare;

import eu.union.dev.api.Ability;
import eu.union.dev.api.Packets;
import eu.union.dev.engine.Kit;
import eu.union.dev.engine.managers.KitManager;
import eu.union.dev.utils.Messages;
import eu.union.dev.utils.Util;
import eu.union.dev.utils.Weapon;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.concurrent.TimeUnit;

public class Pulsar extends Kit implements Listener{


    public Pulsar() {
        super("pulsar", "unkit.pulsar", Difficulty.MEDIUM, Rarity.BEAST, 0);
    }

    public Ability cooldown = new Ability(1,13, TimeUnit.SECONDS);

    @Override
    public void applyKit(Player player) {
        Weapon.giveWeapon(player, Weapon.PULSAR_SHOCK,1);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        Player p = event.getPlayer();
        ItemStack item = p.getItemInHand();

        if (item == null) {
            return;
        }

        if (!(item.getType() == Material.MAGMA_CREAM)) {
            return;
        }

        if (item.getItemMeta() == null) {
            return;
        }

        if (!(item.getItemMeta().hasDisplayName())) {
            return;
        }

        if (!(item.getItemMeta().getDisplayName() == Weapon.PULSAR_SHOCK.getName())) {
            return;
        }

        if(cooldown.tryUse(p)){
            for(Entity e : p.getNearbyEntities(5,5,5)){
                if(e instanceof Player){
                    e.setVelocity(new Vector(0, 5, 0));
                    e.getWorld().strikeLightning(e.getLocation());
                    e.sendMessage(prefix + " §7Shi**, you have been pulsed and lifted by: §e" + p.getDisplayName());
                }
            }
        }else{
            Util.sendCooldownMessage(p, cooldown, TimeUnit.SECONDS, true);
        }
    }

}