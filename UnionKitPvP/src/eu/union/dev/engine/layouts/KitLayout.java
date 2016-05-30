package eu.union.dev.engine.layouts;

import eu.union.dev.api.Icon;
import eu.union.dev.engine.Kit;
import org.apache.commons.lang.WordUtils;
import org.bukkit.inventory.ItemStack;

public class KitLayout implements Layout {

    private static KitLayout layout = new KitLayout();

    String regex = "\\[|\\]";

    public static KitLayout getLayout() {
        return layout;
    }

    @Override
    public ItemStack design(Icon icon, Kit kit) {

        /*icon = new Icon(icon.getMaterial(), kit.getRarity().getColor() + WordUtils.capitalize(kit.getName()),
                " ",
                "§7About",
                "§c" + about.replaceAll(regex,""),
                " ",
                "§7Difficulty: §e" + kit.getDifficulty().value(),
                "§7Level Required: §b" + kit.getLevel(),
                " "
        );*/

        icon = new Icon(icon.getMaterial(), "§7Kit » " + kit.getRarity().getColor() + WordUtils.capitalize(kit.getName()),
                "§7Category: " + kit.getCategory().getName(),
                " ",
                "§7Difficulty: §e" + kit.getDifficulty().value(),
                "§7Level Required: §b" + kit.getLevel()
        );

        return icon.build();
    }
}
