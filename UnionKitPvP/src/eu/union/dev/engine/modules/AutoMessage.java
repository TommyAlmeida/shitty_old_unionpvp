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
        messages.add("§7Suggestions? §6www.unionnetwork.eu");
        messages.add("Ask us some questions: §bhttps://twitter.com/UnionMcNetwork");
        messages.add("§6Union-HG §7is coming very soon :D");
        messages.add("Are you a youtuber? §6TAG minimum: §e1k subs");
        messages.add("§7A new §dgameplay §7experience!");
        messages.add("§7How do i get level? §cBy killing players.");
        messages.add("§7Bugs? Sorry we are in §cearly development stage!");
        messages.add("§7Want to be TMOD? Apply for it on: §chttps://goo.gl/ciYLSc");
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
