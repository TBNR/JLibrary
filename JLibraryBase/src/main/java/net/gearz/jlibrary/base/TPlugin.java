package net.gearz.jlibrary.base;

import com.mongodb.DB;
import lombok.Getter;
import net.gearz.jlibrary.base.command.TCommandDispatch;
import net.gearz.jlibrary.base.command.TCommandHandler;
import net.gearz.jlibrary.base.player.TPlayer;
import net.gearz.jlibrary.base.player.TPlayerJoinEvent;
import net.gearz.jlibrary.base.player.TPlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

/**
 * The TPlugin class is used to represent a plugin! This will handle all the basics for you!
 */
public abstract class TPlugin extends JavaPlugin {
    private static final String divider = ";";
    /**
     * Player Manager
     */
    private static TPlayerManager playerManager = null;
    @Getter
    private Random random = new Random();
    /**
     * The command dispatch for the plugin.
     */
    private TCommandDispatch commandDispatch;

    /**
     * Parses a string intended to represent a location. Used for databases, or config files. Really cool
     *
     * @param string The location string to parse
     * @return The location that the string represents
     */
    public static Location parseLocationString(String string) {
        String[] sep = string.split(TPlugin.divider);
        if (sep.length < 6) return null;
        Location rv = new Location(Bukkit.getWorld(sep[0]), Double.valueOf(sep[1]), Double.valueOf(sep[2]), Double.valueOf(sep[3]));
        rv.setPitch(Float.parseFloat(sep[4]));
        rv.setYaw(Float.parseFloat(sep[5]));
        return rv;
    }

    /**
     * Encode a location into a string for storage
     *
     * @param location The location you intend to encode.
     * @return The string that represents the location.
     */
    public static String encodeLocationString(Location location) {
        return location.getWorld().getName() + TPlugin.divider + location.getX() + TPlugin.divider + location.getY() + TPlugin.divider + location.getZ() + TPlugin.divider + location.getPitch() + TPlugin.divider + location.getYaw();
    }

    /**
     * This will register something for events simply
     *
     * @param listener The listener that you're registering
     */
    public void registerEvents(Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, this);
    }

    /**
     * This registers a handler to handle commands!
     *
     * @param handler The handler to register.
     */
    public void registerCommands(TCommandHandler handler) {
        this.getCommandDispatch().registerHandler(handler);
    }

    /**
     * The enable method, as specified by bukkit.
     */
    @Override
    public void onEnable() {
        try {
            this.saveDefaultConfig(); //save the config
            this.commandDispatch = new TCommandDispatch(this); //Create a new command dispatch
            if (TPlugin.playerManager == null) {
                if (this instanceof TDatabaseMaster) {
                    TPlugin.playerManager = new TPlayerManager(((TDatabaseMaster) this).getAuthDetails(), this);
                    this.registerEvents(this.getPlayerManager());
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        TPlayer tPlayer = this.getPlayerManager().addPlayer(player);
                        Bukkit.getPluginManager().callEvent(new TPlayerJoinEvent(tPlayer));
                    }
                }
            }
            this.enable(); //Enable the plugin using the abstract method (hand this off to the plugin itself)
        } catch (Exception ex) {
            ex.printStackTrace();
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }

    /**
     * When the plugin is disabled, make sure to load the config, because most of the time when I reload the plugin
     * I mean to access the newest config.
     */
    @Override
    public void onDisable() {
        this.reloadConfig();
        this.disable();
        this.commandDispatch = null;
    }

    /**
     * Plugins must implement this to be called on enable.
     */
    public abstract void enable();

    /**
     * Plugins must implement this to be called on disable.
     */
    public abstract void disable();

    /**
     * Get command dispatch
     *
     * @return The command dispatch for this plugin
     */
    public TCommandDispatch getCommandDispatch() {
        return commandDispatch;
    }

    /**
     * Get the player manager
     *
     * @return The player manager for this TPlugin
     */
    public TPlayerManager getPlayerManager() {
        return playerManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        return this.commandDispatch.onCommand(sender, command, s, strings);
    }

    /**
     * Get the BungeeCord API Handler
     * @return The global Bungee API handler
     */

    /**
     * Get a String format from the config.
     *
     * @param formatPath Supplied configuration path.
     * @param color      Include colors in the passed args?
     * @param data       The data arrays. Used to insert variables into the config string. Associates Key to Value.
     * @return The formatted String
     */
    public String getFormat(String formatPath, boolean color, String[]... data) {
        if (!this.getConfig().contains(formatPath)) return null;
        String string = ChatColor.translateAlternateColorCodes('&', this.getConfig().getString(formatPath));
        if (data != null) {
            for (String[] dataPart : data) {
                if (dataPart.length < 2) continue;
                string = string.replaceAll(dataPart[0], dataPart[1]);
            }
        }
        if (color) string = ChatColor.translateAlternateColorCodes('&', string);
        //else string = ChatColor.stripColor(string)
        return string;
    }

    /**
     * Get the format without using any data.
     *
     * @param formatPath The path to the format!
     * @return The formatted message.
     */
    public String getFormat(String formatPath) {
        return this.getFormat(formatPath, true, null);
    }

    public abstract String getStorablePrefix();

    public DB getMongoDB() {
        return this.getPlayerManager().getDatabase();
    }
}
