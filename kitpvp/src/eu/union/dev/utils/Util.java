package eu.union.dev.utils;

import eu.union.dev.api.Icon;
import net.minecraft.server.v1_8_R3.AttributeModifier;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Util {

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

    public static void buildJoinIcons(Player player){
        Inventory inv = player.getInventory();

        {
            Icon kits = new Icon(Material.NETHER_STAR, Messages.ICON_KITS.toString(), "§7Choose your kit");
            inv.setItem(4,kits.build());
        }

        {
            Icon config = new Icon(Material.TRIPWIRE_HOOK, Messages.ICON_CONFIG.toString(),
                    "§eYou've a lot of top features:",
                    "§7Disable your chat",
                    "§7Change your chat tag",
                    "§7more §bsoon..."
            );

            inv.setItem(8,config.build());
        }

        {
            Icon warps = new Icon(Material.COMPASS, Messages.ICON_WARPS.toString(), "§7Where do you wanna go?");
            inv.setItem(0,warps.build());
        }

        {
            Icon stats = new Icon(Material.IRON_SWORD, Messages.ICON_STATS.toString(), "§7Where do you wanna go?");
            inv.setItem(0,stats.build());
        }
    }
}
