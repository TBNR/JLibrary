package me.joey.library;

import me.joey.library.player.TPlayerManager;

/**
 * Created with IntelliJ IDEA.
 * User: Joey
 * Date: 9/10/13
 * Time: 10:33 PM
 * To change this template use File | Settings | File Templates.
 */
public interface TDatabaseMaster {
    public TPlayerManager.AuthenticationDetails getAuthDetails();
}
