package eu.union.dev.kits.rare;

import eu.union.dev.api.Ability;
import eu.union.dev.engine.Kit;
import eu.union.dev.engine.managers.KitManager;
import eu.union.dev.utils.Util;
import eu.union.dev.utils.Weapon;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import java.util.concurrent.TimeUnit;

/**
 * Created by Fentis on 16/05/2016.
 */
public class Hulk extends Kit implements Listener{

    public Hulk(){ super("hulk","unkit.hulk",Difficulty.MEDIUM,Rarity.RARE,0);}

    @Override
    public void applyKit(Player player) {
        Weapon.giveWeapon(player, Weapon.DEFAULT_SWORD);
        Weapon.giveWeapon(player, Weapon.HULK_SLIME,1);
    }

    Ability cooldown = new Ability(1, 30, TimeUnit.SECONDS);
    @EventHandler
    public void onclick(PlayerInteractEntityEvent e){
        Player hulk = e.getPlayer();
        if (e.getRightClicked() instanceof Player){
            Player pux = (Player)e.getRightClicked();
            KitManager km = KitManager.getManager();
            if (km.getKitAmIUsing(hulk, "hulk") &&
                    hulk.getItemInHand().getType() == Material.SLIME_BALL){
                if (cooldown.tryUse(hulk)){
                    if (hulk.getPassenger() == null &&
                            hulk.getVehicle() != pux){
                        hulk.setPassenger(pux);
                        pux.sendMessage("§cYou were caught by a Hulk! Sneak out of it!");
                    }
                }else{
                    Util.getInstance().sendCooldownMessage(hulk,cooldown,TimeUnit.SECONDS,true);
                }
            }
        }
    }
    @EventHandler
    public void ondeath(PlayerDeathEvent e){
        e.getEntity().leaveVehicle();
    }
}