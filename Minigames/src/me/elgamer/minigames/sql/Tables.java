package me.elgamer.minigames.sql;

import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.Bukkit;

import me.elgamer.minigames.Main;

public class Tables {
	
	static String sql;
	static Statement SQL = null; 
	
	public static void createTables() {

		createGamesTable();
		createGuiTable();
		createGameLog();
		
	}

	//Creates games table in database
	private static void createGamesTable()
	{
		
		Main instance = Main.getInstance();

		try
		{
			//Adds a weather pref table
			sql = "CREATE TABLE IF NOT EXISTS `"+ instance.Database+"`.`"+ instance.Games+"` (\n" + 
					"  `GameID` INT NOT NULL AUTO_INCREMENT,\n" + 
					"  `GameType` TEXT NOT NULL,\n" + 
					"   PRIMARY KEY (`GameID`))"
					+	";";

			SQL = instance.getConnection().createStatement();
			
		}
		catch (SQLException se)
		{
			Bukkit.getConsoleSender().sendMessage("[Minigames] [SQL] SQL Error adding Games Table");
			se.printStackTrace();
		}
		catch (Exception e)
		{
			Bukkit.getConsoleSender().sendMessage("[Minigames] [SQL] Error adding Games Table");
			e.printStackTrace();
		}
	}
	
	private static void createGuiTable() {
		
		Main instance = Main.getInstance();

		try
		{
			//Adds a weather pref table
			sql = "CREATE TABLE IF NOT EXISTS `"+ instance.Database+"`.`"+ instance.Gui+"` (\n" + 
					"  `Slot` INT NOT NULL AUTO_INCREMENT,\n" + 
					"  `GameID` INT NOT NULL,\n" + 
					"  `Material` TEXT NOT NULL,\n" +
					"   PRIMARY KEY (`Slot`))"
					+	";";

			SQL = instance.getConnection().createStatement();
			
		}
		catch (SQLException se)
		{
			Bukkit.getConsoleSender().sendMessage("[Minigames] [SQL] SQL Error adding Gui Table");
			se.printStackTrace();
		}
		catch (Exception e)
		{
			Bukkit.getConsoleSender().sendMessage("[Minigames] [SQL] Error adding Gui Table");
			e.printStackTrace();
		}
	}
	
	//Creates games table in database
	private static void createGameLog()
	{
		
		Main instance = Main.getInstance();

		try
		{
			//Adds a weather pref table
			sql = "CREATE TABLE IF NOT EXISTS `"+ instance.Database+"`.`"+ instance.GameLog+"` (\n" + 
					"  `ID` INT NOT NULL AUTO_INCREMENT,\n" + 
					"  `GameID` INT NOT NULL,\n" + 
					"  `MapID` INT NOT NULL,\n" + 
					"  `StartTime` BIGINT NOT NULL,\n" +
					"  `EndTime` BIGINT NOT NULL,\n" +
					"   PRIMARY KEY (`ID`))"
					+	";";

			SQL = instance.getConnection().createStatement();
			
		}
		catch (SQLException se)
		{
			Bukkit.getConsoleSender().sendMessage("[Minigames] [SQL] SQL Error adding Games Table");
			se.printStackTrace();
		}
		catch (Exception e)
		{
			Bukkit.getConsoleSender().sendMessage("[Minigames] [SQL] Error adding Games Table");
			e.printStackTrace();
		}
	}

}
