package me.elgamer.minigames.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.elgamer.minigames.sql.GameTable;
import net.md_5.bungee.api.ChatColor;

public class Game implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (args.length < 2) {
			sender.sendMessage(ChatColor.RED + "/game add/remove <GameType>");
			return true;
		}
		
		if (args[0].equalsIgnoreCase("add")) {
			
			if (sender instanceof Player) {
				Player p = (Player) sender;
				if (!(p.hasPermission("minigames.game.add"))) {
					p.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
					return true;
				}
				
			}
			
			if (GameTable.addGame(args[1])) {
				sender.sendMessage(ChatColor.GREEN + args[1] + " added!");
				return true;
			} else {
				sender.sendMessage(ChatColor.RED + "An error occured");
				return true;
			}
		}
		
		if (args[0].equalsIgnoreCase("remove")) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				if (!(p.hasPermission("minigames.game.remove"))) {
					p.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
					return true;
				}
				
			}
			
			if (GameTable.gameExists(args[1])) {
				GameTable.removeGame(args[1]);
				sender.sendMessage(ChatColor.GREEN + args[0] + " removed!");
				return true;
			} else {
				sender.sendMessage(ChatColor.RED + "This game does not exist!");
				return true;
			}
		}

		return true;
	}

}
