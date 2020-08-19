package de.presti.statsapi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQL {
	
	
	public String username;
	public String passwort;
	public String databse;
	public String host;
	public String database;
	public int port = 3306;
	public Connection con;
	
	public MySQL(String user, String password , String host2, String dB, int port3) {
		StatsAPI.instance.sql = this;
		username = user;
		passwort = password;
		database = dB;
		host = host2;
		port = port3;
		connect();
		createTables();
	}
	
	public void connect() {
		if(!isConnected()) {
			try {
				con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?autoReconnect=true", username, passwort);
				System.out.println("Service (MySQL) wurde gestartet. Verbindung erfolgreich hergestellt");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public void close () {
		if(!isConnected()) {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public boolean isConnected() {

		return con != null;
		
	}
	
	public void createTables() {
		try {
			java.sql.PreparedStatement st = null;
			st = con.prepareStatement("CREATE TABLE IF NOT EXISTS MM ( id INT AUTO_INCREMENT PRIMARY KEY , PLAYER VARCHAR(40) , Wins VARCHAR(100), Kills VARCHAR(100))");
			st.executeUpdate();
			
			st.close();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	
	public void update(String qry) {
		if(isConnected()) {
			try {
				con.createStatement().executeUpdate(qry);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	

}
