package de.presti.mudermystery.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

public class ArmorsUtils {
	ArmorStand e;
	Location loc;

	public ArmorsUtils(Location l) {
		loc = l;
		e = (ArmorStand) loc.getWorld().spawn(loc, ArmorStand.class);
	}

	public void spawn() {
		if(e == null) {
			e = (ArmorStand) loc.getWorld().spawn(loc, ArmorStand.class);
		}
		e.setItemInHand(new ItemStack(Material.BOW));
		e.setCustomName("§bBogen");
		e.setCustomNameVisible(true);
		e.teleport(loc);
		e.setGravity(false);
		e.setBasePlate(false);
		e.setCanPickupItems(false);
		e.setVisible(false);
	}

	public void despawn() {
		if (e != null) {
			e.remove();
			e = null;
		}
	}

	public ArmorStand getE() {
		return e;
	}

	public void setE(ArmorStand e) {
		this.e = e;
	}

	public Location getLoc() {
		return loc;
	}

	public void setLoc(Location loc) {
		this.loc = loc;
	}

}
