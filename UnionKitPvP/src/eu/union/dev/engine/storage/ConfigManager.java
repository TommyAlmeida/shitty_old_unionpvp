package eu.union.dev.engine.storage;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;

import java.io.File;
import java.io.IOException;

public class ConfigManager {

    public ConfigManager() {
    }

    static ConfigManager instance = new ConfigManager();

    public static ConfigManager getInstance() {
        return instance;
    }

    Plugin p;

    FileConfiguration config;
    File cfile;

    FileConfiguration data;
    File dfile;

    public void setup(Plugin p) {
        cfile = new File(p.getDataFolder(), "config.yml");
        config = p.getConfig();
        // config.options().copyDefaults(true);
        // saveConfig();

        if (!p.getDataFolder().exists()) {
            p.getDataFolder().mkdir();
        }

        if (!cfile.exists()) {
            try {
                cfile.createNewFile();
            } catch (IOException e) {
                Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create data.yml!");
            }
        }
    }

    public FileConfiguration getData() {
        return data;
    }

    public void saveData() {
        try {
            data.save(dfile);
        } catch (IOException e) {
            Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save data.yml!");
        }
    }

    public void reloadData() {
        data = YamlConfiguration.loadConfiguration(dfile);
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public void saveConfig() {
        try {
            config.save(cfile);
        } catch (IOException e) {
            Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save config.yml!");
        }
    }

    public void setLocation(String path, Location value) throws IOException {
        config.set(path + ".world", value.getWorld().getName());
        config.set(path + ".x", value.getX());
        config.set(path + ".y", value.getY());
        config.set(path + ".z", value.getZ());
        config.set(path + ".yaw", value.getYaw());
        config.set(path + ".pitch", value.getPitch());

        saveConfig();
    }

    public Location getLocation(String path){
        String world = config.getString(path + ".world");
        double x = config.getDouble(path + ".x");
        double y = config.getDouble(path + ".y");
        double z = config.getDouble(path + ".z");
        double yaw = config.getDouble(path + ".yaw");
        double pitch = config.getDouble(path + ".pitch");

        Location loc = new Location(Bukkit.getWorld(world),x,y,z);
        loc.setYaw((float) yaw);
        loc.setPitch((float) pitch);

        return loc;
    }


    public void reloadConfig() {
        config = YamlConfiguration.loadConfiguration(cfile);
    }

    public PluginDescriptionFile getDesc() {
        return p.getDescription();
    }

}