package eu.union.dev.engine.modules;

import eu.union.dev.PvPMain;
import eu.union.dev.utils.ParticleEffect;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public abstract class Aura {

    private String name;
    private int cost;
    private String permission;
    private ParticleEffect.ParticleColor color;
    private boolean isEnable;

    public abstract void apply(Player player);

    public Aura(String name, int cost, boolean isEnable, String permission, ParticleEffect.ParticleColor color){
        this.name = name;
        this.cost = cost;
        this.color = color;
        this.isEnable = isEnable;
        if(permission.isEmpty() || permission == "" || permission == null){
            this.permission = "union.free";
        }else{
            this.permission = permission;
        }
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public ParticleEffect.ParticleColor getColor() {
        return color;
    }

    public boolean isEnable(){
        return isEnable;
    }

    public String getPermission() {
        return permission;
    }


    public void run(Player player){
        Location loc = player.getLocation();

        int raio = 5;
        for (double y = 0; y <= 360; y += 0.10) {
            double x = raio * Math.cos(y);
            double z = raio * Math.sin(y);
            Location center = new Location(player.getWorld(), loc.getX() + x, loc.getY() + y, loc.getZ() + z);
            ParticleEffect.CLOUD.display(color, center, 6);
        }
    }
}
