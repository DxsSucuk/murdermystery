package de.presti.mudermystery.utils;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;


public class SetItems {
	
    public static ItemStack build(Material m, int anzahl, int sh, String name, String lore) {
        ItemStack item = new ItemStack(m, anzahl, (short)sh);
        ItemMeta itemm = item.getItemMeta();
        itemm.setDisplayName(name);
        ArrayList<String> list = new ArrayList<String>();
        list.add(lore);
        itemm.setLore(list);
        item.setItemMeta(itemm);
        return item;
    }
    public static ItemStack build(Material m, String name) {
        ItemStack item = new ItemStack(m);
        ItemMeta itemm = item.getItemMeta();
        itemm.setDisplayName(name);
        item.setItemMeta(itemm);
        return item;
    }
    public static ItemStack build(Material m, int anzahl, int sh, String name, ArrayList<String> lore) {
        ItemStack item = new ItemStack(m, anzahl, (short)sh);
        ItemMeta itemm = item.getItemMeta();
        itemm.setDisplayName(name);
        itemm.setLore(lore);
        item.setItemMeta(itemm);
        return item;
    }
    
    public static ItemStack buildEnch(Material m, int anzahl, int sh, String name, String lore) {
        ItemStack item = new ItemStack(m, anzahl, (short)sh);
        ItemMeta itemm = item.getItemMeta();
        itemm.setDisplayName(name);
        ArrayList<String> list = new ArrayList<String>();
        list.add(lore);
        itemm.setLore(list);
        itemm.addEnchant(Enchantment.DURABILITY, 1, true);
        item.setItemMeta(itemm);
        return item;
    }
    
    public static ItemStack buildSkull(Material m, String name, String player) {
        ItemStack item = new ItemStack(m, 1, (short)3);
        SkullMeta itemm = (SkullMeta) item.getItemMeta();
        itemm.setDisplayName(name);
        itemm.setOwner(player);
        item.setItemMeta(itemm);
        return item;
    }
	public static ItemStack buildCode(Material m, int i, int j, String name) {
        ItemStack item = new ItemStack(m, i, (short)j);
        ItemMeta itemm = item.getItemMeta();
        itemm.setDisplayName(name);
        item.setItemMeta(itemm);
        return item;
	}
}
