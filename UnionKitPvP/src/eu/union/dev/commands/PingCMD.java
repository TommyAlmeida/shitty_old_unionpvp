package eu.union.dev.commands;

import net.minecraft.server.v1_8_R3.EntityPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class PingCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            return true; //Retorna
        }

        Player player = (Player) sender;

        player.sendMessage("§7Ping: §a" + getPing(player));

        return true;

    }

    private int getPing(Player p){
        CraftPlayer cp = (CraftPlayer) p;
        EntityPlayer entityPlayer = cp.getHandle();

        return entityPlayer.ping;
    }
}
