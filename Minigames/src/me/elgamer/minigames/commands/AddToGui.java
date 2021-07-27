package me.elgamer.minigames.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.elgamer.minigames.Main;
import me.elgamer.minigames.sql.GameTable;
import me.elgamer.minigames.sql.GuiTable;
import net.md_5.bungee.api.ChatColor;

public class AddToGui implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (!(p.hasPermission("minigames.gui.add"))) {
				p.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
				return true;
			}
			
		}
		
		if (args.length < 4) {
			sender.sendMessage(ChatColor.RED + "/gui add <GameType> <Slot> <Material>");
			return true;
		}
		
		if (!(args[0].equalsIgnoreCase("add"))) {
			sender.sendMessage(ChatColor.RED + "/gui add <GameType> <Slot> <Material>");
			return true;
		}
		
		int gameID;
		if (GameTable.gameExists(args[1])) {
			gameID = GameTable.getID(args[1]);
		} else {
			sender.sendMessage(ChatColor.RED + "This game does not exist!");
			return true;
		}
		
		if (GuiTable.gameExists(gameID)) {
			sender.sendMessage(ChatColor.RED + "This game has already been added to the gui!");
			return true;
		}
		
		int slot;
		
		try {
			slot = Integer.parseInt(args[2]);
			
			if (!(Main.slots.contains(slot))) {
				sender.sendMessage(ChatColor.RED + "This slot cannot be used, it must lie between 11-17, 20-26 or 29-35!");
				return true;
			}
			
		} catch (NumberFormatException e) {
			sender.sendMessage(ChatColor.RED + args[2] + " is not a valid slot number!");
			return true;
		}
		
		if (GuiTable.slotTaken(slot)) {
			sender.sendMessage(ChatColor.RED + "This slot is already used in the gui!");
			return true;
		}
		
		if (GuiTable.addEntry(slot, gameID, args[3])) {
			sender.sendMessage(ChatColor.GREEN + args[1] + " added to gui in slot " + args[2] + " with material " + args[3] + "!");
		} else {
			sender.sendMessage(ChatColor.RED + "An error occured");
		}
		
		
		return true;
	}

}
