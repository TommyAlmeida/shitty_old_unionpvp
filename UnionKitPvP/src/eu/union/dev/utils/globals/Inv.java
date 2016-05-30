package eu.union.dev.utils.globals;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

public class Inv {

    private static Inv instance = new Inv();

    public Inventory kits = Bukkit.createInventory(null, 54, "Kits");
    public Inventory config = Bukkit.createInventory(null, 9, "Settings");
    public Inventory warps = Bukkit.createInventory(null, 9*3, "Warps");
    public Inventory shop = Bukkit.createInventory(null, 9*3, "Shop");
    public Inventory menu = Bukkit.createInventory(null, 9*3, "Menu");


    public static Inv getInstance() {
        return instance;
    }
}
