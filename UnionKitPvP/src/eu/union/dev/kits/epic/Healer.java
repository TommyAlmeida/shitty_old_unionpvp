package eu.union.dev.kits.epic;

import com.sun.corba.se.impl.javax.rmi.CORBA.Util;
import eu.union.dev.PvPMain;
import eu.union.dev.api.Ability;
import eu.union.dev.api.Icon;
import eu.union.dev.engine.Kit;
import eu.union.dev.utils.ParticleEffect;
import eu.union.dev.utils.globals.Weapon;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.concurrent.TimeUnit;

public class Healer extends Kit implements Listener {

    public Healer() {
        super("healer", "unkit.healer", Difficulty.LOW, Rarity.EPIC, 2, new Icon(Material.RED_ROSE),  Category.SOCIAL, 450);
    }

    private Ability cooldown = new Ability(0, 17, TimeUnit.SECONDS);

    @Override
    public void applyKit(Player player) {
        Weapon.giveWeapon(player, Weapon.DEFAULT_SWORD);
        Weapon.giveWeapon(player, Weapon.HEALER_ITEM, 1);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        Player p = e.getPlayer();
        ItemStack item = p.getItemInHand();

        if(item == null){
            return;
        }

        if(item.getItemMeta() == null){
            return;
        }

        if(!item.getItemMeta().hasDisplayName()){
            return;
        }

        if(item.getItemMeta().getDisplayName() == "§aHealer Item"){
            if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK){
                if(cooldown.tryUse(p)){
                    p.setHealth(p.getHealth() * 2.0D);
                    p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,20*15, 1));
                }else{
                    eu.union.dev.utils.globals.Util.getInstance().sendCooldownMessage(p,cooldown,TimeUnit.SECONDS,true);
                }
            }
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e){
        Bukkit.getScheduler().scheduleSyncRepeatingTask(PvPMain.getInstance(), new Runnable() {
            @Override
            public void run() {
                circleParticle(e.getPlayer());
            }
        },20,20);
    }

    private void circleParticle(Player player){
        Location loc = player.getLocation();

        double pi = Math.PI;
        float raio = 0.7f;
        float speed = 2;

        for(int i = 0; i < 50; i++){
            double angle, x, z, y;

            angle = 2 * pi * i / 50;
            x = Math.cos(angle) * raio;
            z = Math.sin(angle) * raio;
            y = 2;

            loc.add(x, 2,z);
            ParticleEffect.FLAME.display((float) x, (float) y, (float) x, speed, 1, loc, 2);
        }
    }
}
