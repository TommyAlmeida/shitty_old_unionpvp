package eu.union.dev.utils;

import eu.union.dev.api.Icon;
import eu.union.dev.engine.Kit;
import net.minecraft.server.v1_8_R3.AttributeModifier;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Util {

    private static Util instance = new Util();

    public static Util getInstance() {
        return instance;
    }

    public static String fixFontSize (String s, int size) {

        String ret = s.toUpperCase();

        if ( s != null ) {

            for (int i=0; i < s.length(); i++) {
                if ( s.charAt(i) == 'I' || s.charAt(i) == ' ') {
                    ret += " ";
                }
            }

            int faltaEspacos = size - s.length();
            faltaEspacos = (faltaEspacos * 2);

            for (int i=0; i < faltaEspacos; i++) {
                ret += " ";
            }
        }

        return (ret);
    }

    public static void giveSoups(Player player) {

        for(int i=0; i < 50; i++) {
            player.getInventory().addItem(new ItemStack(Material.MUSHROOM_SOUP));
        }

    }

    public void buildJoinIcons(Player player){
        Inventory inv = player.getInventory();

        {
            Icon kits = new Icon(Material.NETHER_STAR, "§aKits §7(Right-Click)", "§7Choose your kit");
            inv.setItem(0,kits.build());
        }


        {
            Icon warps = new Icon(Material.VINE, " ", " ");
            inv.setItem(3,warps.build());
            inv.setItem(5,warps.build());
        }

        {
            Icon warps = new Icon(Material.COMPASS, "§bWarps §7(Right-Click)", "§7Where do you wanna go?");
            inv.setItem(4,warps.build());
        }

        {
            Icon stats = new Icon(Material.IRON_SWORD, "§9Stats §7(Right-Click)", "§7Where do you wanna go?");
            inv.setItem(8,stats.build());
        }
    }

}
