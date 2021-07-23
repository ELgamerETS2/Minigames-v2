package me.elgamer.minigames.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import me.elgamer.minigames.Main;

public class GuiTable {
	
	public static ResultSet getEntries() {

		Main instance = Main.getInstance();

		try {
			PreparedStatement statement = instance.getConnection().prepareStatement
					("SELECT * FROM " + instance.GUI);
			return statement.executeQuery();

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void addEntry(int slot, int gameID, String material) {
		
		Main instance = Main.getInstance();

		try {
			PreparedStatement statement = instance.getConnection().prepareStatement
					("INSERT INTO " + instance.GUI + " (Slot,GameID,Material) VALUE (?,?,?)");
			statement.setInt(1, slot);
			statement.setInt(2, gameID);
			statement.setString(3, material);
			statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
