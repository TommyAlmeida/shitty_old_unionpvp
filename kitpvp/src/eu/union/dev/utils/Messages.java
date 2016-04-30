package eu.union.dev.utils;

public enum Messages {

    PREFIX("§e§lUnionPvP"),
    NO_PERM("§e§lUnionPvP §cYou dont have permissions to do this");

    String s;

    Messages(String s){
        this.s = s;
    }

    @Override
    public String toString(){
        return this.s;
    }
}
