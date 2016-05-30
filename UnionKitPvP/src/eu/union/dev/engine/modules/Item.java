package eu.union.dev.engine.modules;

public abstract class Item {

    ItemType type;

    public Item(ItemType type) {
        this.type = type;
    }

    public abstract void apply();

    public enum ItemType {
        WEAPON,
        AURA,
        SOCKET;
    }
}
