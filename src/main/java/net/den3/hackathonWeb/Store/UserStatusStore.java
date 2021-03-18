package net.den3.hackathonWeb.Store;

import net.den3.hackathonWeb.Main;
import redis.clients.jedis.Jedis;

public class UserStatusStore implements IUserStatusStore{

    final Jedis jedis = Main.getJedis();

    @Override
    public boolean isBusy(String userUUID) {
        if(!jedis.exists("busy."+userUUID)){
            return false;
        }
        return "true".equalsIgnoreCase(jedis.get("busy."+userUUID));
    }

    @Override
    public void setBusy(String userUUID) {
        jedis.set("busy."+userUUID,String.valueOf(!isBusy(userUUID)));
    }
}
