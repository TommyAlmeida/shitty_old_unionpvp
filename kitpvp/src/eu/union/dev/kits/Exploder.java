package eu.union.dev.kits;

import eu.union.dev.engine.Kit;
import eu.union.dev.utils.Perms;
import eu.union.dev.utils.Weapon;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Created by Samuel on 01/05/2016.
 */

public class Exploder extends Kit implements Listener {

    public Exploder() {
        super("grandpa", Perms.KIT_EXPLODER.toString(), Difficulty.MEDIUM,
                "Do you want explode everything?",
                "use the tnt and have fun ;)"
        );
    }

    @Override
    public void applyKit(Player player) {

        Weapon.giveWeapon(player,Weapon.DEFAULT_SWORD);
        Weapon.giveWeapon(player,Weapon.EXPLODER_TNT);
    }

    @EventHandler
    public void onPlayerUseTnt(PlayerInteractEvent e) {
        Player p = e.getPlayer();

        if(!(e.getItem().getType() == Material.TNT)) {
            return;
        }

        if(e.getItem().getItemMeta() == null) {
            return;
        }



    }

}
