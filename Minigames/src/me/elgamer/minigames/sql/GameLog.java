package me.elgamer.minigames.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import me.elgamer.minigames.Main;
import me.elgamer.minigames.utilities.Time;

public class GameLog {

	private static int getNewID() {

		Main instance = Main.getInstance();

		try {
			PreparedStatement statement = instance.getConnection().prepareStatement
					("SELECT * FROM " + instance.GameLog);
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

	public static int addGame(int GameID, int MapID) {

		Main instance = Main.getInstance();

		try {
			PreparedStatement statement = instance.getConnection().prepareStatement
					("INSERT INTO " + instance.Games + " (ID,GameID,MapID,StartTime,EndTime) VALUE (?,?,?,?,?)");
			
			int ID = getNewID();
			statement.setInt(1, ID);
			statement.setInt(2, GameID);
			statement.setInt(3, MapID);
			statement.setLong(4, Time.currentTime());
			statement.setLong(5, 0);
			statement.executeUpdate();

			return ID;

		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}

	}
	
	public static boolean setGameEnd(int ID) {
		
		Main instance = Main.getInstance();

		try {
			PreparedStatement statement = instance.getConnection().prepareStatement
					("UPDATE " + instance.Games + " SET EndTime=? WHERE ID=" + ID);
			
			statement.setLong(1, Time.currentTime());
			statement.executeUpdate();

			return true;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

}
