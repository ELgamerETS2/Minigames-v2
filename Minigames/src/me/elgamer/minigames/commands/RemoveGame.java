package me.elgamer.minigames.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.elgamer.minigames.sql.GameTable;
import net.md_5.bungee.api.ChatColor;

public class RemoveGame implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (!(p.hasPermission("minigames.removegame"))) {
				p.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
				return true;
			}
			
		}
		
		if (args.length < 1) {
			sender.sendMessage(ChatColor.RED + "/removegame <GameType>");
			return true;
		}
		
		if (GameTable.gameExists(args[0])) {
			GameTable.removeGame(args[0]);
			sender.sendMessage(ChatColor.GREEN + args[0] + " removed!");
		} else {
			sender.sendMessage(ChatColor.RED + "This game does not exist!");
		}
		
		
		return true;
	}

}
