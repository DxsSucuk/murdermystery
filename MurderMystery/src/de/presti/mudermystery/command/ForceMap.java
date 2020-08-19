package de.presti.mudermystery.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.presti.mudermystery.main.Data;
import de.presti.mudermystery.utils.InvManager;

public class ForceMap implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player)sender;
			if(p.hasPermission("mm.forcemap") || p.hasPermission("mm.*")) {
				InvManager.openInv(p);
			} else {
				p.sendMessage(Data.prefix + "Du kannst keine Map Forcemappen!");
			}
		}
		return false;
	}

}
