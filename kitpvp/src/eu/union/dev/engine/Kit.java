package eu.union.dev.engine;

import org.bukkit.entity.Player;

public abstract class Kit {

    public enum Difficulty{
        LOW("Low"),
        MEDIUM("Medium"),
        HARD("Hard"),
        PRO("Prop");

        String d;

        Difficulty(String d){
            this.d = d;
        }

        public String value(){
            return this.d;
        }
    }

    private String name;
    private String permission;
    private String[] about;
    Difficulty difficulty;


    public Kit(String name, String permission, Difficulty difficulty, String... about) {
        this.name = name;
        this.difficulty = difficulty;
        this.about = about;
        this.permission = permission;
    }


    public String getName() {
        return name;
    }

    public Difficulty getDifficulty(){
        return difficulty;
    }

    public String[] getAbout() {
        return about;
    }


    public String getPermission() {
        return permission;
    }

    public abstract void applyKit(Player player);

}
