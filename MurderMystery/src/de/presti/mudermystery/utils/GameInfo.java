package de.presti.mudermystery.utils;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.presti.mudermystery.main.Data;

public class GameInfo {

	public static String map = "";
	public static int zeit = 360;
	public static int countdown = 60;
	public static int speedlvl = 1;
	public static boolean running = false;
	public static boolean full = false;
	public static boolean speed = false;
	public static boolean horror = false;
	public static boolean tatutata = false;
	public static Player d2 = null;
	public static Player m2 = null;
	public static boolean ended = false;
	public static Player murder = null;
	public static String detectivstatus = "§aLebt";
	public static Player detectiv = null;
	public static boolean forcem = false;
	public static boolean forced = false;
	public static Location mitte;
	public static Location dbowloc;

	public static String getRolle(Player p) {
		String rolle;
		if (!p.getGameMode().equals(GameMode.ADVENTURE)) {
			if (p == murder || p == m2) {
				rolle = "§cMörder";
			} else if (p == detectiv || p == d2) {
				rolle = "§bDetektiv";
			} else {
				rolle = "§aUnschuldig";
			}
		} else {
			rolle = "§7Tot";
		}
		return rolle;
	}

	public static int zeitmin() {
		return (zeit / 60);
	}

	public static int getInnoa() {
		int a = Bukkit.getOnlinePlayers().size();
		for (Player all : Bukkit.getOnlinePlayers()) {
			if (all.getGameMode().equals(GameMode.CREATIVE) || all == murder || all == m2
					|| all.getGameMode().equals(GameMode.ADVENTURE)) {
				a--;
			}
		}
		return a;
	}

	public static String zeitsek() {
		int zeitsek = 0;
		String zeitseks = "0";
		if ((zeit <= 360) && (zeit >= 300)) {
			zeitsek = (zeit - 300);
		} else if ((zeit <= 299) && (zeit >= 240)) {
			zeitsek = (zeit - 240);
		} else if ((zeit <= 239) && (zeit >= 180)) {
			zeitsek = (zeit - 180);
		} else if ((zeit <= 179) && (zeit >= 120)) {
			zeitsek = (zeit - 120);
		} else if ((zeit <= 119) && (zeit >= 60)) {
			zeitsek = (zeit - 60);
		} else {
			zeitsek = zeit;
		}

		if (zeitsek < 10) {
			zeitseks = "0" + zeitsek;
		} else {
			zeitseks = "" + zeitsek;
		}
		return zeitseks;
	}

	public static boolean isDead(Player p) {
		
		if(p == null) {
			return true;
		}
		
		if(!p.getGameMode().equals(GameMode.SURVIVAL)) {
			return true;
		}
		return false;
	}
	
	public static Location getMitte() {
		return LocationUtils.getLoc(map + ".mitte");
	}

	public static ItemStack getMurderSword() {
		ItemStack i = new ItemStack(Material.IRON_SWORD);
		ItemMeta meta = i.getItemMeta();
		meta.setDisplayName("§cMörders Klinge");
		i.setItemMeta(meta);
		return i;
	}

	public static ItemStack getDetektivBow() {
		ItemStack i = new ItemStack(Material.BOW);
		ItemMeta meta = i.getItemMeta();
		meta.setDisplayName("§bDer Lange Arm des Gesetzes");
		i.setItemMeta(meta);
		return i;
	}

	public static Location getSpawn() {
		return LocationUtils.getLoc("spawn");
	}

	public static Location getPlayerSpawn(String map, int nummer) {
		map = map.toLowerCase();
		return LocationUtils.getLoc(map + ".playerspawn." + nummer);
	}

	public static Location getItemSpawn(String map, int nummer) {
		map = map.toLowerCase();
		return LocationUtils.getLoc(map + ".itemspawn." + nummer);
	}

	public static void setMitte(Location mitte) {
		GameInfo.mitte = mitte;
	}

	public static String getDetectivstatus() {
		return detectivstatus;
	}

	public static void setDetectivstatus(String detectivstatus) {
		GameInfo.detectivstatus = detectivstatus;
	}

	public static int getCountdown() {
		return countdown;
	}

	public static void setCountdown(int countdown) {
		GameInfo.countdown = countdown;
	}

	public static String getMap() {
		return map;
	}

	public static void setMap(String map) {
		GameInfo.map = map;
	}

	public static boolean isRunning() {
		return running;
	}

	public static void setRunning(boolean running) {
		GameInfo.running = running;
	}

	public static boolean isFull() {
		return full;
	}

	public static void setFull(boolean full) {
		GameInfo.full = full;
	}

	public static Player getMurder() {
		return murder;
	}

	public static void setMurder(Player murder) {
		GameInfo.murder = murder;
	}

	public static Player getDetectiv() {
		return detectiv;
	}

	public static void setDetectiv(Player detectiv) {
		GameInfo.detectiv = detectiv;
	}

	public static int getZeit() {
		return zeit;
	}

	public static void setZeit(int zeit) {
		GameInfo.zeit = zeit;
	}

	public static int getSpeedlvl() {
		return speedlvl;
	}

	public static void setSpeedlvl(int speedlvl) {
		GameInfo.speedlvl = speedlvl;
	}

	public static boolean isSpeed() {
		return speed;
	}

	public static void setSpeed(boolean speed) {
		GameInfo.speed = speed;
	}

	public static boolean isHorror() {
		return horror;
	}

	public static void setHorror(boolean horror) {
		GameInfo.horror = horror;
	}

	public static boolean isTatutata() {
		return tatutata;
	}

	public static void setTatutata(boolean tatutata) {
		GameInfo.tatutata = tatutata;
	}

	public static Player getD2() {
		return d2;
	}

	public static void setD2(Player d2) {
		GameInfo.d2 = d2;
	}

	public static Player getM2() {
		return m2;
	}

	public static void setM2(Player m2) {
		GameInfo.m2 = m2;
	}

	public static boolean isEnded() {
		return ended;
	}

	public static void setEnded(boolean ended) {
		GameInfo.ended = ended;
	}

	public static boolean isForcem() {
		return forcem;
	}

	public static void setForcem(boolean forcem) {
		GameInfo.forcem = forcem;
	}

	public static boolean isForced() {
		return forced;
	}

	public static void setForced(boolean forced) {
		GameInfo.forced = forced;
	}

	public static Location getDbowloc() {
		return dbowloc;
	}

	public static void setDbowloc(Location dbowloc) {
		GameInfo.dbowloc = dbowloc;
	}
	
	public static boolean isEvent() {
		return speed || horror || tatutata;
	}

	public static String getEvents() {
		String events = "Aktive Events:";
		if (speed) {
			events += "\n" + Data.prefix + "Speed(" + speedlvl + ")";
		}

		if (horror) {
			events += "\n" + Data.prefix + "Horror(2Mörder)";
		}

		if (tatutata) {
			events += "\n" + Data.prefix + "TATÜTATA(2Detektivs)";
		}

		return events;
	}
}
