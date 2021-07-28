package me.elgamer.minigames.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.elgamer.minigames.Main;

public class Leave implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "This command can only be run by a player");
			return true;
		}
		
		Player p = (Player) sender;
		p.teleport(Main.lobby.spawn);
		
		Main.getUser(p).inLobby = true;
		
		p.sendMessage(ChatColor.GREEN + "Teleported to the Lobby!");
		return true;
		
	}

}
