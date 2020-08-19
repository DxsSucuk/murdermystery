package de.presti.mudermystery.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import de.presti.mudermystery.main.Data;
import de.presti.mudermystery.main.Main;
import de.presti.mudermystery.scoreboard.ScoreboardManager;
import de.presti.mudermystery.utils.GameInfo;
import de.presti.mudermystery.utils.GameUtils;
import de.presti.statsapi.MySQL;
import de.presti.statsapi.StatsAPI;

public class Stats implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (p.hasPermission("mm.stats") || p.hasPermission("mm.*")) {
				p.sendMessage(Data.prefix + "Deine Stats:");
				try {
				p.sendMessage(Data.prefix + "Wins: " + Main.stats.getWinsMM(p.getUniqueId().toString()));
				p.sendMessage(Data.prefix + "Kills: " + Main.stats.getKillsMM(p.getUniqueId().toString()));
				} catch(Exception e) {
					Main.getInstance().stats.sql.close();
					Main.getInstance().stats.sql = new MySQL("stats", "CqhCF3OL4bz5kOBk", "localhost", "stats", 3306);
					p.sendMessage(Data.prefix + "Wins: " + Main.stats.getWinsMM(p.getUniqueId().toString()));
					p.sendMessage(Data.prefix + "Kills: " + Main.stats.getKillsMM(p.getUniqueId().toString()));
				}
			} else {
				p.sendMessage(Data.prefix + "§7Du kannst diesen Command nicht benutzen!");
			}
		}
		return false;
	}

}
