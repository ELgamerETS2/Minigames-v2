package me.elgamer.minigames.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.elgamer.minigames.Main;
import me.elgamer.minigames.sql.GuiTable;
import net.md_5.bungee.api.ChatColor;

public class RemoveFromGui implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (!(p.hasPermission("minigames.removeguientry"))) {
				p.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
				return true;
			}
			
		}
		
		if (args.length < 1) {
			sender.sendMessage(ChatColor.RED + "/removeguientry <Slot>");
			return true;
		}
					
		int slot;
		
		try {
			slot = Integer.parseInt(args[1]);
			
			if (!(Main.slots.contains(slot))) {
				sender.sendMessage(ChatColor.RED + "This is not a valid slot, it must lie between 11-17, 20-26 or 29-35!");
				return true;
			}
			
		} catch (NumberFormatException e) {
			sender.sendMessage(ChatColor.RED + args[1] + " is not a valid slot number!");
			return true;
		}
		
		if (!(GuiTable.slotTaken(slot))) {
			sender.sendMessage(ChatColor.RED + "This slot is not used in the gui!");
			return true;
		}
		
		if (GuiTable.removeEntry(slot)) {
			sender.sendMessage(ChatColor.GREEN + "Removed slot " + args[1] + " from the gui!");
		} else {
			sender.sendMessage(ChatColor.RED + "An error occured");
		}
		
		
		return true;
	}

}
