package eu.union.dev.engine.modules;

import eu.union.dev.engine.KPlayer;
import org.bukkit.entity.Player;

public abstract class Booster {

    private String name;
    private int coins;
    private boolean owned = false;
    private boolean global = false;
    private KPlayer owner;

    public Booster(String name, int coins, boolean owned, boolean global, KPlayer owner) {
        this.name = name;
        this.coins = coins;
        this.global = global;
        this.owned = owned;
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public boolean isOwned() {
        return owned;
    }

    public void setOwned(boolean owned) {
        this.owned = owned;
    }

    public boolean isGlobal() {
        return global;
    }

    public void setGlobal(boolean global) {
        this.global = global;
    }

    public KPlayer getOwner() {
        return owner;
    }

    public abstract void applyEffect(Player player);
}
