package eu.union.dev.engine.layouts;

import eu.union.dev.api.Icon;
import eu.union.dev.engine.Kit;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class KitLayout implements Layout {

    private static KitLayout layout = new KitLayout();

    @Override
    public ItemStack design(Icon icon, Kit kit) {
        icon = new Icon(icon.getMaterial(), "§6" + kit.getName(),
                " ",
                "§7About",
                "§c" + Arrays.toString(kit.getAbout()),
                " ",
                "§7Difficulty: §e" + kit.getDifficulty(),
                " "
        );

        return icon.build();
    }


    public static KitLayout getLayout() {
        return layout;
    }
}
