package me.elgamer.minigames.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.elgamer.minigames.Main;
import me.elgamer.minigames.utilities.User;

public class JoinEvent implements Listener {

	Main plugin;

	public JoinEvent(Main plugin) {
		this.plugin = plugin;
		Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onPlayerJoin (PlayerJoinEvent e) {

		Player p = e.getPlayer();
		User u = new User(p);
		Main.users.add(u);
		
		if (!(u.inLobby)) {
			u.p.teleport(Main.lobby.spawn);
		}

	}

}
