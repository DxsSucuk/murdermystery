package de.presti.mudermystery.scoreboard;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class PlayerScoreboard {
    private Player player;
    private HashMap<Integer, String> lines = new HashMap<Integer, String>();
    private PacketScoreboard packetScoreboard;
    private String title = ChatColor.DARK_GRAY + "➥ " + ChatColor.RED.toString() + ChatColor.BOLD + "mgt" + ChatColor.DARK_GRAY + " ┃ " + ChatColor.YELLOW + ChatColor.BOLD + "SKY";

    public PlayerScoreboard(Player player) {
        this.player = player;
        this.packetScoreboard = new PacketScoreboard(player);
    }

    public void sendTitle() {
        packetScoreboard.sendSidebar(title);
    }

    public void setup() {
        packetScoreboard.remove();
        sendTitle();
        for (int score : lines.keySet()) {
            String line = lines.get(score);
            packetScoreboard.setLine(score, line);
        }
    }

    public void remove() {
        packetScoreboard.remove();
    }

    public void setLine(int line, String text) {
        if (lines.containsKey(line)) {
            packetScoreboard.removeLine(line);
        }
        lines.put(line, text);
        packetScoreboard.setLine(line, text);
    }
}
