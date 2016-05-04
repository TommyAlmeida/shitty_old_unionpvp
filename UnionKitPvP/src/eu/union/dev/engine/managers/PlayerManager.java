package eu.union.dev.engine.managers;

import eu.union.dev.engine.KPlayer;

import java.util.ArrayList;
import java.util.UUID;

public class PlayerManager {

    private static ArrayList<KPlayer> players = new ArrayList<>();

    public static KPlayer getPlayer(UUID uuid){
        for(int i = 0; i < players.size(); i++){
            if(players.get(i).getUuid() == uuid){
                return players.get(i);
            }
        }
        return null;
    }

    public static boolean addPlayerProfile(KPlayer profile){
        if(getPlayer(profile.getUuid()) != null){
            return false;
        }

        players.add(profile);
        return true;
    }

    public static void removePlayer(KPlayer player){
        players.remove(player);
    }
}
