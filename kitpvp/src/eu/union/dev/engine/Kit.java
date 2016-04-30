package eu.union.dev.engine;

import org.bukkit.entity.Player;

public abstract class Kit {

    String name;
    String permission;

    public Kit(String name, String permission) {
        this.name = name;
        this.permission = permission;
    }


    public String getName() {
        return name;
    }

    public String getPermission() {
        return permission;
    }

    public abstract void applyKit(Player player);

}
