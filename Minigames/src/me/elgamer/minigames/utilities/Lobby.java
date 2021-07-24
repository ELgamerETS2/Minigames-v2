package me.elgamer.minigames.utilities;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

public class Lobby {
	
	public Location spawn;
	public World lobbyWorld;
	
	public Lobby(FileConfiguration config) {
		
		lobbyWorld = Bukkit.getWorld(config.getString("world"));
		
		spawn = new Location(lobbyWorld,
				config.getDouble("spawn.x"),
				config.getDouble("spawn.y"),
				config.getDouble("spawn.z"),
				(float) config.getDouble("spawn.yaw"),
				(float) config.getDouble("spawn.pitch"));
		
	}

}
