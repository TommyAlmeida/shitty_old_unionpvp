package eu.union.dev.listeners;

import eu.union.dev.PvPMain;
import eu.union.dev.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import java.util.HashMap;

public class MysteryBox implements Listener {

    private HashMap<String, Boolean> mysterycase = new HashMap<String, Boolean>();
    private int TimerID;


    @EventHandler
    public void onOpenCase(PlayerInteractEvent event) {
        Block b = event.getClickedBlock();
        Player player = event.getPlayer();

        if(!(event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            return;
        }

        if(!(b.getType() == Material.ENDER_CHEST)) {
            return;
        }

        if(b == null) {
            return;
        }

        if(mysterycase.get("USING") == true) {

            player.sendMessage(Messages.PREFIX.toString() + " §7The case is alread in use. Wait a few moments.");

        } else {

            mysterycase.put("USING", true);
            caseEngine(b);

        }

    }

    public void caseEngine(Block block) {

        Location lb = new Location(block.getWorld(), block.getLocation().getX(), block.getLocation().getY(), block.getLocation().getZ());

        TimerID = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin) PvPMain.getInstance(), new Runnable() {

            int timer = 10;

            public void run() {

                if(timer > 0) {
                    timer--;
                }

                if(timer == 8) {

                    FallingBlock b = block.getWorld().spawnFallingBlock(lb,
                            Material.ENDER_PORTAL_FRAME, (byte) 0);
                    b.setVelocity(new Vector(0D, 0.7D, 0D));

                }

                if(timer == 4) {

                    FallingBlock b = block.getWorld().spawnFallingBlock(lb,
                            Material.ENDER_PORTAL_FRAME, (byte) 0);
                    b.setVelocity(new Vector(0D, 0.7D, 0D));

                }

                if(timer == 0) {
                    Bukkit.getScheduler().cancelTask(TimerID);

                    mysterycase.remove("USING");
                }

            }

        }, 0, 20 * 1);

    }

}
