package de.presti.mudermystery.listener;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import de.presti.mudermystery.main.Data;
import de.presti.mudermystery.scoreboard.ScoreboardManager;
import de.presti.mudermystery.utils.GameInfo;
import de.presti.mudermystery.utils.GameUtils;

public class MoveEvent implements Listener {

	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if (GameInfo.isRunning()) {
			if (p.getLocation().getWorld().equals(GameInfo.dbowloc.getWorld())
					&& p.getLocation().getBlock().getLocation().distance(GameInfo.dbowloc) <= 1.0
					&& p != GameInfo.murder && p.getGameMode() != GameMode.ADVENTURE && p != GameInfo.getM2()) {
				GameUtils.as.despawn();
				GameInfo.dbowloc = GameInfo.getSpawn();
				p.getInventory().setItem(0, GameInfo.getDetektivBow());
				if (p.getInventory().getItem(1) != null) {
					p.getInventory().setItem(1, null);
				}
				if (p.getInventory().getItem(8) == null) {
					p.getInventory().setItem(8, new ItemStack(Material.ARROW));
				}
				if (GameInfo.isDead(GameInfo.detectiv)) {
					GameInfo.detectiv = p;
				} else if (GameInfo.isDead(GameInfo.d2)) {
					GameInfo.d2 = p;
				}
				GameInfo.setDetectivstatus("§aLebt");
				for (Player all : Bukkit.getOnlinePlayers()) {
					all.sendMessage(Data.prefix + "Der Bogen des §bDetektivs §ewurde aufgesammelt.");
					ScoreboardManager.update(all);
				}
			}
		} else {
			if (p.getWorld().equals(GameInfo.getSpawn().getWorld())) {
				if (p.getLocation().distance(GameInfo.getSpawn()) > 100) {
					p.teleport(GameInfo.getSpawn());
				}
			}
		}
	}

}
