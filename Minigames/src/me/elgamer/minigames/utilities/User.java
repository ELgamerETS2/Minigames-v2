package me.elgamer.minigames.utilities;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.elgamer.minigames.Main;

public class User {

	public Player p;

	public boolean inLobby;

	public User(Player p) {
		this.p = p;

		FileConfiguration config = Main.getInstance().getConfig();

		if (p.getWorld().getName().equals(config.getString("world"))) {
			inLobby = true;		
		} else {
			inLobby = false;
		}
	}

}
