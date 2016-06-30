package eu.union.dev.engine;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;

public class ConfigYML implements Listener {

    static ConfigYML instance = new ConfigYML();

    public static ConfigYML getInstance() {
        return instance;
    }

    Plugin p;

    static FileConfiguration data;
    static File dfile;

    public static void setup(Plugin p) {
        dfile = new File(p.getDataFolder(), "config.yml");

        if (!dfile.exists()) {
            try {
                dfile.createNewFile();
            } catch (IOException e) {
            }
        }
        data = YamlConfiguration.loadConfiguration(dfile);
    }

    public static FileConfiguration ficheiro() {
        return data;
    }

    public static void salvar() {
        try {

            data.save(dfile);

        } catch (IOException e) {
            Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save config.yml!");
        }
    }

    public static void reload() {

        data = YamlConfiguration.loadConfiguration(dfile);
    }

    public PluginDescriptionFile desc() {
        return p.getDescription();
    }
}

