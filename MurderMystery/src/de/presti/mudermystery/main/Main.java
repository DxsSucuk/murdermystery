package de.presti.mudermystery.main;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Random;

import javax.net.ssl.HttpsURLConnection;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.NameTagVisibility;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import de.presti.coinapi.CoinAPI;
import de.presti.mudermystery.command.ForceMap;
import de.presti.mudermystery.command.MurderMysteryAdminCommand;
import de.presti.mudermystery.command.Start;
import de.presti.mudermystery.command.Stats;
import de.presti.mudermystery.listener.ASyncChat;
import de.presti.mudermystery.listener.BlockEvent;
import de.presti.mudermystery.listener.DamageEvent;
import de.presti.mudermystery.listener.DropEvent;
import de.presti.mudermystery.listener.InteractEvent;
import de.presti.mudermystery.listener.Join;
import de.presti.mudermystery.listener.Leave;
import de.presti.mudermystery.listener.MoveEvent;
import de.presti.mudermystery.listener.PickUPEvent;
import de.presti.mudermystery.scoreboard.ScoreboardManager;
import de.presti.mudermystery.utils.GameInfo;
import de.presti.mudermystery.utils.GameUtils;
import de.presti.mudermystery.utils.LocationUtils;
import de.presti.mudermystery.utils.MySQL;
import de.presti.statsapi.StatsAPI;
import net.minecraft.server.v1_8_R3.EntityPlayer;

public class Main extends JavaPlugin {

	public BukkitTask taskid;
	public LocationUtils location;
	public MySQL sql;
	public static Main instance;
	public static CoinAPI coins;
	public static StatsAPI stats;
	public static HttpsURLConnection input;
	public static Team hidden;
	public static String[] maps = new String[] { "Headquarters", "Minecraft" , "Lagune"};

	public void onEnable() {
		instance = this;
		createScoreboard();
		startChecker();
		GameInfo.setMap(maps[new Random().nextInt(maps.length)]);
		try {
			sql = new MySQL("mm", "3sobP4H3qFXjDsSa", "localhost", "mm", 3306, instance);
			coins = new CoinAPI("coins", "YqISsNqjQuRcpTny", "localhost", "coins");
			stats = new StatsAPI("stats", "CqhCF3OL4bz5kOBk", "localhost", "stats");
			location = new LocationUtils();
		} catch (Exception e) {
			System.out.println("MySQL Connection Error: " + e.getMessage());
		}
		register();
		getServer().getMessenger().registerOutgoingPluginChannel(instance, "BungeeCord");
	}

	public void onDisable() {
		sql.close();
		coins.sql.close();
		stats.sql.close();
	}
	
	public void createScoreboard() {
		org.bukkit.scoreboard.ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard board = manager.getNewScoreboard();
		hidden = board.registerNewTeam("Hidden");
		hidden.setNameTagVisibility(NameTagVisibility.NEVER);
	}

	public void register() {
		getCommand("start").setExecutor(new Start());
		getCommand("mm").setExecutor(new MurderMysteryAdminCommand());
		getCommand("forcemap").setExecutor(new ForceMap());
		getCommand("stats").setExecutor(new Stats());

		Bukkit.getPluginManager().registerEvents(new BlockEvent(), instance);
		Bukkit.getPluginManager().registerEvents(new DamageEvent(), instance);
		Bukkit.getPluginManager().registerEvents(new Join(), instance);
		Bukkit.getPluginManager().registerEvents(new Leave(), instance);
		Bukkit.getPluginManager().registerEvents(new PickUPEvent(), instance);
		Bukkit.getPluginManager().registerEvents(new DropEvent(), instance);
		Bukkit.getPluginManager().registerEvents(new MoveEvent(), instance);
		Bukkit.getPluginManager().registerEvents(new InteractEvent(), instance);
		Bukkit.getPluginManager().registerEvents(new ASyncChat(), instance);
	}

	public static Main getInstance() {
		return instance;
	}

	public String[] getFromPlayer(Player p) {
		EntityPlayer nms = ((CraftPlayer) p).getHandle();
		GameProfile profile = nms.getProfile();
		Property prop = profile.getProperties().get("textures").iterator().next();
		String texture = prop.getValue();
		String sig = prop.getSignature();
		return new String[] { texture, sig };
	}

	public static void setInstance(Main instance) {
		Main.instance = instance;
	}

	public void startChecker() {
		taskid = Bukkit.getScheduler().runTaskTimer(getInstance(), new Runnable() {
			int lastsend = 20;

			@Override
			public void run() {
				for (Player all : Bukkit.getOnlinePlayers()) {
					ScoreboardManager.update(all);
				}
				if (Bukkit.getOnlinePlayers().size() >= 8) {
					if (GameInfo.getCountdown() == 60) {
						Bukkit.broadcastMessage(
								Data.prefix + "§7Das Spiel startet in §e" + GameInfo.getCountdown() + " §7Sekunden");
					} else if (GameInfo.getCountdown() == 30) {
						Bukkit.broadcastMessage(
								Data.prefix + "§7Das Spiel startet in §e" + GameInfo.getCountdown() + " §7Sekunden");
					} else if (GameInfo.getCountdown() <= 15 && GameInfo.getCountdown() > 1) {
						Bukkit.broadcastMessage(
								Data.prefix + "§7Das Spiel startet in §e" + GameInfo.getCountdown() + " §7Sekunden");
					} else if (GameInfo.getCountdown() == 1) {
						Bukkit.broadcastMessage(Data.prefix + "§7Das Spiel startet in §eeiner §7Sekunde");
					} else if (GameInfo.getCountdown() <= 0) {
						GameUtils.start();
						Bukkit.getScheduler().cancelTask(taskid.getTaskId());
					}
					GameInfo.countdown--;
				} else {
					GameInfo.countdown = 60;
					if (lastsend <= 0) {
						if (Bukkit.getOnlinePlayers().size() < 7) {
							Bukkit.broadcastMessage(Data.prefix + "§7Warten auf §e"
									+ (8 - (Bukkit.getOnlinePlayers().size())) + " §7weiteren Spielern.");
						} else {
							Bukkit.broadcastMessage(Data.prefix + "§7Warten auf §eeinen §7weiteren Spieler.");
						}
						lastsend = 20;
					}
				}
				lastsend--;
			}
		}, 0L, 20L);
	}

	public void connect(Player p, String server) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		DataOutputStream data = new DataOutputStream(out);
		try {
			data.writeUTF("Connect");
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			data.writeUTF(server);
		} catch (IOException e) {
			e.printStackTrace();
		}
		p.sendPluginMessage(instance, "BungeeCord", out.toByteArray());
	}
}
