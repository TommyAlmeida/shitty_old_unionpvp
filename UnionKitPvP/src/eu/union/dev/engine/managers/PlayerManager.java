package eu.union.dev.engine.managers;

import com.google.common.collect.Maps;
import eu.union.dev.engine.KPlayer;

import java.util.Map;
import java.util.UUID;

public class PlayerManager {

    private static Map<UUID, KPlayer> players = Maps.newHashMap();

    public static KPlayer getPlayer(UUID uuid) {
        return players.get(uuid);
    }

    public static boolean addPlayerProfile(KPlayer profile) {
        if (getPlayer(profile.getUuid()) != null) {
            return false;
        }

        players.put(profile.getUuid(), profile);
        return true;
    }

    public static void removePlayer(KPlayer player) {
        players.remove(player.getUuid());
    }
}
