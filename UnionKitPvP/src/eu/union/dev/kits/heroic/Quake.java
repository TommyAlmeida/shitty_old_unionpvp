package eu.union.dev.kits.heroic;

import eu.union.dev.api.Ability;
import eu.union.dev.api.Icon;
import eu.union.dev.api.Packets;
import eu.union.dev.engine.Kit;
import eu.union.dev.engine.managers.KitManager;
import eu.union.dev.utils.ParticleEffect;
import eu.union.dev.utils.globals.Weapon;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftFirework;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.util.Vector;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by Fentis on 30/05/2016.
 */
public class Quake extends Kit implements Listener{

    public Quake() {
        super("quake", "unkit.quake", Difficulty.PRO, Rarity.HEROIC, 3, new Icon(Material.IRON_HOE), Category.LONG_DISTANCE);
    }

    @Override
    public void applyKit(Player player) {
        Weapon.giveWeapon(player, Weapon.DEFAULT_SWORD);
        Weapon.giveWeapon(player, Weapon.QUAKE_GUN,1);
    }
    Ability cooldown = new Ability(1,3, TimeUnit.SECONDS);
    @EventHandler
    public void onclick(PlayerInteractEvent e){
        Player p = e.getPlayer();
        KitManager km = KitManager.getManager();
        if (p.getItemInHand().getType() == Material.IRON_HOE &&
                (e.getAction() == Action.RIGHT_CLICK_AIR ||
                e.getAction() == Action.RIGHT_CLICK_BLOCK) &&
                km.getKitAmIUsing(p,"quake")){
            e.setCancelled(true);
            if (cooldown.tryUse(p)){
                double t = 0;
                boolean status = false;
                ArrayList<UUID> uuid = new ArrayList<>();
                for (int i = 0; i<=100; i++){
                    t = t + 0.5;
                    Location loc = p.getLocation();
                    Vector direction = loc.getDirection().normalize();
                    double x = direction.getX() * t;
                    double y = direction.getY() * t+1.5;
                    double z = direction.getZ() * t;
                    loc.add(x,y,z);
                    ParticleEffect.FIREWORKS_SPARK.display(0, 0, 0, 1, 0, loc, 1000);
                    if (loc.getBlock().getType() != Material.AIR){
                        if (!status){
                            FireworkEffect ef = FireworkEffect.builder().flicker(false).withColor(Color.YELLOW).withFade(Color.ORANGE).build();
                            try {
                                playFirework(loc.getWorld(), loc, ef);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                        i = 100;
                    }
                    for (Entity en : loc.getChunk().getEntities()){
                        if (en.getLocation().distance(loc) <=2.0){
                            if (en instanceof Player && !uuid.contains(en.getUniqueId()) && en != p){
                                status = true;
                                uuid.add(en.getUniqueId());
                                FireworkEffect ef = FireworkEffect.builder().flicker(false).withColor(Color.YELLOW).withFade(Color.ORANGE).build();
                                try {
                                    playFirework(en.getWorld(), en.getLocation(), ef);
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                                ((Player)en).damage(8.0, p);
                            }
                        }
                    }
                    loc.subtract(x,y,z);
                }
            }else{
                Packets.getAPI().sendActionBar(p,"§aRELOADING...");
            }
        }
    }
    private Method world_getHandle = null;
    private Method nms_world_broadcastEntityEffect = null;
    private Method firework_getHandle = null;
    public void playFirework(World world, Location loc, FireworkEffect fe) throws Exception {
        Firework fw = (Firework) world.spawn(loc, Firework.class);
        Object nms_world = null;
        Object nms_firework = null;
        if(world_getHandle == null) {
            world_getHandle = getMethod(world.getClass(), "getHandle");
            firework_getHandle = getMethod(fw.getClass(), "getHandle");
        }
        nms_world = world_getHandle.invoke(world, (Object[]) null);
        nms_firework = firework_getHandle.invoke(fw, (Object[]) null);
        if(nms_world_broadcastEntityEffect == null) {
            nms_world_broadcastEntityEffect = getMethod(nms_world.getClass(), "broadcastEntityEffect");
        }
        FireworkMeta data = (FireworkMeta) fw.getFireworkMeta();
        // clear existing
        data.clearEffects();
        // power of one
        data.setPower(1);
        // add the effect
        data.addEffect(fe);
        // set the meta
        fw.setFireworkMeta(data);
        nms_world_broadcastEntityEffect.invoke(nms_world, new Object[] {nms_firework, (byte) 17});
        ((CraftFirework)fw).getHandle().expectedLifespan = 2;
    }
    private Method getMethod(Class<?> cl, String method) {
        for(Method m : cl.getMethods()) {if(m.getName().equals(method)){return m;}}return null;
    }
}
