package de.presti.mudermystery.listener;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import de.presti.mudermystery.main.Data;
import de.presti.mudermystery.scoreboard.ScoreboardManager;
import de.presti.mudermystery.utils.GameInfo;
import de.presti.mudermystery.utils.GameUtils;
import de.presti.mudermystery.utils.NPC;

public class Leave implements Listener {

	@EventHandler
	public void onLeave(PlayerQuitEvent e) {
		for(Player all : Bukkit.getOnlinePlayers()) {
			ScoreboardManager.updateonleave(all);
		}
		if (!GameInfo.isRunning()) {
			e.setQuitMessage("§7< " + e.getPlayer().getName());
		} else {
			e.setQuitMessage(null);
			if(e.getPlayer().getGameMode() != GameMode.ADVENTURE) {
				NPC npc = new NPC("§7", e.getPlayer().getLocation(), e.getPlayer());
				npc.spawn();
				npc.sleep(true);
			if(e.getPlayer() != GameInfo.getMurder() && e.getPlayer() != GameInfo.getDetectiv() && e.getPlayer() != GameInfo.getM2() && e.getPlayer() != GameInfo.getD2()) {
			if((GameInfo.getInnoa() - 1) <= 0) {
				for(Player all : Bukkit.getOnlinePlayers()) {
					all.sendMessage(Data.prefix + "§eDas Spiel ist beendet! Der §cMörder §ehat alle Spieler ermordet.");
					ScoreboardManager.removeScoreboard(all);
				}
				if(!GameInfo.isDead(GameInfo.murder)) {
				GameUtils.stop(GameInfo.murder);
				} else if(!GameInfo.isDead(GameInfo.m2)) {
					GameUtils.stop(GameInfo.m2);	
				}
			}
			} else if(e.getPlayer() == GameInfo.getDetectiv() || e.getPlayer() == GameInfo.getD2()) {
				Location loc = e.getPlayer().getLocation();
				Bukkit.broadcastMessage(Data.prefix + "Der Bogen des §bDetektivs §eliegt jetzt auf dem Boden.");
				loc.getWorld().dropItem(loc, GameInfo.getDetektivBow());
				GameInfo.dbowloc = loc.getBlock().getLocation();
				GameInfo.setDetectivstatus("§cTot");
				if((GameInfo.getInnoa() - 1 <= 0)) {
					for(Player all : Bukkit.getOnlinePlayers()) {
						all.sendMessage(Data.prefix + "§eDas Spiel ist beendet! Der §cMörder §ehat alle Spieler ermordet.");
						ScoreboardManager.removeScoreboard(all);
					}
					if(!GameInfo.isDead(GameInfo.murder)) {
						GameUtils.stop(GameInfo.murder);
						} else if(!GameInfo.isDead(GameInfo.m2)) {
							GameUtils.stop(GameInfo.m2);	
						}
				}
			} else {
				for(Player all : Bukkit.getOnlinePlayers()) {
					all.sendMessage(Data.prefix
							+ "Das Spiel ist beendet! Der §bDetektiv §eund die §aUnschuldigen §ehaben den §cMörder §egefunden.");
					ScoreboardManager.removeScoreboard(all);
					if(!GameInfo.isDead(GameInfo.detectiv)) {
						GameUtils.stop(GameInfo.detectiv);
					} else if(!GameInfo.isDead(GameInfo.d2)) {
						GameUtils.stop(GameInfo.d2);
					} else {
						GameUtils.stop(GameUtils.getRandom());
					}
				}
			}
			}
		}
	}

}
