package eu.union.dev.engine.modules;

public abstract class Item {

    public enum ItemType{
        WEAPON,
        AURA,
        SOCKET;
    }

    ItemType type;

    public Item(ItemType type){
        this.type = type;
    }

    public abstract void apply();
}
