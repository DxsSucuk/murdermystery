package de.presti.mudermystery.command;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.presti.mudermystery.main.Data;
import de.presti.mudermystery.main.Main;

public class MurderMysteryAdminCommand implements CommandExecutor {

	String prefix = Data.prefix + "§7";

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (p.hasPermission("mm.admin") || p.hasPermission("mm.*")) {
				if (args.length == 0) {
					p.sendMessage(prefix + "/mm maplist");
					p.sendMessage(prefix + "/mm setlobby");
					p.sendMessage(prefix + "/mm setspawn map 1-16");
					p.sendMessage(prefix + "/mm setitem map 1-16");
					p.sendMessage(prefix + "/mm setmitte map");
				} else if (args.length == 1) {
					if (args[0].equalsIgnoreCase("setlobby")) {
						p.sendMessage(prefix + "Der Spawnpoint für die Wartelobby wurde erfolgreich gesetzt!");
						Main.instance.location.setLocation("spawn", p.getLocation());
					} else if (args[0].equalsIgnoreCase("maplist")) {
						p.sendMessage(prefix + "Dies sind alle Maps:");
						for (int i = 0; i < Main.maps.length; i++) {
							p.sendMessage(prefix + Main.maps[i]);
						}
					} else {
						p.sendMessage(Data.prefix + "§cFehler!");
						p.sendMessage(prefix + "/mm maplist");
						p.sendMessage(prefix + "/mm setlobby");
						p.sendMessage(prefix + "/mm setspawn map 1-16");
						p.sendMessage(prefix + "/mm setitem map 1-16");
						p.sendMessage(prefix + "/mm setmitte map");
					}
				} else if (args.length == 2) {
					if (args[0].equalsIgnoreCase("setmitte")) {
						p.sendMessage(prefix + "Der Spawnpoint für die Toten wurde erfolgreich gesetzt!");
						Main.instance.location.setLocation(args[1] + ".mitte", p.getLocation());
					} else {
						p.sendMessage(Data.prefix + "§cFehler!");
						p.sendMessage(prefix + "/mm maplist");
						p.sendMessage(prefix + "/mm setlobby");
						p.sendMessage(prefix + "/mm setspawn map 1-16");
						p.sendMessage(prefix + "/mm setitem map 1-16");
						p.sendMessage(prefix + "/mm setmitte map");
					}
				} else if (args.length == 3) {
					if (args[0].equalsIgnoreCase("setspawn")) {
						p.sendMessage(prefix + "Der Spawnpoint " + args[2] + " für die Map " + args[1]
								+ " wurde erfolgreich gesetzt!");
						Main.instance.location.setLocation(args[1] + ".playerspawn." + args[2], p.getLocation());
					} else if (args[0].equalsIgnoreCase("setitem")) {
						p.sendMessage(prefix + "Der ItemSpawnpoint " + args[2] + " für die Map " + args[1]
								+ " wurde erfolgreich gesetzt!");
						Main.instance.location.setLocation2(args[1] + ".itemspawn." + args[2], p.getLocation().getBlock().getLocation());
					} else {
						p.sendMessage(Data.prefix + "§cFehler!");
						p.sendMessage(prefix + "/mm maplist");
						p.sendMessage(prefix + "/mm setlobby");
						p.sendMessage(prefix + "/mm setspawn map 1-16");
						p.sendMessage(prefix + "/mm setitem map 1-16");
						p.sendMessage(prefix + "/mm setmitte map");
					}
				} else {
					p.sendMessage(Data.prefix + "§cFehler!");
					p.sendMessage(prefix + "/mm maplist");
					p.sendMessage(prefix + "/mm setlobby");
					p.sendMessage(prefix + "/mm setspawn map 1-16");
					p.sendMessage(prefix + "/mm setitem map 1-16");
					p.sendMessage(prefix + "/mm setmitte map");
				}
			} else {
				p.sendMessage(Data.prefix + "§7Du kannst diesen Command nicht benutzen!");
			}
		}
		return false;
	}

}
