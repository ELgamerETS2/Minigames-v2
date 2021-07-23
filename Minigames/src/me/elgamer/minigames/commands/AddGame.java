package me.elgamer.minigames.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.elgamer.minigames.sql.GameTable;
import net.md_5.bungee.api.ChatColor;

public class AddGame implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (!(p.hasPermission("minigames.addgame"))) {
				p.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
				return true;
			}
			
		}
		
		if (args.length < 2) {
			sender.sendMessage(ChatColor.RED + "/addgame <GameType> <World>");
			return true;
		}
		
		if (GameTable.addGame(args[0], args[1])) {
			sender.sendMessage(ChatColor.GREEN + "Game added with name " + args[0] + " in world " + args[1]);
		} else {
			sender.sendMessage(ChatColor.RED + "An error occured");
		}
		
		
		return true;
	}
	
	

}
