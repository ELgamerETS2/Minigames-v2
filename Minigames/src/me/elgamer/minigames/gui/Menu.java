package me.elgamer.minigames.gui;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

import me.elgamer.minigames.sql.GameTable;
import me.elgamer.minigames.sql.GuiTable;
import me.elgamer.minigames.utilities.User;
import me.elgamer.minigames.utilities.Utils;

public class Menu {

	public static Inventory inv;
	public static String inventory_name;
	public static int inv_rows = 3 * 9;

	public static void initialize() {
		inventory_name = ChatColor.AQUA + "" + ChatColor.BOLD + "Menu";

		inv = Bukkit.createInventory(null, inv_rows);

	}

	public static Inventory GUI (User u) {

		Inventory toReturn = Bukkit.createInventory(null, inv_rows, inventory_name);

		inv.clear();

		ResultSet results = GuiTable.getEntries();
		
		try {
			while (results.next()) {
						
				Utils.createItem(inv, Material.getMaterial(results.getString("Material")), 1, results.getInt("Slot"), ChatColor.AQUA + "" + ChatColor.BOLD + GameTable.getName(results.getInt("GameID")));

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			u.p.sendMessage(ChatColor.RED + "An error occured");
		}

		toReturn.setContents(inv.getContents());
		return toReturn;
	}

}
