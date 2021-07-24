package me.elgamer.minigames.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import me.elgamer.minigames.Main;
import me.elgamer.minigames.utilities.User;

public class LeaveEvent implements Listener {
	
	Main plugin;

	public LeaveEvent(Main plugin) {
		this.plugin = plugin;
		Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onPlayerLeave (PlayerQuitEvent e) {

		Player p = e.getPlayer();
		User u = Main.getUser(p);
		Main.users.remove(u);

	}

}
