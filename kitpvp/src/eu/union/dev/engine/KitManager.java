package eu.union.dev.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import eu.union.dev.utils.Messages;
import eu.union.dev.utils.Util;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

public class KitManager {

    public static KitManager km = new KitManager();

    /**
     * Método que sempre retornará o mesmo KitManager.
     * @return
     */
    public static KitManager getManager() {
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

    public boolean getUsingKit(Player player){
        if(playerKit.containsKey(player)){
            return false;
        }else{
            return true;
        }
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

        playerKit.remove(player);
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

        if(getUsingKit(player)){
            player.sendMessage(Messages.PREFIX.toString() + " §7You already have a kit!");
        }else{
            readyPlayer(player);
            kit.applyKit(player);

            playerKit.put(player, kit);

            Util.giveSoups(player);

            player.sendMessage(Messages.PREFIX.toString() + " §7You are using kit: §a" + kit.getName());
        }

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
        player.sendMessage(Messages.PREFIX.toString() + " §cYou are no longer using a kit.");
    }

    /**
     * Returns the difficulty of the kit
     * @param kit
     * @return
     */
    public String getKitDifficulty(Kit kit){
        return kit.difficulty.value();
    }

    /**
     * Get a list of kits
     * @return
     */
    public List<Kit> getKits() {
        return kits;
    }

}
