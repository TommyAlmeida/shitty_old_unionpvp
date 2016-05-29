package eu.union.dev.kits.epic;

import java.util.concurrent.TimeUnit;

import eu.union.dev.api.Icon;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import eu.union.dev.PvPMain;
import eu.union.dev.api.Ability;
import eu.union.dev.engine.Kit;
import eu.union.dev.engine.managers.KitManager;
import eu.union.dev.utils.Util;
import eu.union.dev.utils.Weapon;


public class IceCube extends Kit implements Listener {

	private Ability cd = new Ability(1, 12, TimeUnit.SECONDS);
	private final int[][] cubeOffsets = {
		{0, 1, 1},
		{1, 1, 0},
		{1, 0, 0},
		{0, 0, 1},
		{0, 0, -1},
		{-1, 0, 0},
		{0, 2, 1},
		{1, 2, 0},
		{0, 2, -1},
		{-1, 2, 0},

		{-1, 2, 1},
		{-1, 1, 1},
		{-1, 0, 1},

		{1, 2, -1},
		{1, 1, -1},
		{1, 0, -1},

		{1, 2, 1},
		{1, 1, 1},
		{1, 0, 1},

		{-1, 2, -1},
		{-1, 1, -1},
		{-1, 0, -1},

		{0, 2, 0},

		{-1, 1, 0},
		{0, 1, -1}
	};

	public IceCube() {
		super("icecube", "unkit.icecube", Difficulty.LOW, Rarity.EPIC, 0, new Icon(Material.ICE), Category.LONG_DISTANCE);
	}

	public void applyKit(Player player) {
		Weapon.giveWeapon(player, Weapon.DEFAULT_SWORD);
		Weapon.giveWeapon(player, Weapon.ICECUBE_ITEM,1);
	}

	public void cubeCreate(Player c) {
		/*
		 * A localização precisa ser "cloneada" pois caso o jogador
		 * se movimente o gelo não sera removido.
		*/
		final Location loc = c.getLocation().clone();
		final World world = c.getWorld();

		for (int[] cubeOffset : cubeOffsets) {
			int x = cubeOffset[0];
			int y = cubeOffset[1];
			int z = cubeOffset[2];
			/*
			 * Apenas definir como ICE, caso o bloco seja AIR
			 * para evitar Grief no mapa.
			 */
			Block b = world.getBlockAt(loc.getBlockX() + x, loc.getBlockY() + y, loc.getBlockZ() + z);
			if (b.getType() == Material.AIR) {
				b.setType(Material.ICE);
			}
		}

		c.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 124, 3));
		c.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 124, 4));

		c.sendMessage("§aDAMN, IM FREEZING!...");

		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(PvPMain.getInstance(), () -> {
			for (int[] cubeOffset : cubeOffsets) {
				int x = cubeOffset[0];
				int y = cubeOffset[1];
				int z = cubeOffset[2];
				Block b = world.getBlockAt(loc.getBlockX() + x, loc.getBlockY() + y, loc.getBlockZ() + z);
				if (b.getType() == Material.ICE) {
					b.setType(Material.AIR);
				}
			}
		}, 100L);

		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(PvPMain.getInstance(), () -> {
			world.playEffect(c.getLocation().clone().add(2.0D, 0.0D, 0.0D), Effect.STEP_SOUND, Material.ICE);
			//c.getLocation().getWorld().playEffect(c.getLocation().add(2.0D, 2.0D, 0.0D), Effect.MAGIC_CRIT, Material.ICE); // causes NPE
			world.playEffect(c.getLocation().clone().add(0.0D, 0.0D, 2.0D), Effect.STEP_SOUND, Material.ICE);
			world.playEffect(c.getLocation().clone().add(0.0D, 2.0D, 2.0D), Effect.STEP_SOUND, Material.ICE);
		}, 80L);
	}

	@EventHandler
	public void dano(EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof Player && e.getDamager() instanceof Snowball
			&& ((Snowball) e.getDamager()).getShooter() instanceof Player) {
			Player c = (Player) e.getEntity();

			Player p1 = (Player) ((Snowball) e.getDamager()).getShooter();
			KitManager km = KitManager.getManager();
			if (km.getKitAmIUsing(p1, "icecube")) {
				e.setDamage(e.getDamage() + 3.0D);

				// CRIAR UMA ARENA DE GELO
				cubeCreate(c);
			}
		}
	}

	@EventHandler
	public void onInteragir(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		KitManager km = KitManager.getManager();

		if (p.getItemInHand().getType() == Material.PACKED_ICE && km.getKitAmIUsing(p, "icecube")) {
			if (cd.tryUse(p)) {
				//Vector velo2 = new Vector(0.3, 0.5, 0.3);
				p.launchProjectile(Snowball.class)/*.setVelocity(velo2);*/;
			} else {
				Util.getInstance().sendCooldownMessage(p, cd, TimeUnit.SECONDS, true);
			}
		}
	}

}
