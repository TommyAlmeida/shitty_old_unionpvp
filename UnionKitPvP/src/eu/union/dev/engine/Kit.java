package eu.union.dev.engine;

import eu.union.dev.utils.Messages;
import org.bukkit.entity.Player;

public abstract class Kit {

    public enum Difficulty{
        LOW("Low"),
        MEDIUM("Medium"),
        HARD("Hard"),
        PRO("Pro");

        String d;

        Difficulty(String d){
            this.d = d;
        }

        public String value(){
            return this.d;
        }
    }

    public enum Rarity{
        COMMON("§7",70),
        RARE("§b",15),
        EPIC("§d",7),
        HEROIC("§6",5),
        BEAST("§5",3);

        String color;
        int chance;

        Rarity(String color, int chance){
            this.color = color;
            this.chance = chance;
        }

        public int getChance(){
            return this.chance;
        }

        public String getColor() {
            return this.color;
        }
    }

    private String name;
    private String permission;
    private int level;
    Rarity rarity;
    Difficulty difficulty;
    public String prefix = Messages.PREFIX.toString();


    public Kit(String name, String permission, Difficulty difficulty, Rarity rarity, int level) {
        this.name = name;
        this.difficulty = difficulty;
        this.rarity = rarity;
        this.level = level;
        this.permission = permission;
    }


    public String getName() {
        return name;
    }

    public Difficulty getDifficulty(){
        return difficulty;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level){
        this.level = level;
    }

    public String getPermission() {
        return permission;
    }

    public abstract void applyKit(Player player);

}
