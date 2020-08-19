package de.presti.mudermystery.utils;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;

import de.dytanic.cloudnet.ext.bridge.bukkit.BukkitCloudNetHelper;
import de.presti.mudermystery.main.Data;
import de.presti.mudermystery.main.Main;
import de.presti.mudermystery.scoreboard.ScoreboardManager;

public class GameUtils {
	public static BukkitTask task;
	public static BukkitTask task2;
	public static BukkitTask task3;
	private static boolean counterstarted = false;
	public static ArrayList<Integer> used = new ArrayList<>();
	public static ArmorsUtils as;
	public static ArrayList<NPC> npcs = new ArrayList<NPC>();

	public static void getreqt(Player p) {
		try {
			for (int i = 1; i < 25; i++) {
				npcs.add(new NPC("§6Gewinner!", GameInfo.getPlayerSpawn(GameInfo.getMap(), i), p));
			}

			for (int i = 0; i < npcs.size(); i++) {
				npcs.get(i).spawn();
			}

			Bukkit.getScheduler().runTaskTimer(Main.getInstance(), new Runnable() {
				int value = 0;

				@Override
				public void run() {
					if (value == 0) {
						for (int i = 0; i < npcs.size(); i++) {
							npcs.get(i).sneak(true);
						}
						value++;
					} else if (value >= 1) {
						for (int i = 0; i < npcs.size(); i++) {
							npcs.get(i).sneak(false);
						}
						value = 0;
					}
				}
			}, 0L, 20L);
		} catch (Exception e) {
		}
	}

	public static void start() {

		if (Bukkit.getOnlinePlayers().size() < 2) {
			Bukkit.shutdown();
		}

		if (GameInfo.isEvent()) {
			Bukkit.broadcastMessage(Data.prefix + GameInfo.getEvents());
		}

		if (!GameInfo.isRunning()) {
			if (Bukkit.getOnlinePlayers().size() >= 2) {
				BukkitCloudNetHelper.changeToIngame();
			}
		}
		GameInfo.setRunning(true);

		if (!GameInfo.forced && GameInfo.detectiv == null) {
			GameInfo.detectiv = getRandom();
		}

		if (!GameInfo.forcem  && GameInfo.murder == null) {
			GameInfo.murder = getRandom();
		}

		if (GameInfo.detectiv == GameInfo.murder && GameInfo.forced && GameInfo.forcem) {
			int i = new Random().nextInt(1);
			if (i == 1) {
				GameInfo.detectiv = getRandom();
			} else {
				GameInfo.murder = getRandom();
			}
		}

		if (GameInfo.isHorror() && GameInfo.m2 == null) {
			GameInfo.m2 = getRandom();
		}

		if (GameInfo.isTatutata()  && GameInfo.d2 == null) {
			GameInfo.d2 = getRandom();
		}

		GameInfo.dbowloc = GameInfo.getSpawn();
		for (Player all : Bukkit.getOnlinePlayers()) {

			if (Main.hidden != null) {
				if (!Main.hidden.hasEntry(all.getName())) {
					Main.hidden.addEntry(all.getName());
				} else {
					System.out.println("Bissel was Falsch oder?");
				}
			} else {
				System.out.println("Hidden Team is Null!");
			}

			GameUtils.setInv(all);
			if (all == GameInfo.murder || all == GameInfo.getM2()) {
				all.sendMessage(Data.prefix + "Du bist der §cMörder§e.");
			} else if (all == GameInfo.getDetectiv() || all == GameInfo.getD2()) {
				all.sendMessage(Data.prefix + "Du bist der §bDetektiv§e.");
			} else {
				all.sendMessage(Data.prefix + "Du bist ein §aUnschuldiger§e.");
			}

			ScoreboardManager.sendToPlayer(all);
		}

		if (GameInfo.horror) {
			GameInfo.murder.sendMessage(Data.prefix + "Dein Partner ist: §c" + GameInfo.m2.getName());
			GameInfo.m2.sendMessage(Data.prefix + "Dein Partner ist: §c" + GameInfo.murder.getName());
		}

		if (GameInfo.tatutata) {
			GameInfo.detectiv.sendMessage(Data.prefix + "Dein Partner ist: §b" + GameInfo.d2.getName());
			GameInfo.d2.sendMessage(Data.prefix + "Dein Partner ist: §b" + GameInfo.detectiv.getName());
		}

		startDelayInvset();
		startGoldSpawn();
		startDetectivBowParticle();
		gameendtimer();
		as = new ArmorsUtils(GameInfo.getSpawn());
		for (Player all : Bukkit.getOnlinePlayers()) {

			teleport(all);

		}

		for (Player all : Bukkit.getOnlinePlayers()) {
			if (all.getWorld().equals(GameInfo.getSpawn().getWorld())) {
				teleport2(all);
			}
		}

		events();
	}

	public static void events() {
		if (GameInfo.speed) {
			for (Player all : Bukkit.getOnlinePlayers()) {
				all.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 1000000, GameInfo.speedlvl));
			}
		}
	}

	public static void teleport(Player all) {
		int spawn = new Random().nextInt(16);

		if (spawn == 0 || used.contains(spawn)) {
			return;
		}
		if (!used.contains(spawn) && spawn != 0) {
			all.teleport(GameInfo.getPlayerSpawn(GameInfo.getMap(), spawn));
			used.add(spawn);
		}
	}

	static int spawn3 = 17;

	public static void teleport2(Player all) {

		all.teleport(GameInfo.getPlayerSpawn(GameInfo.getMap(), spawn3));
		spawn3++;
	}

	public static void startDelayInvset() {
		task3 = Bukkit.getScheduler().runTaskTimer(Main.getInstance(), new Runnable() {

			int countdown = 15;

			@Override
			public void run() {
				if (countdown == 0) {
					for (Player all : Bukkit.getOnlinePlayers()) {
						if (all == GameInfo.murder || all == GameInfo.getM2()) {
							all.getInventory().setItem(0, GameInfo.getMurderSword());
						} else if (all == GameInfo.getDetectiv() || all == GameInfo.getD2()) {
							all.getInventory().setItem(0, GameInfo.getDetektivBow());
							all.getInventory().setItem(8, new ItemStack(Material.ARROW));
						} else {
							all.sendMessage(Data.prefix + "Du bist ein §aUnschuldiger§e.");
						}
						all.sendMessage(Data.prefix + "Der Mörder hat nun seine Klinge!");
					}
					Bukkit.getScheduler().cancelTask(task3.getTaskId());
				} else {
					if (countdown == 15 || countdown == 10 || countdown <= 5) {
						for (Player all : Bukkit.getOnlinePlayers()) {
							all.sendMessage(
									Data.prefix + "Der Mörder kriegt seine Klinge in " + countdown + " sekunden!");
						}
					}
					countdown--;
				}
			}
		}, 0L, 20L);
	}

	public static void startDetectivBowParticle() {
		Bukkit.getScheduler().runTaskTimer(Main.getInstance(), new Runnable() {

			@Override
			public void run() {
				if (Bukkit.getOnlinePlayers() != null) {
					if (GameInfo.dbowloc == null) {
						GameInfo.dbowloc = GameInfo.getSpawn();
					}
					for (Player all : Bukkit.getOnlinePlayers()) {
						if (all != null) {
							all.playEffect(GameInfo.dbowloc, Effect.LAVA_POP, 1);
							all.playEffect(GameInfo.dbowloc, Effect.LAVA_POP, 1);
							all.playEffect(GameInfo.dbowloc, Effect.LAVA_POP, 1);
							all.playEffect(GameInfo.dbowloc, Effect.LAVA_POP, 1);
						}
					}
				}
			}
		}, 0L, 20L);
	}

	public static void setInv(Player p) {
		p.getInventory().clear();
		if (GameInfo.isRunning()) {
			if (p.getGameMode().equals(GameMode.ADVENTURE)) {
				p.getInventory().setItem(0, SetItems.build(Material.COMPASS, "§2Teleportier dich zu Spielern!"));
				p.getInventory().setItem(8, SetItems.build(Material.SLIME_BALL, "§cLobby"));
			}
		} else {
			if (p.getGameMode().equals(GameMode.ADVENTURE)) {
				p.getInventory().setItem(0, SetItems.build(Material.COMPASS, "§2Teleportier dich zu Spielern!"));
				p.getInventory().setItem(8, SetItems.build(Material.SLIME_BALL, "§cLobby"));
			} else {
				if (p.hasPermission("system.inhaber") || p.hasPermission("system.presti")) {
					p.getInventory().setItem(2, SetItems.build(Material.TNT, "§cEvents"));
				}
				if (p.hasPermission("mm.start") || p.hasPermission("mm.*")) {
					p.getInventory().setItem(0, SetItems.build(Material.DIAMOND, "§6Start"));
				}
				if (p.hasPermission("mm.forcemap") || p.hasPermission("mm.*")) {
					p.getInventory().setItem(1, SetItems.build(Material.BOOK, "§2Map auswählen"));
				}
				p.getInventory().setItem(8, SetItems.build(Material.SLIME_BALL, "§cLobby"));
			}
		}
	}

	public static void startGoldSpawn() {
		Bukkit.getScheduler().runTaskTimer(Main.getInstance(), new Runnable() {

			@Override
			public void run() {
				for (int i = 1; i < 16; i++) {
					Bukkit.getWorld(GameInfo.getMap()).dropItem(GameInfo.getItemSpawn(GameInfo.getMap(), i),
							new ItemStack(Material.GOLD_INGOT));
				}
			}
		}, 60L, 1200L);
	}

	public static void gameendtimer() {
		if (task2 == null && !counterstarted) {
			counterstarted = true;
			task2 = Bukkit.getScheduler().runTaskTimer(Main.getInstance(), new Runnable() {

				@Override
				public void run() {
					if (GameInfo.zeit == 0) {
						for (Player all : Bukkit.getOnlinePlayers()) {
							ScoreboardManager.update(all);
							all.sendMessage(Data.prefix
									+ "Das Spiel ist beendet! Der §bDetektiv §eund die §aUnschuldigen §ehaben den §cMörder §egefunden.");
						}
						if (GameInfo.detectiv != null && GameInfo.detectiv.getGameMode().equals(GameMode.SURVIVAL)) {
							stop(GameInfo.detectiv);
						} else {
							stop(getRandom());
						}
						GameInfo.zeit--;
					} else if (GameInfo.zeit < 0) {

					} else {
						for (Player all : Bukkit.getOnlinePlayers()) {
							ScoreboardManager.update(all);
						}
						GameInfo.zeit--;
					}
				}
			}, 0L, 20L);
		}
	}

	static ArrayList<Player> spieler = new ArrayList<>();

	public static Player getRandom() {
		if (spieler.isEmpty()) {
			for (Player all : Bukkit.getOnlinePlayers()) {
				spieler.add(all);
			}
		}

		int size = spieler.size();
		int zufall = new Random().nextInt(size) + 0;

		Player k = (Player) spieler.get(zufall);

		if (k == GameInfo.d2 || k == GameInfo.detectiv || k == GameInfo.m2 || k == GameInfo.getMurder()) {
			k = spieler.get(zufall);
		}

		spieler.remove(k);

		return k;
	}

	/*
	 * public void playerThrow(Player player) {
	 * 
	 * ItemStack sword = player.getItemInHand(); ItemStack throwStack = new
	 * ItemStack(sword); throwStack.setAmount(1); int amt = sword.getAmount();
	 * Location pLoc = player.getEyeLocation();
	 * 
	 * Item tsword = player.getWorld().dropItem(pLoc, throwStack);
	 * tsword.setVelocity(pLoc.getDirection()); sword.setAmount(amt - 1);
	 * player.setItemInHand(sword); }
	 */

	public static void stop(Player winner) {
		if (GameInfo.isRunning() && !GameInfo.ended) {
			// getreqt(winner);
			GameInfo.setRunning(false);
			GameInfo.ended = true;
			for (Player all : Bukkit.getOnlinePlayers()) {
				ScoreboardManager.removeScoreboard(all);
			}

			for (Player all : Bukkit.getOnlinePlayers()) {
				all.setGameMode(GameMode.ADVENTURE);
				all.getActivePotionEffects().clear();
			}

			if (task == null) {
				task = Bukkit.getScheduler().runTaskTimer(Main.getInstance(), new Runnable() {
					int countdown = 15;

					@Override
					public void run() {
						if (countdown == 15) {
							Bukkit.broadcastMessage(Data.prefix + "§7Der Server startet in §e" + countdown + "s §7neu");
						} else if (countdown <= 10 && countdown > 0) {
							Bukkit.broadcastMessage(Data.prefix + "§7Der Server startet in §e" + countdown + "s §7neu");
						} else if (countdown == 0) {
							for (Player all : Bukkit.getOnlinePlayers()) {
								Main.instance.connect(all, "Lobby-1");
							}
							Bukkit.getServer().shutdown();
						}
						countdown--;
					}
				}, 0L, 20L);
			}
		}
	}

}
