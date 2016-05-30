package eu.union.dev.engine.layouts;

import eu.union.dev.api.Icon;
import eu.union.dev.engine.Kit;
import org.bukkit.inventory.ItemStack;

/**
 * Design all lore features in a fancy way
 */
public interface Layout {

    ItemStack design(Icon icon, Kit kit);
}
