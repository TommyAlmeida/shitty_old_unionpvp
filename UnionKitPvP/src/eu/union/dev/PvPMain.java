package eu.union.dev;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import eu.union.dev.commands.*;
import eu.union.dev.commands.location.*;
import eu.union.dev.commands.staff.AdminCMD;
import eu.union.dev.commands.staff.BuildCMD;
import eu.union.dev.commands.staff.GameModeCMD;
import eu.union.dev.engine.Kit;
import eu.union.dev.engine.handlers.ExpHandler;
import eu.union.dev.engine.managers.KitManager;
import eu.union.dev.engine.modules.AutoMessage;
import eu.union.dev.engine.storage.ConfigManager;
import eu.union.dev.engine.storage.Database;
import eu.union.dev.kits.heroic.*;
import eu.union.dev.listeners.PlayerListeners;
import eu.union.dev.listeners.ServerListeners;
import eu.union.dev.listeners.mechanics.GiveKitInArea;
import eu.union.dev.listeners.mechanics.JumpPad;
import eu.union.dev.listeners.mechanics.SoupListener;
import eu.union.dev.listeners.menus.KitMenu;
import eu.union.dev.listeners.menus.MainMenu;
import eu.union.dev.listeners.menus.StatsMenu;
import eu.union.dev.listeners.menus.WarpsMenu;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.sql.Connection;

public class PvPMain extends JavaPlugin {

    private static PvPMain instance;
    Database sql = new Database("root", "Be2Cj16M790EcI", "Profiles", "3306", "localhost");
    private Connection c;
    public ExpHandler exp = new ExpHandler();

    public void onEnable(){
        instance = this;

        //Handlers
        canConnect(true);
        ConfigManager.getInstance().setup(this);
        AutoMessage.getAPI().broadcast();
        exp.initializeLevels();
        registerKits();

        //Listeners
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new PlayerListeners(), this);
        pm.registerEvents(new ServerListeners(), this);
        pm.registerEvents(new KitMenu(), this);
        pm.registerEvents(new MainMenu(), this);
        pm.registerEvents(new StatsMenu(), this);
        pm.registerEvents(new SoupListener(), this);
        pm.registerEvents(new JumpPad(), this);
        pm.registerEvents(new GiveKitInArea(), this);
        pm.registerEvents(new WarpsMenu() ,this);
        pm.registerEvents(new AdminCMD() ,this);

        //Commands
        getCommand("kit").setExecutor(new KitCMD());
        getCommand("kits").setExecutor(new ListKitsCMD());
        getCommand("gm").setExecutor(new GameModeCMD());
        getCommand("build").setExecutor(new BuildCMD());
        getCommand("setspawn").setExecutor(new SetSpawn());
        getCommand("spawn").setExecutor(new Spawn());
        getCommand("stats").setExecutor(new StatsCMD());
        getCommand("recon").setExecutor(new ReconnectCMD());
        getCommand("setfps").setExecutor(new SetFPS());
        getCommand("fps").setExecutor(new FPS());
        getCommand("setlavachallenge").setExecutor(new SetLavaChallenge());
        getCommand("lavachallenge").setExecutor(new LavaChallenge());
        getCommand("setclick").setExecutor(new SetClick());
        getCommand("click").setExecutor(new Click());
        getCommand("warps").setExecutor(new Warps());
        getCommand("reset").setExecutor(new ResetStatsCMD());
        getCommand("admin").setExecutor(new AdminCMD());

        /**
         * Kits with runnables (in seconds)
         */
        new Madman().start();
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

        try {
            /* Pega todas as classes do pacote de kits */
            ImmutableSet<ClassPath.ClassInfo> kitClasses = ClassPath.from(getClassLoader())
                .getTopLevelClassesRecursive("eu.union.dev.kits");

            kitClasses.forEach(classInfo -> {
                try {
                    /* Carrega a classe do kit. */
                    Class<?> kitClass = classInfo.load();

                    /*  Cria uma nova instancia da classe carregada. */
                    Kit kit = (Kit) kitClass.newInstance();

                    /* Verifica se a classe é um Listener, e registra os eventos */
                    if (Listener.class.isAssignableFrom(kitClass)) {
                      getServer().getPluginManager().registerEvents((Listener) kit, this);
                    }

                    /* Registra o kit */
                    km.registerKit(kit);
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
          });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
