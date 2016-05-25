package eu.union.dev.engine.modules;

import eu.union.dev.PvPMain;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.Random;

public class AutoMessage {

    private static AutoMessage ins = new AutoMessage();
    private Random rand = new Random();

    private String prefix = "§e§lUnionNetwork §r§7» ";
    public ArrayList<String> messages = new ArrayList<String>();
    public long interval = 20000 / 30;

    private void setMessages(){
        messages.add("§cThe server is in beta, sorry for the bugs!");
        messages.add("Report? §bwww.unionnetwork.eu");
        messages.add("Every week §anew kits §7are implemented");
    }

    private void pushMessages(){
        Bukkit.broadcastMessage(prefix + messages.get(rand.nextInt(messages.size())));
    }

    public void broadcast(){
        Bukkit.getScheduler().scheduleSyncRepeatingTask(PvPMain.getInstance(), () -> {
            pushMessages();
        },0,interval);
    }
    public static AutoMessage getAPI() {
        return ins;
    }
}
