package de.presti.statsapi;

import java.sql.SQLException;
import java.util.ArrayList;

public class StatsAPI {
	public static StatsAPI instance;
	public MySQL sql;
	
	public StatsAPI(String user, String password, String host, String db) {
		instance = this;
		sql = new MySQL(user, password, host, db, 3306);
	}
	
	public void addWinMM(String uuid, int wins) {
		MurderMysterySQL.addWin(uuid, wins);
	}
	
	public void addKillMM(String uuid, int kills) {
		MurderMysterySQL.addKil(uuid, kills);
	}
	
	public Integer getWinsMM(String uuid) {
		return MurderMysterySQL.getWins(uuid);
	}
	
	public Integer getKillsMM(String uuid) {
		return MurderMysterySQL.getKills(uuid);
	}
	
	public boolean existUserMM(String uuid) {
		return MurderMysterySQL.playerexists(uuid);
	}
	
	public void addUserMM(String uuid) {
		MurderMysterySQL.addplayer(uuid);
	}
	
	public ArrayList<String> getTop3() {
		try {
			return MurderMysterySQL.top3();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
