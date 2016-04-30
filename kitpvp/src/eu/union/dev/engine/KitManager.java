package eu.union.dev.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import eu.union.dev.utils.Messages;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

public class KitManager {

    public static KitManager km;

    /**
     * Método que sempre retornará o mesmo KitManager.
     * @return
     */
    public static KitManager getManager() {
        if (km == null)
            km = new KitManager();
        return km;
    }


    List<Kit> kits = new ArrayList<>(); //Lista de Kits.

    HashMap<Player, Kit> playerKit = new HashMap<>();


    /**
     * Register kit by adding him on kits list
     * @param kit
     */
    public void registerKit(Kit kit) {
        kits.add(kit);
    }

    /**
     * Remove kit form kits list
     * @param kit
     */
    public void unregisterKit(Kit kit) {
        kits.remove(kit);
    }

    /**
     * Search kit by name
     * @param name
     * @return
     */
    public Kit getKitByName(String name) {
        for (Kit kit : kits) {
            if (kit.getName().equalsIgnoreCase(name))
                return kit;
        }
        return null;
    }

    /**
     * Get the player kit that hes using
     * @param player
     * @return
     */
    public Kit getKitByPlayer(Player player) {
        if (playerKit.containsKey(player))
            return playerKit.get(player);
        return null;
    }

    /**
     * Reset all player values to default
     * @param player
     */
    public void readyPlayer(Player player) {
        player.getInventory().clear();
        player.setHealth(player.getMaxHealth());
        player.setFoodLevel(20);
        player.setExhaustion(0f);
        player.setFallDistance(0f);
        player.setFireTicks(0);
        for (PotionEffect pE : player.getActivePotionEffects()) {
            player.removePotionEffect(pE.getType());
        }
    }

    /**
     * Function activated when player use the kit
     * @param player
     * @param kit
     */
    public void applyKit(Player player, Kit kit) {

        if (!player.hasPermission(kit.getPermission())) {
            player.sendMessage(Messages.NO_PERM.toString());
            return;
        }

        readyPlayer(player);
        kit.applyKit(player);
        playerKit.put(player, kit);
    }

    /**
     * Remove kit from player
     * @param player
     */
    public void removeKit(Player player) {

        if (!playerKit.containsKey(player)) {
            player.sendMessage(Messages.NO_PERM.toString());
            return;
        }

        readyPlayer(player);
        playerKit.remove(player);

    }

}
