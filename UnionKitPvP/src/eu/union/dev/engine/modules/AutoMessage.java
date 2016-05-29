package eu.union.dev.engine.modules;

import eu.union.dev.PvPMain;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.Random;

public class AutoMessage {

    private static AutoMessage ins = new AutoMessage();
    private Random rand = new Random();

    private String prefix = "§e§lUnionNetwork §r§7» ";
    public ArrayList<String> messages = new ArrayList<>();

    public long interval = 20000 / 100;

    public AutoMessage() {
      setMessages();
    }

    private void setMessages(){
        messages.add("§7The server is in §c§lBETA");
        messages.add("Report a player? §bwww.unionnetwork.eu");
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
