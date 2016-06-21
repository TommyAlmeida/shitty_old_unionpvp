package eu.union.dev.kits.heroic;

import eu.union.dev.api.Ability;
import eu.union.dev.api.Icon;
import eu.union.dev.engine.Kit;
import eu.union.dev.engine.managers.KitManager;
import eu.union.dev.utils.globals.Util;
import eu.union.dev.utils.globals.Weapon;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.concurrent.TimeUnit;

public class Stomper extends Kit implements Listener {

    public Stomper() {
        super("stomper", "unkit.stomper", Difficulty.MEDIUM, Rarity.HEROIC, 7, new Icon(Material.IRON_BOOTS), Category.JUMPER, 1000L);
    }

    private Ability cd = new Ability(1, 18, TimeUnit.SECONDS);

    @Override
    public void applyKit(Player player) {
        Weapon.giveWeapon(player, Weapon.DEFAULT_SWORD);
        Weapon.giveWeapon(player, Weapon.STOMPER_JUMP, 1);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerStomp(EntityDamageEvent e) {
        KitManager km = KitManager.getManager();

        if (!(e.getEntity() instanceof Player)) {
            return;
        }

        Player p = (Player) e.getEntity();
        if (e.getCause() == EntityDamageEvent.DamageCause.FALL) {
            if (km.getKitAmIUsing(p, "stomper")) {
                if (e.getDamage() >=6.0D){
                    for (Entity ent : p.getNearbyEntities(5.0D,2.0D,5.0D)){
                        if (ent instanceof Player){
                            Player plr = (Player)ent;
                            if (Util.getInstance().inPvP(plr)){
                                double damage = e.getDamage();
                                if (plr.isSneaking()){//Se estive no shift
                                    plr.damage(6.0, p);//Leva dano baixo 3 corações
                                    plr.sendMessage(prefix + " §7You were stomped by: §b" + p.getName());
                                }else{
                                    plr.damage(damage,p);//O kit Somper da o dano q ele tomou para o plr
                                    plr.sendMessage(prefix + " §7You were stomped by: §b" + p.getName());
                                }
                            }
                        }
                    }
                    e.setDamage(4.0);
                }
            }
            return;
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        Player p = e.getPlayer();
        ItemStack item = p.getItemInHand();
        KitManager km = KitManager.getManager();

        if(km.getKitAmIUsing(p, "stomper")){
            if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK){
                if(item.getItemMeta().getDisplayName() == "§bStomper Jump" && item.getType() == Material.FEATHER){
                    if(cd.tryUse(p)){
                        p.setVelocity(new Vector(0,p.getLocation().getY() + 7, 0));
                    }else{
                       Util.getInstance().sendCooldownMessage(p, cd, TimeUnit.SECONDS, true);
                    }
                }
            }
        }
    }
}
