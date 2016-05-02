package eu.union.dev.utils;

public enum Messages {

    PREFIX("§e§lUnionPvP"),
    NO_PERM("§e§lUnionPvP §cYou dont have permissions to do this"),

    ICON_KITS("§aKits §7(Right-Click)"),
    ICON_WARPS("§bWarps §7(Right-Click)"),
    ICON_STATS("§9Stats §7(Right-Click)"),
    ICON_CONFIG("§cSettings §7(Right-Click)");

    String s;

    Messages(String s){
        this.s = s;
    }

    @Override
    public String toString(){
        return this.s;
    }
}
