package de.presti.mudermystery.utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import de.presti.mudermystery.main.Main;

public class LocationUtils {

	public LocationUtils() {
		Main.instance.location = this;
	}

	public void setLocation(String path, Player player) {
		if (locexists(path)) {
			Main.instance.sql.update("DELETE FROM LOCS WHERE LOCATION= '" + path + "'");
			Main.instance.sql.update("INSERT INTO LOCS (LOCATION, WORLD, X, Y, Z, Yaw, Pitch) VALUES ('" + path + "', '"
					+ player.getLocation().getWorld().getName() + "','" + player.getLocation().getX() + "', '"
					+ player.getLocation().getY() + "', '" + player.getLocation().getZ() + "', '"
					+ player.getLocation().getYaw() + "', '" + player.getLocation().getPitch() + "');");
		} else {
			Main.instance.sql.update("INSERT INTO LOCS (LOCATION, WORLD, X, Y, Z, Yaw, Pitch) VALUES ('" + path + "', '"
					+ player.getLocation().getWorld().getName() + "','" + player.getLocation().getX() + "', '"
					+ player.getLocation().getY() + "', '" + player.getLocation().getZ() + "', '"
					+ player.getLocation().getYaw() + "', '" + player.getLocation().getPitch() + "');");
		}
	}

	public void setLocation(String path, Location loc) {
		if (locexists(path)) {
			Main.instance.sql.update("DELETE FROM LOCS WHERE LOCATION= '" + path + "'");
			Main.instance.sql.update("INSERT INTO LOCS (LOCATION, WORLD, X, Y, Z, Yaw, Pitch) VALUES ('" + path + "', '"
					+ loc.getWorld().getName() + "','" + loc.getX() + "', '" + loc.getY() + "', '" + loc.getZ() + "', '"
					+ loc.getYaw() + "', '" + loc.getPitch() + "');");
		} else {
			Main.instance.sql.update("INSERT INTO LOCS (LOCATION, WORLD, X, Y, Z, Yaw, Pitch) VALUES ('" + path + "', '"
					+ loc.getWorld().getName() + "','" + loc.getX() + "', '" + loc.getY() + "', '" + loc.getZ() + "', '"
					+ loc.getYaw() + "', '" + loc.getPitch() + "');");
		}
	}

	public boolean locexists(String loc) {
		try {
			java.sql.PreparedStatement st = null;
			ResultSet rs = null;
			try {
				st = Main.instance.sql.con.prepareStatement("SELECT * FROM LOCS WHERE LOCATION='" + loc + "'");
				rs = st.executeQuery("SELECT * FROM LOCS WHERE LOCATION='" + loc + "'");
			} catch (SQLException e) {
			}
			if (rs.next()) {
				return rs.getString("X") != null;
			}

			return false;
		} catch (SQLException e) {

		}
		return false;
	}

	public static Location loadloc(String path) {
		String[] i = new String[6];
		java.sql.PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = Main.instance.sql.con.prepareStatement("SELECT * FROM LOCS WHERE LOCATION='" + path + "'");
			rs = st.executeQuery("SELECT * FROM LOCS WHERE LOCATION='" + path + "'");
		} catch (SQLException e) {
		}
		try {
			while (rs.next()) {
				i[5] = rs.getString("WORLD");
				i[0] = rs.getString("X");
				i[1] = rs.getString("Y");
				i[2] = rs.getString("Z");
				if (rs.getString("Yaw").equalsIgnoreCase("-69")) {
					i[3] = "-99";
				} else {
					i[3] = rs.getString("Yaw");
				}
				if (rs.getString("Pitch").equalsIgnoreCase("-69")) {
					i[4] = "-99";
				} else {
					i[4] = rs.getString("Pitch");
				}
			}
		} catch (NumberFormatException e) {
			i[0] = "0";
			i[1] = "100";
			i[2] = "0";
			i[3] = "0";
			i[4] = "0";
			i[5] = "world";
		} catch (SQLException e) {
			i[0] = "0";
			i[1] = "100";
			i[2] = "0";
			i[3] = "0";
			i[4] = "0";
			i[5] = "world";

		}
		Location newloc;
		if (i[3].equalsIgnoreCase("-99") && i[4].equalsIgnoreCase("-99")) {
			newloc = new Location(Bukkit.getWorld(i[5]), Double.parseDouble(i[0]), Double.parseDouble(i[1]),
					Double.parseDouble(i[2]));
		} else {
			newloc = new Location(Bukkit.getWorld(i[5]), Double.parseDouble(i[0]), Double.parseDouble(i[1]),
					Double.parseDouble(i[2]));
			newloc.setYaw(Float.parseFloat(i[3]));
			newloc.setPitch(Float.parseFloat(i[4]));
		}
		return newloc;
	}

	public static Location getLoc(String name) {
		return loadloc(name);
	}

	public void setLocation2(String path, Location loc) {
		if (locexists(path)) {
			Main.instance.sql.update("DELETE FROM LOCS WHERE LOCATION= '" + path + "'");
			Main.instance.sql.update("INSERT INTO LOCS (LOCATION, WORLD, X, Y, Z, Yaw, Pitch) VALUES ('" + path + "', '"
					+ loc.getWorld().getName() + "','" + loc.getX() + "', '" + loc.getY() + "', '" + loc.getZ()
					+ "', '-69', '-69');");
		} else {
			Main.instance.sql.update("INSERT INTO LOCS (LOCATION, WORLD, X, Y, Z, Yaw, Pitch) VALUES ('" + path + "', '"
					+ loc.getWorld().getName() + "','" + loc.getX() + "', '" + loc.getY() + "', '" + loc.getZ()
					+ "', '-69', '-69');");
		}
	}

}
