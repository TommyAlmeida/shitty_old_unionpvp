package eu.union.dev.api;

import eu.union.dev.engine.Kit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class Icon {

    Material mat;
    String name;
    int amount;
    String[] lore;

    public Icon(Material mat, String name, String... lore){
        this.mat = mat;
        this.name = name;
        this.amount = 1;
        this.lore = lore;
    }

    public Icon(Material mat){
        this.mat = mat;
        this.amount = 1;
    }


    public int getAmount() {
        return amount;
    }

    public Material getMaterial() {
        return mat;
    }

    public boolean hasLore(){
        if(lore.length < 0){
            return false;
        }else{
            return true;
        }
    }

    public void name(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public String[] getLore() {
        return lore;
    }


    public ItemStack build(){
        ItemStack item = new ItemStack(mat, amount);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));
        item.setItemMeta(meta);

        return item;
    }

}
