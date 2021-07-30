package me.elgamer.minigames.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;

import me.elgamer.minigames.Main;
import me.elgamer.minigames.gui.Menu;
import me.elgamer.minigames.gui.Stats;
import me.elgamer.minigames.utilities.User;

public class PlayerInteract implements Listener {
	public PlayerInteract(Main plugin) {
		Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void interactEvent(PlayerInteractEvent e) {
		
		Player player;
		
		player = e.getPlayer();
		User u = Main.getUser(player);
		
		if (player.getOpenInventory().getType() != InventoryType.CRAFTING && e.getPlayer().getOpenInventory().getType() != InventoryType.CREATIVE)
		{
		    return;
		}
		
		if (player.getInventory().getItemInMainHand().equals(Main.menu)) {
			e.setCancelled(true);
			u.p.openInventory(Menu.GUI(u));
			return;		
		} else if (player.getInventory().getItemInMainHand().equals(Main.stats)) {			
			e.setCancelled(true);
			u.p.openInventory(Stats.GUI(u));
			return;
		}
	}
}
