package de.presti.statsapi;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class MurderMysterySQL {
	
	public static boolean playerexists(String uuid) {
		try {
			java.sql.PreparedStatement st = null;
			ResultSet rs = null;
			try {
				st = StatsAPI.instance.sql.con.prepareStatement("SELECT * FROM MM WHERE PLAYER='" + uuid + "'");
				rs = st.executeQuery("SELECT * FROM MM WHERE PLAYER='" + uuid + "'");
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
			StatsAPI.instance.sql.update("INSERT INTO MM (PLAYER , Wins, Kills) VALUES ('" + uuid + "', '0', '0');");			
		}
	}
	
	
	public static Integer getWins(String uuid) {
		Integer i = Integer.valueOf(0);
		if(playerexists(uuid)) {
			java.sql.PreparedStatement st = null;
			ResultSet rs = null;
			try {
				st = StatsAPI.instance.sql.con.prepareStatement("SELECT * FROM MM WHERE PLAYER='" + uuid + "'");
				rs = st.executeQuery("SELECT * FROM MM WHERE PLAYER='" + uuid + "'");
			} catch (SQLException e) {
			}
			try {
				while(rs.next()) {
					i = Integer.valueOf(rs.getString("Wins"));
				}
			} catch(Exception e) {
				
			}
		} else {
			addplayer(uuid);
			getWins(uuid);
		}
		return i;
	}
	
	public static Integer getKills(String uuid) {
		Integer i = Integer.valueOf(0);
		if(playerexists(uuid)) {
			java.sql.PreparedStatement st = null;
			ResultSet rs = null;
			try {
				st = StatsAPI.instance.sql.con.prepareStatement("SELECT * FROM MM WHERE PLAYER='" + uuid + "'");
				rs = st.executeQuery("SELECT * FROM MM WHERE PLAYER='" + uuid + "'");
			} catch (SQLException e) {
			}
			try {
				while(rs.next()) {
					i = Integer.valueOf(rs.getString("Kills"));
				}
			} catch(Exception e) {
				
			}
		} else {
			addplayer(uuid);
			getWins(uuid);
		}
		return i;
	}
	
	public static void addWin(String uuid, int MM) {
		setWin(uuid, getWins(uuid) + MM);
	}
	
	public static void addKil(String uuid, int MM) {
		setKills(uuid, getKills(uuid) + MM);
	}
	
	
	public static void setWin(String uuid, int MM) {
		if(playerexists(uuid)) {
			StatsAPI.instance.sql.update("UPDATE MM SET Wins='" + MM + "' WHERE PLAYER='" + uuid + "'");
		} else {
			addplayer(uuid);
			setWin(uuid, MM);
		}
	}
	
	public static void setKills(String uuid, int MM) {
		if(playerexists(uuid)) {
			StatsAPI.instance.sql.update("UPDATE MM SET Kills='" + MM + "' WHERE PLAYER='" + uuid + "'");
		} else {
			addplayer(uuid);
			setKills(uuid, MM);
		}
	}

	public static ArrayList<String> top3() throws SQLException {
		   String sql = "SELECT * FROM MM ORDER BY Wins DESC LIMIT " + 3 + ";";
		    
		    ArrayList<String> UUIDs = new ArrayList<>();
		    
		    Statement statement = StatsAPI.instance.sql.con.createStatement();
		    ResultSet resultset = statement.executeQuery(sql);
		    while (resultset.next()) {
		      
		      UUID uuid = UUID.fromString(resultset.getString("PLAYER"));

		      
		      
		      UUIDs.add(uuid.toString());
		    } 
		    
		    resultset.close();
		    statement.close();
		    
		    return UUIDs;
	}
	
}

