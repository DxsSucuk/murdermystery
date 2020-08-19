package de.presti.mudermystery.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockEvent implements Listener {
	
	@EventHandler
	public void onBlock(BlockPlaceEvent e) {
		e.setCancelled(true);
	}

	@EventHandler
	public void onBlock2(BlockBreakEvent e) {
		e.setCancelled(true);
	}
	
}
