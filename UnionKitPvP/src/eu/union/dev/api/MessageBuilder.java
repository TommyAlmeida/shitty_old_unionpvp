package eu.union.dev.api;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class MessageBuilder {

    private String message;

    public MessageBuilder(String message) {
        this.message = "[\"\", {\"text\":\"" + message + "\"";
    }

    public void addCommandExecutor(String command) {
        this.message = message + ",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"" + command + "\"}";
    }

    public void addCommandSuggestion(String command) {
        this.message = message + ",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"" + command + "\"}";
    }

    public void addURL(String link) {
        this.message = message + ",\"clickEvent\":{\"action\":\"open_url\",\"value\":\"" + link + "\"}";
    }

    public void addHoverText(String... text) {
        String string = "";
        for (int i = 0; i < text.length; i++) {
            if (i + 1 == text.length) {
                string = string + text[i];
            } else {
                string = string + text[i] + "\n";
            }
        }
        this.message = message + ",\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"" + string + "\"}]}}";
    }

    public void send() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            IChatBaseComponent comp = IChatBaseComponent.ChatSerializer.a(this.message + "}]");
            PacketPlayOutChat packet = new PacketPlayOutChat(comp);
            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
        }
    }

    public void send(Player... player) {
        for (Player p : player) {
            IChatBaseComponent comp = IChatBaseComponent.ChatSerializer.a(this.message + "}]");
            PacketPlayOutChat packet = new PacketPlayOutChat(comp);
            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
        }
    }

}