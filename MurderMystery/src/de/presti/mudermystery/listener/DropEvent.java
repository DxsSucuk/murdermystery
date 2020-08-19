package de.presti.mudermystery.listener;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

import de.presti.mudermystery.main.Data;
import de.presti.mudermystery.scoreboard.ScoreboardManager;
import de.presti.mudermystery.utils.GameInfo;
import de.presti.mudermystery.utils.GameUtils;
import de.presti.mudermystery.utils.InvManager;
import de.presti.mudermystery.utils.SetItems;

public class DropEvent implements Listener {

	@EventHandler
	public void onDrop(PlayerDropItemEvent e) {
		if(e.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
			e.getItemDrop().remove();
			GameUtils.setInv(e.getPlayer());
		}
		e.setCancelled(true);
	}

	@EventHandler
	public void onInvClick(InventoryClickEvent e) {
		Player p = (Player) e.getView().getPlayer();
		if (e.getCurrentItem() != null && e.getView() != null && e.getCurrentItem().getItemMeta() != null) {
			e.setCancelled(true);
			if (e.getInventory().getName().equalsIgnoreCase(InvManager.inv.getName())) {
				if (e.getCurrentItem().getType() == Material.BOOK) {
					if (e.getView().getPlayer().hasPermission("mm.forcemap")
							|| e.getView().getPlayer().hasPermission("mm.*")) {
						if (!GameInfo.isRunning()) {
							GameInfo.setMap(e.getCurrentItem().getItemMeta().getDisplayName().replace("§6", ""));
							e.getView().getPlayer().sendMessage(Data.prefix + "§7Die Map wird nun "
									+ e.getCurrentItem().getItemMeta().getDisplayName() + " §7sein!");
							e.getView().close();
							for(Player all : Bukkit.getOnlinePlayers()) {
								ScoreboardManager.update(all);
							}
						} else {
							e.getView().close();
							e.getView().getPlayer().sendMessage(Data.prefix + "§7Die Runde hat schon begonnen!");
						}
					} else {
						e.getView().close();
						e.getView().getPlayer().sendMessage(Data.prefix + "Du kannst keine Map Forcemappen!");
					}
				}
			} else if (e.getInventory().getName().equalsIgnoreCase(InvManager.tele.getName())) {
				if(e.getCurrentItem().getType() == Material.SKULL_ITEM) {
					Player t = Bukkit.getPlayer(e.getCurrentItem().getItemMeta().getDisplayName().replace("§e", ""));
					e.getView().getPlayer().teleport(t);
				}
			} else if(e.getInventory().getName().equalsIgnoreCase(InvManager.cheats.getName())) {
				if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§cAlle gegen einen!")) {
					GameInfo.murder = (Player) e.getView().getPlayer();
					GameInfo.forcem = true;
				} else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§bIch bin der Detektiv")) {
					GameInfo.detectiv = (Player) e.getView().getPlayer();
					GameInfo.forced = true;
				} else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§rSPEED")) {
					e.getView().close();
					GameInfo.speed = true;
					InvManager.openSpeed(p);
				} else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§cHorror")) {
					e.getView().close();
					GameInfo.horror = true;
				} else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§1Tatütata")) {
					e.getView().close();
					GameInfo.tatutata = true;
				}
			} else if(e.getInventory().getName().equalsIgnoreCase(InvManager.speed.getName())) {
				if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§cWeniger")) {
					GameInfo.speedlvl--;
					e.getView().setItem(4, SetItems.build(Material.BLAZE_POWDER, "§rSPEED §2" + GameInfo.speedlvl));
				} else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aMehr")) {
					GameInfo.speedlvl++;
					e.getView().setItem(4, SetItems.build(Material.BLAZE_POWDER, "§rSPEED §2" + GameInfo.speedlvl));
				} else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§cZurück")) {
					e.getView().close();
					InvManager.openCheats(p);
				}
			} else {
				if(e.getView().getPlayer().getGameMode().equals(GameMode.ADVENTURE)) {
					GameUtils.setInv((Player) e.getView().getPlayer());
				}
			}
		}
	}

}
