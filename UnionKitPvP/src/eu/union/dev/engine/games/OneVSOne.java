package eu.union.dev.engine.games;

import eu.union.dev.utils.chatUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * Created by Josue on 04/07/2016.
 */
public class OneVSOne {

    private Location SpawnPoint1;
    private Location SpawnPoint2;
    private Player p1;
    private Player p2;

    public OneVSOne(Player p1, Player p2, Location sp1, Location sp2)
    {
        this.p1 = p1;
        this.p2 = p2;
        this.SpawnPoint1 = sp1;
        this.SpawnPoint2 = sp2;
    }

    public void startMatch()
    {
        this.p1.teleport(this.SpawnPoint1);
        this.p2.teleport(this.SpawnPoint2);

        for (Player p : Bukkit.getOnlinePlayers())
        {
            //Esconder todos os players
            p1.hidePlayer(p);
            p2.hidePlayer(p);
        }

        //Mostrar o Adversario
        p1.showPlayer(p2);
        p2.showPlayer(p1);

        String start = " &CQue a batalha comece!";

        p1.sendMessage(chatUtil.color(start));
        p2.sendMessage(chatUtil.color(start));
    }

}
