package de.presti.coinapi;

public class CoinAPI {
	public static CoinAPI instance;
	public MySQL sql;
	
	public CoinAPI(String user, String password, String host, String db) {
		instance = this;
		sql = new MySQL(user, password, host, db, 3306);
	}
	
	public void addCoins(String uuid, int coins) {
		CoinsSQL.addCoins(uuid, coins);
	}
	
	public void removeCoins(String uuid, int coins) {
		CoinsSQL.removeCoins(uuid, coins);
	}
	
	public void setCoins(String uuid, int coins) {
		CoinsSQL.setCoins(uuid, coins);
	}
	
	public Integer getCoins(String uuid) {
		return CoinsSQL.getCoins(uuid);
	}
	
	public boolean exist(String uuid) {
		return CoinsSQL.playerexists(uuid);
	}
	
	public void adduser(String uuid) {
		CoinsSQL.addplayer(uuid);
	}

}
