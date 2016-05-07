package eu.union.dev.engine.layouts;

import eu.union.dev.api.Icon;
import eu.union.dev.engine.Kit;
import org.bukkit.inventory.ItemStack;

public interface Layout {

    /**
     * Design all lore features in a fancy way
     * @param icon
     * @param kit
     */
     ItemStack design(Icon icon, Kit kit, String... about);
}
