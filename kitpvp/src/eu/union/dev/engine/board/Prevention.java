package eu.union.dev.engine.board;

import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class Prevention {

    String name;
    boolean canEnter;

    public Prevention(String name, boolean canEnter){
        this.name = name;
        this.canEnter = canEnter;
    }

    public String getName() {
        return name;
    }

    public boolean canPlayerEnter(){
        return canEnter;
    }

    public void shoutPlayer(Player player){
        if(!canEnter){
            Vector v = player.getVelocity().multiply(2).add(new Vector(0,0,1d));
            player.setVelocity(v);
        }
    }

    public void build(){

    }
}
