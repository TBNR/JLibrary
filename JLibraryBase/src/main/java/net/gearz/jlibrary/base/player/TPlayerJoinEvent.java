package net.gearz.jlibrary.base.player;

import org.bukkit.ChatColor;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created with IntelliJ IDEA.
 * User: Joey
 * Date: 9/5/13
 * Time: 9:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class TPlayerJoinEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private String joinMessage;
    private TPlayer player;

    public TPlayerJoinEvent(TPlayer player) {
        this.player = player;
        this.joinMessage = ChatColor.YELLOW + player.getPlayer().getName() + " has joined the game.";
    }

    /*
     * In it's own section because yolo copy paste.
     */

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public TPlayer getPlayer() {
        return player;
    }

    public String getJoinMessage() {
        return joinMessage;
    }

    public void setJoinMessage(String joinMessage) {
        this.joinMessage = joinMessage;
    }
}
