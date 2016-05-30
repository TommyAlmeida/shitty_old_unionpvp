package eu.union.dev.engine.modules;

import eu.union.dev.PvPMain;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.Random;

public class AutoMessage {

    private static AutoMessage ins = new AutoMessage();
    public ArrayList<String> messages = new ArrayList<>();
    public long interval = 20 * 85;
    private Random rand = new Random();
    private String prefix = "§e§lUnionPvP §r§7» ";

    public AutoMessage() {
        setMessages();
    }

    public static AutoMessage getAPI() {
        return ins;
    }

    private void setMessages() {
        messages.add("§7The server is in §c§lBETA");
        messages.add("§7Suggestions? §6www.unionnetwork.eu");
        messages.add("Follow us on twitter: §bhttps://twitter.com/UnionMcNetwork");
        messages.add("Every week §anew kits §7are added.");
        messages.add("Are you a youtuber? §6TAG minimum: §e1k subs");
        messages.add("§7A new §dgameplay §7experience just for u!");
    }

    private void pushMessages() {
        Bukkit.broadcastMessage(prefix + messages.get(rand.nextInt(messages.size())));
    }

    public void broadcast() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(PvPMain.getInstance(), () -> {
            pushMessages();
        }, 0, interval);
    }
}
