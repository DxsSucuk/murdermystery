package de.presti.mudermystery.utils;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import de.presti.mudermystery.main.Main;

public class InvManager {

	public static Inventory inv = Bukkit.createInventory(null, 9 * 4, "§2Map auswählen");

	public static void openInv(Player p) {
		for (int i = 0; i < inv.getSize(); i++) {
			inv.setItem(i, SetItems.buildCode(Material.STAINED_GLASS_PANE, 1, 15, "§8"));
		}

		int i2 = 0;
		for (int i = 0; i < Main.maps.length; i++) {
			inv.setItem(i2, SetItems.build(Material.BOOK, "§6" + Main.maps[i]));
			i2++;
		}

		p.openInventory(inv);
	}

	public static Inventory tele = Bukkit.createInventory(null, 9 * 4, "§2Teleporter");

	public static void openTele(Player p) {
		for (int i = 0; i < tele.getSize(); i++) {
			tele.setItem(i, SetItems.buildCode(Material.STAINED_GLASS_PANE, 1, 15, "§8"));
		}

		int i2 = 0;
		for (Player all : Bukkit.getOnlinePlayers()) {
			if (all.getGameMode() != GameMode.ADVENTURE) {
				tele.setItem(i2, SetItems.buildSkull(Material.SKULL_ITEM, "§e" + all.getName(), all.getName()));
				i2++;
			}
		}

		p.openInventory(tele);
	}
	
	public static Inventory cheats = Bukkit.createInventory(null, 9 * 4, "§2Admin Events");

	public static void openCheats(Player p) {
		
		for (int i = 0; i < cheats.getSize(); i++) {
			cheats.setItem(i, SetItems.buildCode(Material.STAINED_GLASS_PANE, 1, 15, "§8"));
		}

		
		cheats.setItem(3, SetItems.build(Material.REDSTONE, "§cAlle gegen einen!"));
		cheats.setItem(5, SetItems.build(Material.DIAMOND, "§bIch bin der Detektiv"));
		cheats.setItem(14, SetItems.build(Material.BLAZE_POWDER, "§rSPEED"));
		cheats.setItem(12, SetItems.build(Material.APPLE, "§cHorror"));
		cheats.setItem(23, SetItems.build(Material.LAPIS_ORE, "§1Tatütata"));

		p.openInventory(cheats);
	}
	public static Inventory speed = Bukkit.createInventory(null, 9, "§2Admin Events > Speed");
	
	public static void openSpeed(Player p) {
		
		for (int i = 0; i < speed.getSize(); i++) {
			speed.setItem(i, SetItems.buildCode(Material.STAINED_GLASS_PANE, 1, 15, "§8"));
		}
		
		speed.setItem(2, SetItems.build(Material.STONE_BUTTON, "§cWeniger"));
		
		speed.setItem(4, SetItems.build(Material.BLAZE_POWDER, "§rSPEED §2" + GameInfo.speedlvl));
		
		speed.setItem(6, SetItems.build(Material.WOOD_BUTTON, "§aMehr"));
		
		speed.setItem(8, SetItems.build(Material.BARRIER, "§cZurück"));
		
		p.openInventory(speed);
	}

}
