package de.presti.mudermystery.listener;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import de.presti.mudermystery.main.Data;
import de.presti.mudermystery.main.Main;
import de.presti.mudermystery.utils.GameInfo;

public class ASyncChat implements Listener {

	ArrayList<Player> gg = new ArrayList<>();

	@EventHandler
	public void onASyncChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		if (GameInfo.running) {
			if (p.getGameMode().equals(GameMode.ADVENTURE)) {
				for (Player all : Bukkit.getOnlinePlayers()) {
					if (all.getGameMode().equals(GameMode.ADVENTURE)) {
						all.sendMessage("§7[TOT] §8" + p.getName() + " §7: " + e.getMessage());
					}
				}
				e.setCancelled(true);
			}
		}

		if (GameInfo.ended) {
			if (e.getMessage().toLowerCase().contains("gg")) {
				if (!gg.contains(p)) {
					p.sendMessage("§e+10Shrubs");
					Main.coins.addCoins(p.getUniqueId().toString(), 10);
					gg.add(p);
				}
			}
		}

	}

}
