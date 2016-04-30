package eu.union.dev;

import eu.union.dev.commands.KitCMD;
import eu.union.dev.commands.ListKitsCMD;
import eu.union.dev.engine.KitManager;
import eu.union.dev.kits.PvP;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public void onEnable(){
        registerKits();

        getCommand("kit").setExecutor(new KitCMD());
        getCommand("kits").setExecutor(new ListKitsCMD());
    }

    /**
     * Register all kits in the main thread
     */
    private void registerKits(){
        KitManager km = KitManager.getManager();

        km.registerKit(new PvP());
    }
}
