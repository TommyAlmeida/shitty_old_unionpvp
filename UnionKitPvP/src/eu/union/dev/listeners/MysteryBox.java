package eu.union.dev.listeners;

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
import org.bukkit.util.Vector;

import pt.josegamerpt.unionhub.Main;
import pt.josegamerpt.unionhub.config.ConfigYML;
import pt.josegamerpt.unionhub.utils.Color;

public class MysteryBox implements Listener {

    private int TimerID;

    @EventHandler
    public void onOpenCase(PlayerInteractEvent event) {
        Block b = event.getClickedBlock();
        Player p = event.getPlayer();

        if (b == null) {
            return;
        }

        if (!(event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            return;
        }

        if (!(b.getType() == Material.ENDER_CHEST)) {
            return;
        }

        if (State.getState(b) == 1) {
            p.sendMessage(Color.color(" &7This case is already in use!"));
            event.setCancelled(true);
            return;
        } else {
            ConfigYML.ficheiro().set("MysteryBoxes." + b.toString(), true);
            ConfigYML.salvar();
            caseEngine(b, p);
        }

    }

    public void caseEngine(Block b, Player p) {

        Location lb = new Location(b.getWorld(), b.getLocation().getX(), b.getLocation().getY(),
                b.getLocation().getZ());

        TimerID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.pl, new Runnable() {

            int timer = 10;

            @SuppressWarnings("deprecation")
            public void run() {

                if (timer > 0) {
                    timer--;
                }

                if (timer == 8) {

                    FallingBlock f = b.getWorld().spawnFallingBlock(lb, Material.ENDER_PORTAL_FRAME, (byte) 6);
                    f.setDropItem(false);
                    f.setVelocity(new Vector(0D, 0.7D, 0D));

                }

                if (timer == 6) {

                    FallingBlock f = b.getWorld().spawnFallingBlock(lb, Material.OBSIDIAN, (byte) 0);
                    f.setDropItem(false);
                    f.setVelocity(new Vector(0D, 0.7D, 0D));

                }

                if (timer == 4) {

                    FallingBlock f = b.getWorld().spawnFallingBlock(lb, Material.ENDER_PORTAL_FRAME, (byte) 0);
                    f.setDropItem(false);
                    f.setVelocity(new Vector(0D, 0.7D, 0D));

                }

                if (timer == 0) {

                    Bukkit.getScheduler().cancelTask(TimerID);

                    p.sendMessage("You won nothing! Wow!");

                    ConfigYML.ficheiro().set("MysteryBoxes." + b.toString(), false);
                    ConfigYML.salvar();
                }

            }

        }, 0, 20 * 1);

    }
}
