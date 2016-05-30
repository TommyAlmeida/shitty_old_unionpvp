package eu.union.dev.engine.managers;

import eu.union.dev.engine.modules.Booster;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BoosterManager {

    private static BoosterManager boosterManager = new BoosterManager();

    List<Booster> boosters = new ArrayList<>();

    public static BoosterManager getManager() {
        return boosterManager;
    }

    public Booster getBoosterByName(String name) {
        for (Booster booster : boosters) {
            if (booster.getName().equalsIgnoreCase(name)) {
                return booster;
            }
        }
        return null;
    }

    public void addBoosterToPlayer(UUID uuid) {

    }
}
