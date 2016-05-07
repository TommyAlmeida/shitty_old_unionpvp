package eu.union.dev.utils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public enum Weapon {

    DEFAULT_SWORD(Material.WOOD_SWORD, "§aDefault Sword"),
    GRANDPA_STICK(Material.STICK, "§aGrandPa Cane"),
    DEFAULT_BOW(Material.BOW, "§aDefault Bow"),
    EXPLODER_TNT(Material.BOW, "§aExploder TnT");


    Material mat;
    String name;

    Weapon(Material mat, String name){
        this.mat = mat;
        this.name = name;
    }

    private static ItemStack makeWeapon(Weapon weapon){
        ItemStack item = new ItemStack(weapon.mat, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(weapon.name);
        meta.spigot().setUnbreakable(true);
        item.setItemMeta(meta);

        return item;
    }

    private static ItemStack makeWeapon(Weapon weapon, Enchantment enchant, int level){
        ItemStack item = new ItemStack(weapon.mat, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(weapon.name);
        meta.spigot().setUnbreakable(true);
        item.addEnchantment(enchant, level);
        item.setItemMeta(meta);

        return item;
    }

    public static void giveWeapon(Player p, Weapon weapon){
        ItemStack item = Weapon.makeWeapon(weapon);
        p.getInventory().setItem(0,item);
    }

    public static void giveWeapon(Player p, Weapon weapon, Enchantment enchant, int level){
        ItemStack item = Weapon.makeWeapon(weapon, enchant, level);
        p.getInventory().setItem(0,item);
    }


}
