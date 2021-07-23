package me.elgamer.minigames;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
//import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import me.elgamer.minigames.sql.Tables;
import me.elgamer.minigames.utilities.Utils;

/*
import Minigames.MainLobby;
import Minigames.minigamesMain;
import Minigames.Games.Gametype;
import Minigames.Games.HideAndSeek.HideAndSeekLobby;
import Minigames.Games.RiverRace.RRSelection;
import Minigames.Games.RiverRace.RiverRaceLobby;
import Minigames.commands.HSBJoin;
import Minigames.commands.HSMap;
import Minigames.commands.Lobby;
import Minigames.commands.RRCheck;
import Minigames.commands.RRGrid;
import Minigames.commands.RRMap;
import Minigames.commands.RiverRace;
import Minigames.commands.Wand;
import Minigames.gui.HideStatsGUI;
import Minigames.gui.MenuGUI;
import Minigames.gui.RiverRaceStatsGUI;
import Minigames.gui.StatsGUI;
import Minigames.gui.Utils;
import Minigames.listeners.InventoryClicked;
import Minigames.listeners.JoinEvent;
import Minigames.listeners.PlayerInteract;
import Minigames.statistics.UpdateCall;
*/
public class Main extends JavaPlugin {
	
String sql;
	
	Statement SQL = null; 
	ResultSet resultSet = null;
	
	static Main instance;
	static FileConfiguration config;
	
	private String HOST;
	private int PORT;
	public String Database;
	private String USER;
	private String PASSWORD;
	
	public String GAMES;
	public String GUI;
	
	public String MainLobbies;
	/*
	public MainLobby MainLobby;
	public HideAndSeekLobby HSLobby;
	public RiverRaceLobby RRLobby;
	*/
	public Plugin plugin;
	
	private Connection connection = null;
    
	boolean bIsConnected;
	
	private String DB_CON;
	
	public ItemStack slot9;
	public static ItemStack menu;
	
	public ItemStack slot8;
	public static ItemStack stats;
	
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
		
		//--------------------------------------
		//------------Create lobbies------------
		//--------------------------------------
		
		/*
		MainLobby = new MainLobby(this);
		//Attempts to load world
	//	Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mv import " +MainLobby.getWorldName() +" normal");
		
		HSLobby = new HideAndSeekLobby(this);
		RRLobby = new RiverRaceLobby(this);
		
		//---------------------------------------
		//--------------Create GUIs--------------
		//---------------------------------------

		MenuGUI.initialize();
		StatsGUI.initialize();
		HideStatsGUI.initialize();
		RiverRaceStatsGUI.initialize();
		*/
		//Create menu item				
		menu = new ItemStack(Material.EMERALD);
		ItemMeta meta = menu.getItemMeta();
		meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Minigames Menu");
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
		
		//Create selection tool list
		/*
		selections = new ArrayList<RRSelection>();
		
		//Handles welcome message and gamemode
		new JoinEvent(this);
		new PlayerInteract(this);
		new InventoryClicked(this);
		new Wand(this);
		*/
		//--------------------------------------
		//---------------Commands---------------
		//--------------------------------------
		
		//Handles viewing own stats
	//	getCommand("mystats").setExecutor(new Mystats());
		/*
		//Handles joining hide and seek lobby
		getCommand("hide").setExecutor(new HSBJoin());
				
		//Allows Map-Makers to manage Hide and Seek maps
		getCommand("hsmap").setExecutor(new HSMap());
		
		//Allows Game-Makers to manually start and end River Race games
		getCommand("rr").setExecutor(new RiverRace());
		
		//Allows Map-Makers to manage River Race maps
		getCommand("rrmap").setExecutor(new RRMap());
		
		//Allows Map-Makers to manage River Race start points
		getCommand("rrgrid").setExecutor(new RRGrid());
		
		//Allows Map-Makers to manage River Race checkpoints
		getCommand("rrcheck").setExecutor(new RRCheck());
		
		//Allows devs to manage lobbies
		getCommand("lobbys").setExecutor(new Lobby());
		*/
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
}
