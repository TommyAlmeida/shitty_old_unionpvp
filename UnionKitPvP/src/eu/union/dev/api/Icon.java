package eu.union.dev.api;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class Icon {

    Material mat;
    String name;
    int amount;
    String[] lore;
    Enchantment enchantment;
    int level;

    public Icon(Material mat, String name, String... lore) {
        this.mat = mat;
        this.name = name;
        this.amount = 1;
        this.lore = lore;
    }

    public Icon(Material mat) {
        this.mat = mat;
        this.amount = 1;
    }

    public Icon(Material mat, Enchantment enchantment, int level, String name) {
        this.mat = mat;
        this.amount = 1;
        this.enchantment = enchantment;
        this.name = name;
        this.level = level;
    }


    public int getAmount() {
        return amount;
    }

    public Material getMaterial() {
        return mat;
    }

    public boolean hasLore() {
        return lore.length >= 0;
    }

    public void name(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String[] getLore() {
        return lore;
    }

    public ItemStack build() {
        ItemStack item = new ItemStack(mat, amount);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));

        item.setItemMeta(meta);

        return item;
    }

    public ItemStack buildEnchant() {
        ItemStack item = new ItemStack(mat, amount);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));
        item.addEnchantment(enchantment, level);
        item.setItemMeta(meta);

        return item;
    }

    public ItemStack buildNoDrop() {
        ItemStack item = new ItemStack(mat, amount);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));
        meta.spigot().setUnbreakable(true);
        item.setItemMeta(meta);

        return item;
    }

}
