package de.presti.mudermystery.scoreboard;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import net.minecraft.server.v1_8_R3.IScoreboardCriteria;
import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardScore;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class PacketScoreboard {

    boolean displayed;
    private Player player;
    private HashMap<Integer, String> lines;

    public PacketScoreboard(Player player) {
        // fills values
        this.player = player;
        this.lines = new HashMap<Integer, String>();
        this.displayed = false;

    }

    public void sendSidebar(String displayedName) {
        // checks wether it already is displayed or not to prevent exceptions
        if (displayed)
            return;
        // visibility check
        // scoreboard packet
        PacketContainer scoreboard = new PacketContainer(PacketType.Play.Server.SCOREBOARD_OBJECTIVE);
        scoreboard.getStrings()
                .write(0, "sideboard")
                .write(1, displayedName);
        scoreboard.getIntegers()
                .write(0, 0);
        scoreboard.getSpecificModifier(IScoreboardCriteria.EnumScoreboardHealthDisplay.class)
                .write(0, IScoreboardCriteria.EnumScoreboardHealthDisplay.INTEGER);
        // display packet
        PacketContainer playOut = new PacketContainer(PacketType.Play.Server.SCOREBOARD_DISPLAY_OBJECTIVE);
        playOut.getIntegers()
                .write(0, 1);
        playOut.getStrings()
                .write(0, "sideboard");
        // packet sending
        try {
            ProtocolLibrary.getProtocolManager().sendServerPacket(this.player, scoreboard);
            ProtocolLibrary.getProtocolManager().sendServerPacket(this.player, playOut);
            displayed = true;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            displayed = false;
        }
    }

    public void remove() {
        // checks wether it already is displayed or not to prevent exceptions
        // packet to remove the scoreboard
        PacketContainer unsend = new PacketContainer(PacketType.Play.Server.SCOREBOARD_OBJECTIVE);
        unsend.getStrings()
                .write(0, "sideboard")
                .write(1, "");
        unsend.getIntegers()
                .write(0, 1);
        // packet sending
        try {
            ProtocolLibrary.getProtocolManager().sendServerPacket(this.player, unsend);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void setLine(Integer line, String text) {
        // checks wether it already is displayed or not to prevent exceptions
        if (!displayed)
            return;
        // checks if this line already is set

        // shortens line-text to 1.8 compatible length
        if (text.length() > 40)
            text = text.substring(0, 40);
        // score packet to be sent
        PacketContainer score = new PacketContainer(PacketType.Play.Server.SCOREBOARD_SCORE);
        score.getStrings()
                .write(0, text)
                .write(1, "sideboard");
        score.getIntegers()
                .write(0, line);
        score.getSpecificModifier(PacketPlayOutScoreboardScore.EnumScoreboardAction.class)
                .write(0, PacketPlayOutScoreboardScore.EnumScoreboardAction.CHANGE);
        // packet sending
        try {
            ProtocolLibrary.getProtocolManager().sendServerPacket(this.player, score);
            lines.put(line, text);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void removeLine(Integer line) {
        // checks wether it already is displayed or not to prevent exceptions
        if (!displayed)
            return;
        // checks if this line already is set
        if (!lines.containsKey(line))
            return;
        // gets displayed text at this line
        String text = lines.get(line);
        // remove player score packet
        PacketContainer score = new PacketContainer(PacketType.Play.Server.SCOREBOARD_SCORE);
        score.getStrings()
                .write(0, text)
                .write(1, "sideboard");
        score.getIntegers()
                .write(0, line);
        score.getSpecificModifier(PacketPlayOutScoreboardScore.EnumScoreboardAction.class)
                .write(0, PacketPlayOutScoreboardScore.EnumScoreboardAction.REMOVE);
        // sending packet
        try {
            ProtocolLibrary.getProtocolManager().sendServerPacket(this.player, score);
            lines.remove(line);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void setName(String displayedName) {
        // checks wether it already is displayed or not to prevent exceptions
        if (!displayed)
            return;
        // scoreboard packet
        PacketContainer scoreboard = new PacketContainer(PacketType.Play.Server.SCOREBOARD_OBJECTIVE);
        scoreboard.getStrings()
                .write(0, "sideboard")
                .write(1, displayedName);
        scoreboard.getIntegers()
                .write(0, 2);
        // packet sending
        try {
            ProtocolLibrary.getProtocolManager().sendServerPacket(this.player, scoreboard);
            displayed = true;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            displayed = false;
        }
    }

    public String getLine(Integer line) {
        return this.lines.get(line);
    }
}
