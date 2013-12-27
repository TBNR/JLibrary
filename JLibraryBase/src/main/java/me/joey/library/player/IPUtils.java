package me.joey.library.player;

import me.joey.library.TPlugin;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;
import java.net.InetAddress;

/**
 * Created by George on 24/12/13.
 */
public class IPUtils {

    /**
     * Returns player ping via the eventHandler passed in
     * Standard timeout of 9999 milliseconds ~ 9.999 seconds
     * @param ip ~ Player to ping
     */
    public static void getPing(TPlugin tplugin, final InetAddress ip, final PingCallbackEventHandler ev) {
        Bukkit.getScheduler().runTaskAsynchronously(tplugin, new BukkitRunnable() {

            @Override
            public void run() {
                float oldTime = System.currentTimeMillis();

                //give up trying to reach it at 9.999 seconds
                try {
                    ip.isReachable(9999);
                } catch (IOException e) {
                    /*If Not reachable fail silently and return 9999 as it's the default ping*/
                    ev.onPingCallback(new PingCallbackEvent(9999));
                    return;
                }

                float newTime = System.currentTimeMillis();

                //time it took in miliseconds
                float totalTime = newTime - oldTime;

                ev.onPingCallback(new PingCallbackEvent(totalTime));
            }

        });
    }

    public interface PingCallbackEventHandler {
        public void onPingCallback(PingCallbackEvent e);
    }

    public static class PingCallbackEvent {

        float ping = 9999;

        public PingCallbackEvent(float ping) {
            this.ping = ping;
        }

        public float getPing() {
            return ping;
        }

    }
}
