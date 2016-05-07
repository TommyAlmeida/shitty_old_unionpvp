package eu.union.dev.engine.layouts;

import eu.union.dev.api.Icon;
import eu.union.dev.engine.Kit;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class KitLayout implements Layout {

    private static KitLayout layout = new KitLayout();

    String regex = "\\[|\\]";

    @Override
    public ItemStack design(Icon icon, Kit kit, String... about) {

        icon = new Icon(icon.getMaterial(), kit.getRarity().getColor() + kit.getName(),
                " ",
                "§7About",
                "§c" + about,
                " ",
                "§7Difficulty: §e" + kit.getDifficulty().value(),
                "§7Level Required: §b" + kit.getLevel(),
                " "
        );

        return icon.build();
    }



    public static KitLayout getLayout() {
        return layout;
    }
}
