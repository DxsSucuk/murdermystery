package de.presti.mudermystery.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import de.presti.mudermystery.main.Main;
import de.presti.mudermystery.utils.GameInfo;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

public class ScoreboardManager {
	public static HashMap<Player, PacketScoreboard> scoreboards = new HashMap();
	static int counter = 0;
	private static BukkitTask bukkitTask;

	public static void sendToPlayer(Player player) {
		final PacketScoreboard packetScoreboard = new PacketScoreboard(player);
		packetScoreboard.remove();
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
			public void run() {
				ScoreboardManager.scoreboards.put(player, packetScoreboard);

				if (GameInfo.isRunning()) {
					packetScoreboard.sendSidebar("§e§lMURDER MYSTERY");

					packetScoreboard.setLine(Integer.valueOf(12), " ");
					packetScoreboard.setLine(Integer.valueOf(1), "§6www.shrubby.eu");

					packetScoreboard.setLine(Integer.valueOf(11), "§rRolle: " + GameInfo.getRolle(player));
					packetScoreboard.setLine(Integer.valueOf(10), "§e            ");
					packetScoreboard.setLine(Integer.valueOf(9), "§rUnschuldige: §a" + GameInfo.getInnoa());
					packetScoreboard.setLine(Integer.valueOf(8),
							"§rZeit: §a" + GameInfo.zeitmin() + ":" + GameInfo.zeitsek());
					packetScoreboard.setLine(Integer.valueOf(7), "§9          ");
					packetScoreboard.setLine(Integer.valueOf(6), "§rDetektiv: " + GameInfo.getDetectivstatus());
					packetScoreboard.setLine(Integer.valueOf(5), "§5         ");
					packetScoreboard.setLine(Integer.valueOf(4), "§6        ");
					packetScoreboard.setLine(Integer.valueOf(3), "§rKarte: §a" + GameInfo.getMap());

					packetScoreboard.setLine(Integer.valueOf(2), "§r    ");
				} else if (GameInfo.ended) {
					packetScoreboard.remove();
				} else {
					packetScoreboard.sendSidebar("§e§lMURDER MYSTERY");

					packetScoreboard.setLine(Integer.valueOf(7), " ");
					packetScoreboard.setLine(Integer.valueOf(2), ""); // Score, Wert

					packetScoreboard.setLine(Integer.valueOf(6), "§rKarte: §e" + GameInfo.getMap());
					packetScoreboard.setLine(Integer.valueOf(5),
							"§rSpieler: §e" + Bukkit.getOnlinePlayers().size() + "/" + Bukkit.getMaxPlayers());
					packetScoreboard.setLine(Integer.valueOf(4), "  ");
					packetScoreboard.setLine(Integer.valueOf(3), "§rStart in §e" + GameInfo.getCountdown() + "s");
				}
			}
		}, 3L);
	}

	public static void removeScoreboard(Player player) {
		PacketScoreboard packetScoreboard = (PacketScoreboard) scoreboards.get(player);
		if ((packetScoreboard != null) && (packetScoreboard.displayed)) {
			packetScoreboard.remove();
		}
	}

	public static void update(Player player) {
		try {

			PacketScoreboard packetScoreboard = (PacketScoreboard) scoreboards.get(player);
			if (packetScoreboard != null) {
				if (GameInfo.isRunning()) {
					packetScoreboard.removeLine(Integer.valueOf(9));
					packetScoreboard.setLine(Integer.valueOf(9), "§rUnschuldige: §a" + GameInfo.getInnoa());
					packetScoreboard.removeLine(Integer.valueOf(8));
					packetScoreboard.setLine(Integer.valueOf(8),
							"§rZeit: §a" + GameInfo.zeitmin() + ":" + GameInfo.zeitsek());
					packetScoreboard.removeLine(Integer.valueOf(6));
					packetScoreboard.setLine(Integer.valueOf(6), "§rDetektiv: " + GameInfo.getDetectivstatus());
				} else if(GameInfo.ended) {
					packetScoreboard.remove();
				} else {
					packetScoreboard.removeLine(Integer.valueOf(6));
					packetScoreboard.setLine(Integer.valueOf(6), "§rKarte: §e" + GameInfo.getMap());
					packetScoreboard.removeLine(Integer.valueOf(5));
					packetScoreboard.setLine(Integer.valueOf(5),
							"§rSpieler: §e" + Bukkit.getOnlinePlayers().size() + "/" + Bukkit.getMaxPlayers());
					packetScoreboard.removeLine(Integer.valueOf(3));
					packetScoreboard.setLine(Integer.valueOf(3), "§rStart in §e" + GameInfo.getCountdown() + "s");

				}
			}
		} catch (Exception e) {
		}

	}

	public static void updateonleave(Player player) {
		try {

			PacketScoreboard packetScoreboard = (PacketScoreboard) scoreboards.get(player);
			if (packetScoreboard != null) {
				if (GameInfo.isRunning()) {
					packetScoreboard.removeLine(Integer.valueOf(9));
					packetScoreboard.setLine(Integer.valueOf(9), "§rUnschuldige: §a" + (GameInfo.getInnoa() - 1));
					packetScoreboard.removeLine(Integer.valueOf(8));
					packetScoreboard.setLine(Integer.valueOf(8),
							"§rZeit: §a" + GameInfo.zeitmin() + ":" + GameInfo.zeitsek());
					packetScoreboard.removeLine(Integer.valueOf(6));
					packetScoreboard.setLine(Integer.valueOf(6), "§rDetektiv: " + GameInfo.getDetectivstatus());
				} else if(GameInfo.ended) {
					packetScoreboard.remove();
				} else {
					packetScoreboard.removeLine(Integer.valueOf(6));
					packetScoreboard.setLine(Integer.valueOf(6), "§rKarte: §e" + GameInfo.getMap());
					packetScoreboard.removeLine(Integer.valueOf(5));
					packetScoreboard.setLine(Integer.valueOf(5),
							"§rSpieler: §e" + (Bukkit.getOnlinePlayers().size() - 1) + "/" + Bukkit.getMaxPlayers());
					packetScoreboard.removeLine(Integer.valueOf(3));
					packetScoreboard.setLine(Integer.valueOf(3), "§rStart in §e" + GameInfo.getCountdown() + "s");

				}
			}
		} catch (Exception e) {
		}

	}

}
