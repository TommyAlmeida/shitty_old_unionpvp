package eu.union.dev.utils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public enum Weapon {

    DEFAULT_SWORD(Material.WOOD_SWORD, "§aDefault Sword"),
    DEFAULT_BOW(Material.BOW, "§aDefault Bow"),
    DEFAULT_ARROW(Material.ARROW, "§aArrow"),
    GRANDPA_STICK(Material.STICK, "§eGrandPa Cane"),
    PULSAR_SHOCK(Material.MAGMA_CREAM, "§ePulsar Shock"),
    ICECUBE_ITEM(Material.PACKED_ICE, "§aIceCube Item"),
    EXPLODER_TNT(Material.BOW, "§eExploder TnT"),
    ENDERMAGE_PORTAL(Material.ENDER_PORTAL_FRAME, "§5Endermage Portal"),
    FISHERMAN_ROD(Material.FISHING_ROD, "§eFisherman Rod"),
    SWITCHER_SNOW_BALL(Material.SNOW_BALL, "§7Switcher Ball"),
    KANGAROO_FIREWORK(Material.FIREWORK, "§cKangaroo Firework"),
    FLASH_TORCH(Material.REDSTONE_TORCH_ON, "§4Flash Torch"),
    PHANTOM_FEATHER(Material.FEATHER, "§7Phantom Feather"),
    THOR_HAMMER(Material.GOLD_AXE, "§6Thor Hammer"),
    SPECIALIST_BOOK(Material.ENCHANTED_BOOK, "§9Specialist Book"),
    TIMELORD_CLOCK(Material.WATCH, "§6TimeLord Clock"),
    HULK_SLIME(Material.SLIME_BALL, "§aHulk Catch"),
    MONK_ROD(Material.BLAZE_ROD, "§6Monk's Rod"),
    JUMPFALL_PAPER(Material.PAPER, "§7JumpFall Paper"),
    CHECKPOINT_FENCE(Material.NETHER_FENCE, "§4Set Position"),
    CHECKPOINT_POT(Material.FLOWER_POT_ITEM, "§5Teleport"),
    SPECTRE_SUGAR(Material.SUGAR, "§7Spectre"),
    C4_SLIME(Material.SLIME_BALL, "§4C4");


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

    /*private static ItemStack makeWeapon(Weapon weapon, Enchantment enchant, int level){
        ItemStack item = new ItemStack(weapon.mat, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(weapon.name);
        meta.spigot().setUnbreakable(true);
        item.addEnchantment(enchant, level);
        item.setItemMeta(meta);

        return item;
    }*/

    public static void giveWeapon(Player p, Weapon weapon){
        ItemStack item = Weapon.makeWeapon(weapon);
        p.getInventory().setItem(0,item);
    }

    public static void giveWeapon(Player p, Weapon weapon, int slot){
        ItemStack item = Weapon.makeWeapon(weapon);
        p.getInventory().setItem(slot,item);
    }

    public static void giveWeapon(Player p, Weapon weapon, int slot, Enchantment enchant, int level){
        ItemStack item = Weapon.makeWeapon(weapon);
        p.getInventory().setItem(slot,item);
        p.getInventory().getItem(slot).addEnchantment(enchant,level);
    }

    public static void giveWeapon(Player p, Weapon weapon, Enchantment enchant, int level){
        ItemStack item = Weapon.makeWeapon(weapon);
        p.getInventory().setItem(0,item);
        p.getInventory().getItem(0).addEnchantment(enchant,level);
    }

    public String getName(){
        return this.name;
    }


}
