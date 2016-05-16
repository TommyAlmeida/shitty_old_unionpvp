package eu.union.dev.engine.handlers;

import org.bukkit.*;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShockwaveHandler implements Runnable{

    private final JavaPlugin plugin;
    private final double radius;
    private final int height = 3;
    private List<BlockState> blocks;
    private Map<Double, List<Location>> sortedBlocks;
    private double counter = 0.0D;
    private int taskId;

    public ShockwaveHandler(JavaPlugin paramJavaPlugin, int paramInt)
    {
        this.radius = (paramInt + 0.5D);
        this.plugin = paramJavaPlugin;
    }

    public void createShockwave(Player paramPlayer)
    {
        this.blocks = getTotalBlocks(paramPlayer.getLocation().add(0.0D, -2.0D, 0.0D));
        this.sortedBlocks = getSortedBlocks(this.blocks, paramPlayer.getLocation());
        this.taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(this.plugin, this, 0L, 3L);
    }

    public void run()
    {
        if (this.sortedBlocks.containsKey(this.counter))
        {
            List<Location> localList = this.sortedBlocks.get(this.counter);
            for (Location localLocation : localList)
            {
                localLocation.getWorld().playSound(localLocation, Sound.ZOMBIE_WOODBREAK, 1.0F, 2.0F);
                localLocation.getWorld().playEffect(localLocation,
                        Effect.STEP_SOUND, Material.BEDROCK,
                        3);
                localLocation.getBlock().setType(Material.AIR);
            }
        }
        this.counter += 1.0D;
        if (this.counter >= this.radius)
        {
            Bukkit.getScheduler().cancelTask(this.taskId);

            Bukkit.getScheduler().scheduleSyncDelayedTask(this.plugin, new BlockRebuilder(this.blocks), 200L);
        }
    }

    private Map<Double, List<Location>> getSortedBlocks(List<BlockState> paramList, Location paramLocation)
    {
        Location localLocation = paramLocation.clone().add(new Vector(0, 3, 0));

        HashMap<Double, List<Location>> localHashMap = new HashMap<>();
        for (BlockState localBlockState : paramList)
        {
            double d = Math.floor(localBlockState.getLocation().distance(localLocation));
            if (localHashMap.containsKey(d))
            {
                (localHashMap.get(d)).add(localBlockState.getLocation());
            }
            else
            {
                ArrayList<Location> localArrayList = new ArrayList<>();
                localArrayList.add(localBlockState.getLocation());
                localHashMap.put(d, localArrayList);
            }
        }
        return localHashMap;
    }

    private List<BlockState> getTotalBlocks(Location paramLocation)
    {
        ArrayList<BlockState> localArrayList = new ArrayList<BlockState>();
        World localWorld = paramLocation.getWorld();
        int i = paramLocation.getBlockX();
        int j = paramLocation.getBlockY();
        int k = paramLocation.getBlockZ();

        Vector localVector2 = paramLocation.toVector();
        for (int m = 0; m <= 3; m++) {
            for (double d1 = -this.radius; d1 < this.radius; d1 += 1.0D) {
                for (double d2 = -this.radius; d2 < this.radius; d2 += 1.0D)
                {
                    Vector localVector1 = new Vector(i + d1, j, k + d2);
                    if (localVector2.distance(localVector1) < this.radius)
                    {
                        BlockState localBlockState = localWorld.getBlockAt((int)(i + d1), j + m, (int)(k + d2)).getState();
                        if (localBlockState.getType() != Material.AIR) {
                            localArrayList.add(localBlockState);
                        }
                    }
                }
            }
        }
        return localArrayList;
    }
}

