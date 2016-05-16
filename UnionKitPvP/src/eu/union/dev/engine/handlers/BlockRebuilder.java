package eu.union.dev.engine.handlers;

import org.bukkit.block.BlockState;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class BlockRebuilder extends BukkitRunnable {
    private final List<BlockState> blocks;

    public BlockRebuilder(List<BlockState> paramList) {
        this.blocks = paramList;
    }

    public void run() {
        for (BlockState localBlockState : this.blocks) {
            localBlockState.update(true);
        }
    }
}
