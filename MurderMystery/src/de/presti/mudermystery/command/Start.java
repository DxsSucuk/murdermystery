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

public class Start implements CommandExecutor {

	public BukkitTask id;
	public boolean playerused = false;

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (p.hasPermission("mm.start") || p.hasPermission("mm.*")) {
				if (!playerused) {
					if(Bukkit.getOnlinePlayers().size() >= 2) {
					p.sendMessage(Data.prefix + "§7Du hast den Countdown auf 15 Sekunden gestellt!");
					playerused = true;
					Bukkit.getScheduler().cancelTask(Main.instance.taskid.getTaskId());
					GameInfo.setCountdown(15);
					id = Bukkit.getScheduler().runTaskTimer(Main.getInstance(), new Runnable() {

						@Override
						public void run() {
							for (Player all : Bukkit.getOnlinePlayers()) {
								ScoreboardManager.update(all);
							}
							if (GameInfo.getCountdown() == 60) {
								Bukkit.broadcastMessage(Data.prefix + "§7Das Spiel startet in §e"
										+ GameInfo.getCountdown() + " §7Sekunden");
							} else if (GameInfo.getCountdown() == 30) {
								Bukkit.broadcastMessage(Data.prefix + "§7Das Spiel startet in §e"
										+ GameInfo.getCountdown() + " §7Sekunden");
							} else if (GameInfo.getCountdown() <= 15 && GameInfo.getCountdown() > 1) {
								Bukkit.broadcastMessage(Data.prefix + "§7Das Spiel startet in §e"
										+ GameInfo.getCountdown() + " §7Sekunden");
							} else if (GameInfo.getCountdown() == 1) {
								Bukkit.broadcastMessage(Data.prefix + "§7Das Spiel startet in §eeiner §7Sekunde");
							} else if (GameInfo.getCountdown() <= 0) {
								GameUtils.start();
								Bukkit.getScheduler().cancelTask(id.getTaskId());
							}
							GameInfo.countdown--;
						}
					}, 0L, 20L);
					} else {
						p.sendMessage(Data.prefix + "§7Es müssen mindestens 2 Spieler Online sein!");
					}
				} else {
					p.sendMessage(Data.prefix + "§7Jemand hat diesen Command schon benutzt!");
				}
			} else {
				p.sendMessage(Data.prefix + "§7Du kannst diesen Command nicht benutzen!");
			}
		}
		return false;
	}

}
