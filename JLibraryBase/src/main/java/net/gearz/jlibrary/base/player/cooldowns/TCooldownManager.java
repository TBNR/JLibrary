package net.gearz.jlibrary.base.player.cooldowns;

import com.mongodb.*;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Joey
 * Date: 9/18/13
 * Time: 11:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class TCooldownManager {

    public static DB database = null;
    private static HashMap<String, TCooldown> cooldowns = new HashMap<>();

    public static boolean canContinue(String key, TCooldown cooldown) {

        if (database == null) {
            return true;
        }

        DBCollection collection = TCooldownManager.getCollection();
        BasicDBObject key2 = new BasicDBObject("key", key);
        DBCursor key1 = collection.find(key2);
        DBObject object = null;

        while (key1.hasNext()) {
            object = key1.next();
        }
        if (object == null) {
            collection.save(key2.append("cooldown-time_stored", cooldown.getTime_stored()).append("cooldown-length", cooldown.getLength()));
            return true;
        }

        TCooldown cooldown1 = new TCooldown((Long) object.get("cooldown-time_stored"), (Long) object.get("cooldown-length"));
        if (cooldown1.canContinue()) {
            collection.remove(object);
            return canContinue(key, new TCooldown(cooldown.getLength()));
        }

        return false;
    }

    public static boolean canContinueLocal(String key, TCooldown cooldown) {
        if (cooldowns.containsKey(key)) {
            if (cooldowns.get(key).canContinue()) {
                cooldowns.remove(key);
                return canContinueLocal(key, new TCooldown(cooldown.getLength()));
            }
            return false;
        }

        cooldowns.put(key, cooldown);
        return true;

    }

    private static DBCollection getCollection() {
        return database.getCollection("cooldowns");
    }
}
