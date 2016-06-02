package eu.union.dev.engine.managers;

import eu.union.dev.engine.KPlayer;
import eu.union.dev.engine.modules.Aura;
import eu.union.dev.utils.globals.Messages;
import eu.union.dev.utils.globals.Util;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class AuraManager {

    public Aura aura;
    public List<Aura> auras = new ArrayList<>();

    private String prefix = Messages.PREFIX.toString();

    public void registerAura(Aura aura){
        auras.add(aura);
    }

    public void unregisterAura(Aura aura){
        auras.remove(aura);
    }

    public void applyAura(Player player, Aura aura){
        if(!player.hasPermission(aura.getPermission())){
            player.sendMessage(prefix + " §cYou dont have permission to use this aura");
            return;
        }

        aura.apply(player);
    }

    public boolean purchaseAura(Player player, Aura aura){
        KPlayer kPlayer = PlayerManager.getPlayer(player.getUniqueId());

        if(kPlayer.getCoins() < aura.getCost()){
            player.sendMessage(prefix + " §cYou dont have §ecoins §7to buy this aura");
            return false;
        }else{
            Util.getInstance().addPermission(player.getName(), aura.getPermission());
            return true;
        }
    }
}
