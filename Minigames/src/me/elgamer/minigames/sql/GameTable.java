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
					("SELECT * FROM " + instance.Games + " WHERE GameID=" + i);			
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
	
	public static int getID(String gameType) {

		Main instance = Main.getInstance();

		try {
			PreparedStatement statement = instance.getConnection().prepareStatement
					("SELECT * FROM " + instance.Games + " WHERE GameType=?");		
			statement.setString(1, gameType);
			ResultSet results = statement.executeQuery();
						
			if (results.next()) {		
				return results.getInt("GameID");		
			} else {
				return 0;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public static boolean addGame(String gameType) {
		
		Main instance = Main.getInstance();

		try {
			PreparedStatement statement = instance.getConnection().prepareStatement
					("INSERT INTO " + instance.Games + " (GameID,GameType) VALUE (?,?)");
			statement.setInt(1, getNewID());
			statement.setString(2, gameType);
			statement.executeUpdate();
			
			return true;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	private static int getNewID() {
		
		Main instance = Main.getInstance();

		try {
			PreparedStatement statement = instance.getConnection().prepareStatement
					("SELECT * FROM " + instance.Games);
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
	
	public static boolean gameExists(String gameType) {

		Main instance = Main.getInstance();

		try {
			PreparedStatement statement = instance.getConnection().prepareStatement
					("SELECT * FROM " + instance.Games + " WHERE GameType=?");		
			statement.setString(1, gameType);
			ResultSet results = statement.executeQuery();
						
			if (results.next()) {		
				return true;		
			} else {
				return false;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean removeGame(String gameType) {

		Main instance = Main.getInstance();

		try {
			PreparedStatement statement = instance.getConnection().prepareStatement
					("DELETE FROM " + instance.Games + " WHERE GameType=?");		
			statement.setString(1, gameType);
			statement.executeUpdate();
			return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

}
