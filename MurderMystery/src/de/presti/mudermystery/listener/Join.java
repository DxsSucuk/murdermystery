package de.presti.mudermystery.listener;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.scheduler.BukkitRunnable;

import de.presti.mudermystery.main.Data;
import de.presti.mudermystery.main.Main;
import de.presti.mudermystery.scoreboard.ScoreboardManager;
import de.presti.mudermystery.utils.GameInfo;
import de.presti.mudermystery.utils.GameUtils;

public class Join implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if (GameInfo.isRunning()) {
			e.setJoinMessage(null);
			new BukkitRunnable() {

				@Override
				public void run() {
					p.getInventory().clear();
					p.setAllowFlight(true);
					GameUtils.setInv(p);
					ScoreboardManager.sendToPlayer(p);
					for (Player all : Bukkit.getOnlinePlayers()) {
						if (all.getGameMode().equals(GameMode.ADVENTURE)) {
							p.hidePlayer(all);
						}
						all.hidePlayer(p);
					}
					p.teleport(GameInfo.getMitte());
					
					while(p.getGameMode() != GameMode.ADVENTURE) {
						p.setGameMode(GameMode.ADVENTURE);
					}
					
				}
			}.runTaskLater(Main.instance, 5L);
		} else {
			e.setJoinMessage("§7> " + e.getPlayer().getName());
			ScoreboardManager.sendToPlayer(p);
			for (Player all : Bukkit.getOnlinePlayers()) {
				ScoreboardManager.update(all);
			}
			new BukkitRunnable() {

				@Override
				public void run() {
					p.setGameMode(GameMode.SURVIVAL);
					p.teleport(GameInfo.getSpawn());
					GameUtils.setInv(p);
				}
			}.runTaskLater(Main.instance, 5L);

		}
	}

	@EventHandler
	public void onLogin(PlayerLoginEvent e) {
		if (GameInfo.isFull() && !e.getPlayer().hasPermission("mm.premium") && !e.getPlayer().hasPermission("mm.*")) {
			e.disallow(Result.KICK_FULL, Data.prefix + "§7Diese Runde ist leider voll!");
		} else if (GameInfo.isFull()
				&& (e.getPlayer().hasPermission("mm.premium") || e.getPlayer().hasPermission("mm.*"))) {
			Player r = GameUtils.getRandom();
			if (r.hasPermission("mm.premium") || r.hasPermission("mm.*")) {
				return;
			}

			r.kickPlayer(Data.prefix + "§7Du wurdest gekickt um Platz für einen Premium User zu schaffen!");
			e.allow();
		}
	}

}
