package eu.union.dev.kits.beast;

import eu.union.dev.PvPMain;
import eu.union.dev.api.Icon;
import eu.union.dev.engine.Kit;
import eu.union.dev.engine.managers.KitManager;
import eu.union.dev.utils.globals.Messages;
import eu.union.dev.utils.globals.Util;
import eu.union.dev.utils.globals.Weapon;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Fentis on 29/06/2016.
 */
public class Gladiator extends Kit implements Listener{

    public Gladiator() {
        super("gladiator", "unkit.gladiator", Difficulty.MEDIUM, Rarity.BEAST, 8, new Icon(Material.IRON_FENCE),Category.SOCIAL, 1000);
    }

    @Override
    public void applyKit(Player player) {
        Weapon.giveWeapon(player, Weapon.DEFAULT_SWORD);
        Weapon.giveWeapon(player, Weapon.GLADIATOR_BARS,1);
    }

    HashMap<String, Location> oldl = new HashMap<>();
    HashMap<String, String> fighting = new HashMap<>();
    HashMap<Player, Location> localizacao = new HashMap<>();
    HashMap<Location, Block> bloco = new HashMap<>();
    HashMap<Integer, String[]> players = new HashMap<>();
    HashMap<String, Integer> tasks = new HashMap<>();
    ArrayList<String> gladgladiator = new ArrayList<>();
    int nextID = 0;
    int id1;
    int id2;

    @EventHandler
    public void onGladiatorKit(PlayerInteractEntityEvent e){
        final Player p = e.getPlayer();
        if (e.getRightClicked() instanceof Player){
            final Player r = (Player)e.getRightClicked();
            KitManager km = KitManager.getManager();
            if (p.getItemInHand().getType() == Material.IRON_FENCE &&
                    km.getKitAmIUsing(p,"gladiator")){
                e.setCancelled(true);
                p.updateInventory();
                if (Util.getInstance().inPvP(p)){
                    return;
                }
                int camada = 125;
                Location loc = new Location(p.getWorld(), p.getLocation().getBlockX(), camada-2, p.getLocation().getBlockZ());
                localizacao.put(p, loc);
                localizacao.put(r, loc);
                Location loc2 = new Location(p.getWorld(),
                        p.getLocation().getBlockX() + 8,
                        camada+1,
                        p.getLocation().getBlockZ() + 8);
                Location loc3 = new Location(p.getWorld(),
                        p.getLocation().getBlockX() - 8,
                        camada+1,
                        p.getLocation().getBlockZ() - 8);
                if (fighting.containsKey(p.getName()) ||
                        fighting.containsKey(r.getName())){
                    p.sendMessage(Messages.PREFIX+" §cYou is already in battle!");
                    return;
                }
                Integer currentID = Integer.valueOf(nextID);
                nextID += 1;
                ArrayList list = new ArrayList();
                list.add(p.getName());
                list.add(r.getName());
                players.put(currentID, (String[])list.toArray(new String[1]));
                oldl.put(p.getName(), p.getLocation());
                oldl.put(r.getName(), r.getLocation());
                List<Location> cuboid = new ArrayList();
                cuboid.clear();
                int bY;
                for (int bX = -10; bX <= 10; bX++) {
                    for (int bZ = -10; bZ <= 10; bZ++) {
                        for (bY = -1; bY <= 10; bY++)
                        {
                            Block b = loc.clone().add(bX, bY, bZ).getBlock();
                            if (!b.isEmpty()){
                                p.sendMessage(Messages.PREFIX+" §cYou can not create your arena here!");
                                return;
                            }
                            if (bY == 10){
                                cuboid.add(loc.clone().add(bX, bY, bZ));
                            }else{
                                if (bY == -1){
                                    cuboid.add(loc.clone().add(bX, bY, bZ));
                                }else{
                                    if (bX == -10 || bZ == -10 || bX == 10 || bZ ==10){
                                        cuboid.add(loc.clone().add(bX, bY, bZ));
                                    }
                                }
                            }
                        }
                    }
                }
                for (Location loc1 : cuboid){
                    loc1.getBlock().setType(Material.GLASS);
                    bloco.put(loc1, loc1.getBlock());
                }
                loc2.setYaw(135.0F);
                p.teleport(loc2);
                loc3.setYaw(-45.0F);
                r.teleport(loc3);
                p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 5*20, 5));
                r.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 5*20, 5));
                p.sendMessage(Messages.PREFIX+" §aYou pulled the player §7" + r.getDisplayName() + "§a for 1v1!");
                r.sendMessage(Messages.PREFIX+" §aYou were pulled by the player §7" + r.getDisplayName() + "§a for 1v1!");
                fighting.put(p.getName(), r.getName());
                fighting.put(r.getName(), p.getName());
                gladgladiator.add(p.getName());
                gladgladiator.add(r.getName());
                id2 = Bukkit.getScheduler().scheduleSyncDelayedTask(PvPMain.getInstance(), new Runnable()
                        {
                            public void run()
                            {
                                if (fighting.containsKey(p.getName()) &&
                                        fighting.get(p.getName()).equalsIgnoreCase(r.getName()) &&
                                        fighting.containsKey(r.getName()) &&
                                        fighting.get(r.getName()).equalsIgnoreCase(p.getName())){
                                    p.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 2000000, 5));
                                    r.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 2000000, 5));
                                }
                            }
                        }
                        , 2400L);
                id1 = Bukkit.getScheduler().scheduleSyncDelayedTask(PvPMain.getInstance(), new Runnable()
                        {
                            public void run()
                            {
                                if (fighting.containsKey(p.getName()) &&
                                        fighting.get(p.getName()).equalsIgnoreCase(r.getName()) &&
                                        fighting.containsKey(r.getName()) &&
                                        fighting.get(r.getName()).equalsIgnoreCase(p.getName())){
                                    fighting.remove(p.getName());
                                    fighting.remove(r.getName());
                                    gladgladiator.remove(p.getName());
                                    gladgladiator.remove(r.getName());
                                    p.teleport(oldl.get(p.getName()));
                                    r.teleport(oldl.get(r.getName()));
                                    oldl.remove(p.getName());
                                    oldl.remove(r.getName());
                                    p.removePotionEffect(PotionEffectType.WITHER);
                                    r.removePotionEffect(PotionEffectType.WITHER);
                                    p.sendMessage(Messages.PREFIX+" §aThe time is over! You returned to its original position!");
                                    r.sendMessage(Messages.PREFIX+" §aThe time is over! You returned to its original position!");
                                    Location loc = localizacao.get(p);
                                    List<Location> cuboid = new ArrayList();
                                    cuboid.clear();
                                    int bY;
                                    for (int bX = -10; bX <= 10; bX++) {
                                        for (int bZ = -10; bZ <= 10; bZ++) {
                                            for (bY = -1; bY <= 10; bY++) {
                                                if (bY == 10)
                                                    cuboid.add(loc.clone().add(bX, bY, bZ));
                                                else if (bY == -1)
                                                    cuboid.add(loc.clone().add(bX, bY, bZ));
                                                else if ((bX == -10) || (bZ == -10) || (bX == 10) || (bZ == 10)) {
                                                    cuboid.add(loc.clone().add(bX, bY, bZ));
                                                }
                                            }
                                        }
                                    }
                                    for (Location loc1 : cuboid){
                                        loc1.getBlock().setType(Material.AIR);
                                        if (bloco.containsKey(loc1)) {
                                            bloco.get(loc1).setType(Material.AIR);
                                        }
                                    }
                                }
                            }
                        }
                        , 4800L);
            }
        }
    }

    @EventHandler(priority= EventPriority.MONITOR)
    public void onPlyaerInteract(final PlayerInteractEvent e) {
        if (e.getAction() == Action.LEFT_CLICK_BLOCK &&
                e.getClickedBlock().getType() == Material.GLASS &&
                e.getPlayer().getGameMode() != GameMode.CREATIVE &&
                fighting.containsKey(e.getPlayer().getName())){
            e.setCancelled(true);
            e.getClickedBlock().setType(Material.BEDROCK);
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(PvPMain.getInstance(), new Runnable()
                    {
                        public void run()
                        {
                            if (fighting.containsKey(e.getPlayer().getName()))
                                e.getClickedBlock().setType(Material.GLASS);
                        }
                    }
                    , 30L);
        }
    }

    @EventHandler(priority=EventPriority.MONITOR)
    public void onBlockBreak(final BlockBreakEvent e)
    {
        if (e.getBlock().getType() == Material.GLASS &&
                e.getPlayer().getGameMode() != GameMode.CREATIVE &&
                fighting.containsKey(e.getPlayer().getName())){
            e.setCancelled(true);
            e.getBlock().setType(Material.BEDROCK);
            Bukkit.getScheduler().scheduleSyncDelayedTask(PvPMain.getInstance(), new Runnable()
                    {
                        public void run()
                        {
                            if (e.getPlayer().getGameMode() != GameMode.CREATIVE &&
                                    fighting.containsKey(e.getPlayer().getName())){
                                e.getBlock().setType(Material.GLASS);
                            }
                        }
                    }
                    , 30L);
        }
    }

    @EventHandler(priority=EventPriority.HIGHEST)
    public void onPlayerLeft(PlayerQuitEvent e)
    {
        Player p = e.getPlayer();
        if (fighting.containsKey(p.getName())){
            Player t = Bukkit.getServer().getPlayer(fighting.get(p.getName()));
            fighting.remove(t.getName());
            fighting.remove(p.getName());
            gladgladiator.remove(p.getName());
            gladgladiator.remove(t.getName());
            Location old = oldl.get(t.getName());
            t.teleport(old);
            t.sendMessage(Messages.PREFIX+" §4O §7" + p.getDisplayName() + " gave rage quit!");
            Bukkit.getScheduler().cancelTask(id1);
            Bukkit.getScheduler().cancelTask(id2);
            t.removePotionEffect(PotionEffectType.WITHER);
            Location loc = localizacao.get(p);
            List<Location> cuboid = new ArrayList();
            int bY;
            for (int bX = -10; bX <= 10; bX++) {
                for (int bZ = -10; bZ <= 10; bZ++) {
                    for (bY = -1; bY <= 10; bY++) {
                        if (bY == 10)
                            cuboid.add(loc.clone().add(bX, bY, bZ));
                        else if (bY == -1)
                            cuboid.add(loc.clone().add(bX, bY, bZ));
                        else if ((bX == -10) || (bZ == -10) || (bX == 10) || (bZ == 10)) {
                            cuboid.add(loc.clone().add(bX, bY, bZ));
                        }
                    }
                }
            }
            for (Location loc1 : cuboid){
                loc1.getBlock().setType(Material.AIR);
                bloco.get(loc1).setType(Material.AIR);
            }
            for (Location loc1 : cuboid){
                loc1.getBlock().setType(Material.AIR);
                bloco.get(loc1).setType(Material.AIR);
            }
            for (Location loc1 : cuboid){
                loc1.getBlock().setType(Material.AIR);
                bloco.get(loc1).setType(Material.AIR);
            }
        }
    }

    @EventHandler(priority=EventPriority.HIGHEST)
    public void onDeathGladiator(PlayerDeathEvent e)
    {
        Player p = e.getEntity();
        if (fighting.containsKey(p.getName()))
        {
            Player k = Bukkit.getServer().getPlayer(fighting.get(p.getName()));
            Location old = oldl.get(p.getName());
            k.teleport(old);
            k.sendMessage(Messages.PREFIX+" §aYou killed §7" + p.getDisplayName() + "§a!");
            Bukkit.getScheduler().cancelTask(id1);
            Bukkit.getScheduler().cancelTask(id2);
            k.removePotionEffect(PotionEffectType.WITHER);
            k.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100, 10));
            fighting.remove(k.getName());
            fighting.remove(p.getName());
            gladgladiator.remove(p.getName());
            gladgladiator.remove(k.getName());
            Location loc = localizacao.get(p);
            List<Location> cuboid = new ArrayList();
            cuboid.clear();
            int bY;
            for (int bX = -10; bX <= 10; bX++) {
                for (int bZ = -10; bZ <= 10; bZ++) {
                    for (bY = -1; bY <= 10; bY++) {
                        if (bY == 10)
                            cuboid.add(loc.clone().add(bX, bY, bZ));
                        else if (bY == -1)
                            cuboid.add(loc.clone().add(bX, bY, bZ));
                        else if ((bX == -10) || (bZ == -10) || (bX == 10) || (bZ == 10)) {
                            cuboid.add(loc.clone().add(bX, bY, bZ));
                        }
                    }
                }
            }
            for (Location loc1 : cuboid){
                loc1.getBlock().setType(Material.AIR);
                if (bloco.containsKey(loc1)) {
                    bloco.get(loc1).setType(Material.AIR);
                }
            }
            return;
        }
    }
}
