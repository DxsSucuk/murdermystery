package de.presti.mudermystery.listener;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

import de.presti.mudermystery.main.Data;
import de.presti.mudermystery.scoreboard.ScoreboardManager;
import de.presti.mudermystery.utils.GameInfo;

public class PickUPEvent implements Listener {

	@EventHandler
	public void onPickUp(PlayerPickupItemEvent e) {
		Player p = e.getPlayer();
		Item e2 = e.getItem();
		ItemStack item = e.getItem().getItemStack();
		if (p.getGameMode() != GameMode.ADVENTURE) {
			if (GameInfo.isRunning() && !GameInfo.isEnded()) {
				if (item.getType().equals(Material.GOLD_INGOT)) {
					ItemStack gold = new ItemStack(Material.GOLD_INGOT);
					if (p.getInventory().getItem(4) != null) {
						gold.setAmount(p.getInventory().getItem(4).getAmount() + item.getAmount());
					}
					p.getInventory().setItem(4, gold);
					if (p.getInventory().getItem(4).getAmount() >= 10) {
						if (p == GameInfo.getDetectiv() || p == GameInfo.getD2()) {
							if (p.getInventory().getItem(8) != null) {
								p.getInventory().setItem(8,
										new ItemStack(Material.ARROW, p.getInventory().getItem(8).getAmount() + 1));
							} else {
								p.getInventory().setItem(8, new ItemStack(Material.ARROW));
							}
						} else {
							p.getInventory().setItem(1, new ItemStack(Material.BOW));
							if (p.getInventory().getItem(8) != null) {
								p.getInventory().setItem(8,
										new ItemStack(Material.ARROW, p.getInventory().getItem(8).getAmount() + 1));
							} else {
								p.getInventory().setItem(8, new ItemStack(Material.ARROW));
							}
						}
						if (p.getInventory().getItem(4).getAmount() > 10) {
							p.getInventory().getItem(4).setAmount((p.getInventory().getItem(4).getAmount() - 10));
						} else {
							p.getInventory().setItem(4, null);
						}
					}
					e.getItem().remove();
					e.setCancelled(true);
				} else if (item.getType().equals(Material.BOW) && p != GameInfo.getMurder() && p != GameInfo.getM2()) {
					GameInfo.dbowloc = GameInfo.getSpawn();
					for (Player all : Bukkit.getOnlinePlayers()) {
						all.sendMessage(Data.prefix + "Der Bogen des §bDetektivs §ewurde aufgesammelt.");
						ScoreboardManager.update(all);
					}
					p.getInventory().setItem(0, GameInfo.getDetektivBow());
					p.getInventory().setItem(8, new ItemStack(Material.ARROW));
					e.getItem().remove();
					e.setCancelled(true);
				} else {
					e.setCancelled(true);
				}
			} else {
				e.setCancelled(true);
			}
		} else {
			e.setCancelled(true);
		}
	}

}
