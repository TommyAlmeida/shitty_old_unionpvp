package eu.union.dev;

import eu.union.dev.commands.KitCMD;
import eu.union.dev.commands.ListKitsCMD;
import eu.union.dev.engine.KitManager;
import eu.union.dev.kits.Grandpa;
import eu.union.dev.kits.PvP;
import eu.union.dev.listeners.PlayerListeners;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public void onEnable(){
        registerKits();

        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new PlayerListeners(), this);

        getCommand("kit").setExecutor(new KitCMD());
        getCommand("kits").setExecutor(new ListKitsCMD());
    }

    /**
     * Register all kits in the main thread
     */
    private void registerKits(){
        KitManager km = KitManager.getManager();

        km.registerKit(new PvP());
        km.registerKit(new Grandpa());
    }
}
