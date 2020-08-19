package de.presti.mudermystery.listener;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.bukkit.potion.PotionEffectType;

import de.presti.mudermystery.main.Data;
import de.presti.mudermystery.main.Main;
import de.presti.mudermystery.scoreboard.ScoreboardManager;
import de.presti.mudermystery.utils.GameInfo;
import de.presti.mudermystery.utils.GameUtils;
import de.presti.mudermystery.utils.NPC;
import net.minecraft.server.v1_8_R3.ItemSword;

public class DamageEvent implements Listener {

	@EventHandler
	public void entityspawn(EntitySpawnEvent e) {
		if (e.getEntityType() == EntityType.DROPPED_ITEM && e.getEntityType() != EntityType.ARMOR_STAND) {
			Item i = (Item) e.getEntity();
			if (i.getItemStack().getType() == Material.BOW) {
				e.setCancelled(true);
			} else {
				e.setCancelled(false);
			}
		} else if (e.getEntityType() == EntityType.ARMOR_STAND) {
			e.setCancelled(false);
		} else {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void damage(EntityDamageEvent e) {
		e.setCancelled(true);
	}

	@EventHandler
	public void onBlockDamage(EntityDamageByBlockEvent e) {
		e.setCancelled(true);
	}

	@EventHandler
	public void onHunger(FoodLevelChangeEvent e) {
		e.setFoodLevel(20);
		e.setCancelled(true);
	}

	@EventHandler
	public void onHangigBreak(HangingBreakEvent e) {
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onEntityDamage(EntityDamageByEntityEvent e) {
		
		if (e.getDamager() instanceof Player && e.getEntity() instanceof Player) {
			Player t = (Player) e.getEntity();
			Player p = (Player) e.getDamager();
			if (p.getItemInHand().getType().equals(Material.IRON_SWORD)) {
				Location loc = t.getLocation();
				if (GameInfo.isRunning()) {
					
					if((p == GameInfo.murder && t == GameInfo.m2) || (p == GameInfo.m2 && t == GameInfo.murder)) {
						e.setCancelled(true);
						return;
					}
					
					if (p == GameInfo.murder || p == GameInfo.m2) {
						
						NPC npc = new NPC("§7", t.getLocation(), t);
						npc.spawn();
						npc.sleep(true);
						
						Main.stats.addKillMM(p.getUniqueId().toString(), 1);

						t.removePotionEffect(PotionEffectType.SPEED);
						
						t.teleport(GameInfo.getMitte());
						t.removePotionEffect(PotionEffectType.SPEED);
						t.setGameMode(GameMode.ADVENTURE);
						t.setAllowFlight(true);
						GameUtils.setInv(t);
						if (t == GameInfo.detectiv || t == GameInfo.d2) {
							GameInfo.dbowloc = loc.getBlock().getLocation();
							GameInfo.setDetectivstatus("§cTot");
							Bukkit.broadcastMessage(Data.prefix + "Der Bogen des §bDetektivs §eliegt jetzt auf dem Boden.");
							Bukkit.getWorld(GameInfo.getMap()).dropItem(loc, GameInfo.getDetektivBow());
							GameUtils.as.setLoc(GameInfo.dbowloc);
							GameUtils.as.spawn();
							if (GameInfo.tatutata) {
								if (t == GameInfo.detectiv) {
									GameInfo.detectiv = null;
								} else if (t == GameInfo.d2) {
									GameInfo.d2 = null;
								}
							} else {
								GameInfo.detectiv = null;
							}
						}
						for (Player all : Bukkit.getOnlinePlayers()) {
							all.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 0.5F, 0.5F);
							all.hidePlayer(t);
							
							
							ScoreboardManager.update(all);
							if (GameInfo.getInnoa() == 0) {
								ScoreboardManager.removeScoreboard(all);
								all.sendMessage(Data.prefix
										+ "§eDas Spiel ist beendet! Der §cMörder §ehat alle Spieler ermordet.");
							}
						}
						if (GameInfo.getInnoa() == 0) {
							try {
								Main.coins.addCoins(GameInfo.murder.getUniqueId().toString(), 100);
								Main.stats.addWinMM(GameInfo.murder.getUniqueId().toString(), 1);
								if (GameInfo.horror) {
									Main.coins.addCoins(GameInfo.m2.getUniqueId().toString(), 100);
									Main.stats.addWinMM(GameInfo.m2.getUniqueId().toString(), 1);
								}
							} catch (Exception e5) {
							}
							GameUtils.stop(GameInfo.murder);
						}
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
		} else if (e.getDamager() instanceof Projectile && e.getEntity() instanceof Player) {
			Projectile pro = (Projectile) e.getDamager();
			Player t = (Player) e.getEntity();
			Player p = (Player) pro.getShooter();
			Location loc = t.getLocation();
			if (GameInfo.isRunning()) {
				if (p == GameInfo.murder || p == GameInfo.m2) {
					Main.stats.addKillMM(p.getUniqueId().toString(), 1);
					if (t == GameInfo.detectiv || t == GameInfo.d2) {
						Bukkit.broadcastMessage(Data.prefix + "Der Bogen des §bDetektivs §eliegt jetzt auf dem Boden.");
						loc.getWorld().dropItemNaturally(loc, GameInfo.getDetektivBow());
						GameInfo.dbowloc = loc.getBlock().getLocation();
						GameInfo.setDetectivstatus("§cTot");
						GameUtils.as.setLoc(GameInfo.dbowloc);
						GameUtils.as.spawn();
						if (GameInfo.tatutata) {
							if (t == GameInfo.detectiv) {
								GameInfo.detectiv = null;
							} else if (t == GameInfo.d2) {
								GameInfo.d2 = null;
							}
						} else {
							GameInfo.detectiv = null;
						}
					} else if(t == GameInfo.getMurder() || t == GameInfo.getM2()) {
						return;
					}
					
					NPC npc = new NPC("§7", t.getLocation(), t);
					npc.spawn();
					npc.sleep(true);
					
					t.teleport(GameInfo.getMitte());
					t.setGameMode(GameMode.ADVENTURE);
					t.getActivePotionEffects().clear();
					t.setAllowFlight(true);
					GameUtils.setInv(t);
					for (Player all : Bukkit.getOnlinePlayers()) {
						if (t == GameInfo.detectiv || t == GameInfo.d2) {
							all.playSound(t.getLocation(), Sound.WITHER_SPAWN, 1F, 1F);
						}
						all.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 0.5F, 0.5F);
						all.hidePlayer(t);
						if (GameInfo.getInnoa() == 0) {
							ScoreboardManager.removeScoreboard(all);
							all.sendMessage(
									Data.prefix + "§eDas Spiel ist beendet! Der §cMörder §ehat alle Spieler ermordet.");
						} else {
							ScoreboardManager.update(all);
						}
					}
					if (GameInfo.getInnoa() == 0) {
						try {
							Main.coins.addCoins(GameInfo.murder.getUniqueId().toString(), 100);
						} catch (Exception e5) {
						}
						GameUtils.stop(GameInfo.murder);
					}
					e.setCancelled(true);
				} else if (t == GameInfo.murder || t == GameInfo.m2) {
					Main.stats.addKillMM(p.getUniqueId().toString(), 1);
					t.teleport(GameInfo.getMitte());
					t.setGameMode(GameMode.ADVENTURE);
					t.getActivePotionEffects().clear();
					t.setAllowFlight(true);
					GameUtils.setInv(t);
					if (GameInfo.horror) {
						for (Player all : Bukkit.getOnlinePlayers()) {
							if (GameInfo.isDead(GameInfo.murder) && GameInfo.isDead(GameInfo.m2)) {
								all.sendMessage(Data.prefix
										+ "Das Spiel ist beendet! Der §bDetektiv §eund die §aUnschuldigen §ehaben den §cMörder §egefunden.");
								all.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 0.5F, 0.5F);
								all.hidePlayer(t);
								try {
									if (all != GameInfo.murder && all != GameInfo.m2) {
										Main.coins.addCoins(all.getUniqueId().toString(), 100);
										Main.stats.addWinMM(all.getUniqueId().toString(), 1);
									}
								} catch (Exception e4) {
								}
								ScoreboardManager.removeScoreboard(all);
							} else {
								all.sendMessage(Data.prefix
										+ "Ein Mörder wurde besiegt!");
								all.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 0.5F, 0.5F);
								all.hidePlayer(t);
								ScoreboardManager.update(all);
								if(t == GameInfo.m2) {
									GameInfo.m2 = null;
								} else {
									GameInfo.murder = null;
								}
							}
						}
						if (GameInfo.isDead(GameInfo.murder) && GameInfo.isDead(GameInfo.m2)) {
							GameUtils.stop(p);
						}
					} else {
						for (Player all : Bukkit.getOnlinePlayers()) {
							all.sendMessage(Data.prefix
									+ "Das Spiel ist beendet! Der §bDetektiv §eund die §aUnschuldigen §ehaben den §cMörder §egefunden.");
							all.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 0.5F, 0.5F);
							all.hidePlayer(t);
							try {
								if (all != GameInfo.murder) {
									Main.coins.addCoins(all.getUniqueId().toString(), 100);
									Main.stats.addWinMM(all.getUniqueId().toString(), 1);
								}
							} catch (Exception e4) {
							}
							ScoreboardManager.removeScoreboard(all);
						}
						GameUtils.stop(p);
					}
				} else {

					if (t == GameInfo.detectiv || p == GameInfo.detectiv) {
						Bukkit.broadcastMessage(Data.prefix + "Der Bogen des §bDetektivs §eliegt jetzt auf dem Boden.");
						if (p == GameInfo.detectiv || p == GameInfo.d2) {
							loc = p.getLocation().getBlock().getLocation();
						} else if (t == GameInfo.detectiv || t == GameInfo.d2) {
							loc = t.getLocation().getBlock().getLocation();
						}
						GameInfo.dbowloc = loc;
						GameInfo.setDetectivstatus("§cTot");
						GameUtils.as.setLoc(GameInfo.dbowloc);
						GameUtils.as.spawn();
						if (GameInfo.tatutata) {
							if (t == GameInfo.detectiv) {
								GameInfo.detectiv = null;
							} else if (t == GameInfo.d2) {
								GameInfo.d2 = null;
							}
						} else {
							GameInfo.detectiv = null;
						}
					}

					NPC npc = new NPC("§7", t.getLocation(), t);
					npc.spawn();
					npc.sleep(true);
					
					NPC npc2 = new NPC("§7", p.getLocation(), p);
					npc2.spawn();
					npc2.sleep(true);

					t.teleport(GameInfo.getMitte());
					t.setGameMode(GameMode.ADVENTURE);
					t.setAllowFlight(true);
					p.teleport(GameInfo.getMitte());
					p.setGameMode(GameMode.ADVENTURE);
					p.setAllowFlight(true);
					t.removePotionEffect(PotionEffectType.SPEED);
					p.removePotionEffect(PotionEffectType.SPEED);
					GameUtils.setInv(t);
					GameUtils.setInv(p);
					for (Player all : Bukkit.getOnlinePlayers()) {
						if (t == GameInfo.detectiv || t == GameInfo.d2) {
							all.playSound(t.getLocation(), Sound.WITHER_SPAWN, 1F, 1F);
						} else if (p == GameInfo.detectiv || p == GameInfo.d2) {
							all.playSound(p.getLocation(), Sound.WITHER_SPAWN, 1F, 1F);
						}
						all.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 0.5F, 0.5F);
						all.playSound(t.getLocation(), Sound.SUCCESSFUL_HIT, 0.5F, 0.5F);
						all.hidePlayer(t);
						all.hidePlayer(p);
						ScoreboardManager.update(all);
					}
					if (GameInfo.getInnoa() <= 0) {
						try {
							Main.stats.addWinMM(GameInfo.murder.getUniqueId().toString(), 1);
							Main.coins.addCoins(GameInfo.murder.getUniqueId().toString(), 100);
							Main.stats.addWinMM(GameInfo.m2.getUniqueId().toString(), 1);
							Main.coins.addCoins(GameInfo.m2.getUniqueId().toString(), 100);
						} catch (Exception e5) {
						}
						for (Player all : Bukkit.getOnlinePlayers()) {
							ScoreboardManager.removeScoreboard(all);
							all.sendMessage(
									Data.prefix + "§eDas Spiel ist beendet! Der §cMörder §ehat alle Spieler ermordet.");
						}
						GameUtils.stop(GameInfo.murder);
					}
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
