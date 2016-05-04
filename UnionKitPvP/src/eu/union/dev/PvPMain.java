package eu.union.dev;

import eu.union.dev.commands.KitCMD;
import eu.union.dev.commands.ListKitsCMD;
import eu.union.dev.commands.staff.BuildCMD;
import eu.union.dev.commands.staff.GameModeCMD;
import eu.union.dev.engine.managers.KitManager;
import eu.union.dev.engine.storage.Database;
import eu.union.dev.kits.Archer;
import eu.union.dev.kits.Grandpa;
import eu.union.dev.kits.PvP;
import eu.union.dev.listeners.PlayerListeners;
import eu.union.dev.listeners.ServerListeners;
import eu.union.dev.listeners.mechanics.SoupListener;
import eu.union.dev.listeners.menus.KitMenu;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;

public class PvPMain extends JavaPlugin {

    private static PvPMain instance;
    Database sql = new Database("root", "Be2Cj16M790EcI", "Profiles", "3306", "localhost");
    private Connection c;

    public void onEnable(){
        registerKits();

        instance = this;
        canConnect(true);

        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new PlayerListeners(), this);
        pm.registerEvents(new ServerListeners(), this);
        pm.registerEvents(new KitMenu(), this);
        pm.registerEvents(new SoupListener(), this);

        getCommand("kit").setExecutor(new KitCMD());
        getCommand("kits").setExecutor(new ListKitsCMD());
        getCommand("gm").setExecutor(new GameModeCMD());
        getCommand("build").setExecutor(new BuildCMD());
    }

    @Override
    public void onDisable() {
        canConnect(false);
    }

    public static PvPMain getInstance() {
        return instance;
    }

    public void canConnect(boolean can){
        if(!can){
            if(c != null){
                c = sql.close(c);
            }
        }else{
            try{
                sql.open();
                this.c = sql.getConnection();
                sql.setupTables();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public Database getSQL() {
        return sql;
    }

    /**
     * Register all kits
     */
    private void registerKits(){
        KitManager km = KitManager.getManager();

        km.registerKit(new PvP());
        km.registerKit(new Grandpa());
        km.registerKit(new Archer());
    }
}
