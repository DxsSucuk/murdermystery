package de.presti.mudermystery.listener;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;
import org.bukkit.material.Door;

import de.presti.mudermystery.main.Data;
import de.presti.mudermystery.main.Main;
import de.presti.mudermystery.utils.InvManager;

import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;

public class InteractEvent implements Listener {

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		Player p = (Player) e.getPlayer();

		e.setCancelled(true);
		
		if ((e.getClickedBlock() != null)) {
			if(e.getClickedBlock() instanceof Door) {
				e.setCancelled(false);
			}
		}

		if (e.getItem() != null && e.getItem().hasItemMeta()) {
			if (e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§cLobby")) {
				p.sendMessage(Data.prefix + "§7Du wirst in die Lobby geschickt!");
				Main.instance.connect(p, "Lobby-1");
			} else if (e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§2Map auswählen")) {
				p.performCommand("forcemap");
			} else if (e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6Start")) {
				p.performCommand("start");
			} else if (e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§2Teleportier dich zu Spielern!")) {
				InvManager.openTele(p);
			} else if (e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§cEvents")) {
				InvManager.openCheats(p);
			}
		}
	}

	@EventHandler
	public void onOpenInv(InventoryOpenEvent e) {
		String name = e.getInventory().getName();
		if (!isCustomInv(name)) {
			e.getView().close();
			e.getPlayer().closeInventory();
			e.setCancelled(true);
		}
	}

	public boolean isCustomInv(String name) {
		return ((name.equalsIgnoreCase(InvManager.cheats.getName()))
				|| (name.equalsIgnoreCase(InvManager.inv.getName()))
				|| (name.equalsIgnoreCase(InvManager.speed.getName()))
				|| (name.equalsIgnoreCase(InvManager.tele.getName())));
	}

	@EventHandler
	public void onInteractEntity(PlayerInteractEntityEvent e) {
		e.setCancelled(true);
	}

	@EventHandler
	public void onInteractEntityAtEntity(EntityInteractEvent e) {
		e.setCancelled(true);
	}

	@EventHandler
	public void onPlayerArmorStandManipulate(PlayerArmorStandManipulateEvent e) {
		e.setCancelled(true);
	}
}
