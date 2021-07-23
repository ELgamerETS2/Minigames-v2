package me.elgamer.minigames.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import me.elgamer.minigames.Main;

public class GameTable {

	public static String getName(int i) {

		Main instance = Main.getInstance();

		try {
			PreparedStatement statement = instance.getConnection().prepareStatement
					("SELECT * FROM " + instance.GAMES + " WHERE GameID=" + i);			
			ResultSet results = statement.executeQuery();
						
			if (results.next()) {		
				return results.getString("GameType");		
			} else {
				return null;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static boolean addGame(String gameType, String world) {
		
		Main instance = Main.getInstance();

		try {
			PreparedStatement statement = instance.getConnection().prepareStatement
					("INSERT INTO " + instance.GAMES + " (GameID,GameType,World) VALUE (?,?,?)");
			statement.setInt(1, getNewID());
			statement.setString(2, gameType);
			statement.setString(3, world);
			statement.executeUpdate();
			
			return true;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	public static int getNewID() {
		
		Main instance = Main.getInstance();

		try {
			PreparedStatement statement = instance.getConnection().prepareStatement
					("SELECT * FROM " + instance.GAMES);
			ResultSet results = statement.executeQuery();

			if (results.last()) {
				int last = results.getInt("GameID");
				return (last+1);
			} else {
				return 1;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return 1;
		}
	}

}
