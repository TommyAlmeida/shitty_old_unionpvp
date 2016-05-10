package eu.union.dev.engine.layouts;

import eu.union.dev.api.Icon;
import eu.union.dev.engine.Kit;
import org.apache.commons.lang.WordUtils;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class KitLayout implements Layout {

    private static KitLayout layout = new KitLayout();

    String regex = "\\[|\\]";



    @Override
    public ItemStack design(Icon icon, Kit kit) {


        String about = String.join("\n",kit.getAbout());

        /*icon = new Icon(icon.getMaterial(), kit.getRarity().getColor() + WordUtils.capitalize(kit.getName()),
                " ",
                "§7About",
                "§c" + about.replaceAll(regex,""),
                " ",
                "§7Difficulty: §e" + kit.getDifficulty().value(),
                "§7Level Required: §b" + kit.getLevel(),
                " "
        );*/

        icon = new Icon(icon.getMaterial(), kit.getRarity().getColor() + WordUtils.capitalize(kit.getName()),
                " ",
                "§7Difficulty: §e" + kit.getDifficulty().value(),
                "§7Level Required: §b" + kit.getLevel()
        );

        return icon.build();
    }



    public static KitLayout getLayout() {
        return layout;
    }
}
