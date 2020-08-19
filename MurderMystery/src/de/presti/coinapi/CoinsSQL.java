package de.presti.coinapi;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CoinsSQL {
	
	public static boolean playerexists(String uuid) {
		try {
			java.sql.PreparedStatement st = null;
			ResultSet rs = null;
			try {
				st = CoinAPI.instance.sql.con.prepareStatement("SELECT * FROM Coins WHERE PLAYER='" + uuid + "'");
				rs = st.executeQuery("SELECT * FROM Coins WHERE PLAYER='" + uuid + "'");
			} catch (SQLException e) {
			}
			if(rs.next()) {
				return rs.getString("PLAYER") != null;
			}
			
			return false;
		} catch (SQLException e) {
			
		}
		return false;
	}
	
	public static void addplayer(String uuid) {
		if(!playerexists(uuid)) {
			CoinAPI.instance.sql.update("INSERT INTO Coins (PLAYER , COINS, time) VALUES ('" + uuid + "', '1000', '0');");			
		}
	}
	
	
	public static Integer getCoins(String uuid) {
		Integer i = Integer.valueOf(0);
		if(playerexists(uuid)) {
			java.sql.PreparedStatement st = null;
			ResultSet rs = null;
			try {
				st = CoinAPI.instance.sql.con.prepareStatement("SELECT * FROM Coins WHERE PLAYER='" + uuid + "'");
				rs = st.executeQuery("SELECT * FROM Coins WHERE PLAYER='" + uuid + "'");
			} catch (SQLException e) {
			}
			try {
				while(rs.next()) {
					i = Integer.valueOf(rs.getString("COINS"));
				}
			} catch(Exception e) {
				
			}
		} else {
			addplayer(uuid);
			getCoins(uuid);
		}
		return i;
	}
	
	public static void addCoins(String uuid, int coins) {
		setCoins(uuid, getCoins(uuid) + coins);
	}
	
	public static void removeCoins(String uuid, int coins) {
		setCoins(uuid, getCoins(uuid) - coins);
	}
	
	public static void setCoins(String uuid, int coins) {
		if(playerexists(uuid)) {
			CoinAPI.instance.sql.update("UPDATE Coins SET COINS='" + coins + "' WHERE PLAYER='" + uuid + "'");
		} else {
			addplayer(uuid);
			setCoins(uuid, coins);
		}
	}
	
}

