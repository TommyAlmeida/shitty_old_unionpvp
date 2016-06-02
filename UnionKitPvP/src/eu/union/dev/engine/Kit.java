package eu.union.dev.engine;

import eu.union.dev.api.Icon;
import eu.union.dev.utils.globals.Messages;
import org.bukkit.entity.Player;

public abstract class Kit {

    public String prefix = Messages.PREFIX.toString();
    Rarity rarity;
    Difficulty difficulty;
    Category category;
    private String name;
    private String permission;
    private int level;
    private Icon icon;

    public Kit(String name, String permission, Difficulty difficulty, Rarity rarity, int level, Icon icon, Category category) {
        this.name = name;
        this.difficulty = difficulty;
        this.rarity = rarity;
        this.level = level;
        this.category = category;
        this.permission = permission;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getPermission() {
        return permission;
    }

    public Category getCategory() {
        return category;
    }

    public abstract void applyKit(Player player);

    public Icon getIcon() {
        return icon;
    }

    public enum Difficulty {
        LOW("Low"),
        MEDIUM("Medium"),
        HARD("Hard"),
        PRO("Pro");

        String d;

        Difficulty(String d) {
            this.d = d;
        }

        public String value() {
            return this.d;
        }
    }

    public enum Rarity {
        COMMON("§7", 70),
        RARE("§b", 15),
        EPIC("§d", 7),
        HEROIC("§6", 5),
        BEAST("§5", 3);

        String color;
        int chance;

        Rarity(String color, int chance) {
            this.color = color;
            this.chance = chance;
        }

        public int getChance() {
            return this.chance;
        }

        public String getColor() {
            return this.color;
        }
    }

    public enum Category {
        NONE(""),
        CHANCE("Chance"),
        JUMPER("Jumper"),
        SPAWNER("Spawner"),
        CHARGER("Charger"),
        BROKEN("Broken Kit"),
        LONG_DISTANCE("Long Distance"),
        PROTECTED("Protected"),
        KAMIKAZE("Kamikaze"),
        CATCHER("Catcher"),
        SOCIAL("Interact"),
        TELEPORT("Teleport"),
        FREEZE("Freeze"),
        GRAB("Grabber"),
        POTION("Potion"),
        SWORDS("Swords");

        String name;

        Category(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

}
