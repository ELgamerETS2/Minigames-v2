package me.elgamer.minigames;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import me.elgamer.minigames.commands.AddGame;
import me.elgamer.minigames.commands.AddToGui;
import me.elgamer.minigames.commands.RemoveFromGui;
import me.elgamer.minigames.commands.RemoveGame;
import me.elgamer.minigames.gui.Menu;
import me.elgamer.minigames.listeners.JoinEvent;
import me.elgamer.minigames.listeners.LeaveEvent;
import me.elgamer.minigames.sql.Tables;
import me.elgamer.minigames.utilities.Lobby;
import me.elgamer.minigames.utilities.User;
import me.elgamer.minigames.utilities.Utils;

public class Main extends JavaPlugin {
	
	String sql;	
	Statement SQL = null; 
	ResultSet resultSet = null;
	private Connection connection = null;    
	boolean bIsConnected;	
	private String DB_CON;
	
	static Main instance;
	static FileConfiguration config;
	
	//MySQL
	private String HOST;
	private int PORT;
	public String Database;
	private String USER;
	private String PASSWORD;
	
	public String GAMES;
	public String GUI;
	
	//Possible game slots in gui
	public static ArrayList<Integer> slots;
	
	//List of users
	public static ArrayList<User> users;
	
	//Hotbar items
	public ItemStack slot9;
	public static ItemStack menu;
	
	public ItemStack slot8;
	public static ItemStack stats;
	
	//Lobby
	public static Lobby lobby;
	
	@Override
	public void onEnable()
	{
		//Config Setup
		instance = this;
		config = this.getConfig();
		saveDefaultConfig();
		
		//MySQL
		boolean bSuccess;
		bIsConnected = false;
		
		//Attempt set up from config and connect
		mySQLSetup();
		bSuccess = connect();
		
		//Test whether database connected
		if (bSuccess)
		{
			Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[Minigames] MySQL Connected");
			bIsConnected = true;
			
			
			//Create tables for database
			Tables.createTables();
		}
		
		//Setup gui slots list
		slots = new ArrayList<Integer>(
			      Arrays.asList(11,12,13,14,15,16,17,
			    		  20,21,22,23,24,25,26,
			    		  29,30,31,32,33,34,35));
		
		//Create lobby
		lobby = new Lobby(config);
		
		//---------------------------------------
		//--------------Create GUIs--------------
		//---------------------------------------

		Menu.initialize();
		//Stats.initialize();
		
		//Create menu item				
		menu = new ItemStack(Material.EMERALD);
		ItemMeta meta = menu.getItemMeta();
		meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Menu");
		menu.setItemMeta(meta);
		
		//1 second timer - updates slot
		this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			public void run()
			{
				for (Player p : Bukkit.getOnlinePlayers())
				{
					//Menu
					slot9 = p.getInventory().getItem(8);
					if (slot9 == null)
					{
						p.getInventory().setItem(8, menu);
					}
					else if (!slot9.equals(menu))
					{
						p.getInventory().setItem(8, menu);
					}
					
					//Create stats item
					stats = Utils.createPlayerSkull(p, 1, 7, ChatColor.GREEN + "" + ChatColor.BOLD + "Stats");
					ItemMeta meta = stats.getItemMeta();
					meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Stats");
					stats.setItemMeta(meta);
					
					slot8 = p.getInventory().getItem(7);
					if (slot8 == null)
					{
						p.getInventory().setItem(7, stats);
					}
					else if (!slot8.equals(stats))
					{
						p.getInventory().setItem(7, stats);
					}
				}
			}
		}, 0L, 20L);
		
		//---------------------------------------
		//---------------Listeners---------------
		//---------------------------------------

		//Handles welcome message and gamemode
		new JoinEvent(this);
		new LeaveEvent(this);
		
		//--------------------------------------
		//---------------Commands---------------
		//--------------------------------------

		getCommand("addgame").setExecutor(new AddGame());
		getCommand("removegame").setExecutor(new RemoveGame());
		getCommand("addguientry").setExecutor(new AddToGui());
		getCommand("removeguientry").setExecutor(new RemoveFromGui());
		
		//--------------------------------------
		//-------------Stats Update-------------
		//--------------------------------------
		
		//long lMinute = 1200L;
		/*
		//Schedule the stats to change
		this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable()
		{
			public void run()
			{
				UpdateCall up = new UpdateCall(instance);
				up.run();
			}
		}, 0L, lMinute * config.getInt("StatisticsSignUpdates"));
		*/
	}
	
	@Override
	public void onDisable()
	{
		//Where stats archive occures
		
		disconnect();
	}
	
	public void mySQLSetup()
	{
		this.HOST = config.getString("mysql.host");
		this.PORT = config.getInt("mysql.port");
		this.Database = config.getString("mysql.database");
		this.USER = config.getString("mysql.username");
		this.PASSWORD = config.getString("mysql.password");
		
		this.GAMES = config.getString("mysql.table.game");
		this.GUI = config.getString("mysql.table.gui");
		this.DB_CON = "jdbc:mysql://" + this.HOST + ":" 
				+ this.PORT + "/" + this.Database + "?&useSSL=false&";
	}
	
	public boolean connect() 
	{
		try
		{
		//	System.out.println(this.getClass().getName() +" : Connecting la la la");
			DriverManager.getDriver(DB_CON);
			connection = DriverManager.getConnection(DB_CON, USER, PASSWORD);
			if (this.connection != null)
				return true;
			else
				return false;
		}
		catch (SQLException e)
		{
			if (e.toString().contains("Access denied"))
			{
				Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[Minigames] - [minigamesMain] - Access denied");			
			}
			else if (e.toString().contains("Communications link failure"))
			{
				Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[Minigames] - [minigamesMain] - Communications link failure");			
			}
			else
			{
				Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[Minigames] - [minigamesMain] - Other SQLException - "+e.getMessage());
				e.printStackTrace();
			}
			return false;
		}
		catch (Exception e)
		{
			Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[Minigames] - [minigamesMain] - Other Exception whilst connecting to database- "+e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
	
	public void disconnect()
	{
		try
		{
			if (bIsConnected)
			{
				this.connection.close() ;
				this.bIsConnected = false ;
			}
		//	System.err.println( this.getClass().getName() + ":: disconnected." ) ;
		}
		catch ( SQLException se )
		{
			Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[Minigames] - [minigamesMain] - SQLException - closing connection");			
			se.printStackTrace() ;
		}
		catch ( Exception e )
		{
			Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[Minigames] - [minigamesMain] - Exception - closing connection");			
			e.printStackTrace() ;
		}
	}	
	
	public static Main getInstance()
	{
		return instance;
	}
	
	public Connection getConnection()
	{
		try
		{
			if (connection.isValid(0) && connection != null)
			{
				
			}
			else
			{
				connect();
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return connection;
	}
	
	public static User getUser(Player p) {
		
		for (User u : users) {			
			if (u.p.equals(p)) {
				return u;
			}			
		}		
		return null;				
	}
}
