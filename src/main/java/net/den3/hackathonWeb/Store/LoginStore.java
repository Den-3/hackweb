package net.den3.hackathonWeb.Store;

import net.den3.hackathonWeb.Main;
import redis.clients.jedis.Jedis;


public class LoginStore implements ILoginStore{
    final Jedis jedis = Main.getJedis();


    /**
     * ログイン済みかどうか
     * @param key Cookie
     * @return true->ログイン済み
     */
    @Override
    public boolean isLoggedIn(String key) {
        try {
            return jedis.exists(key);
        }catch (ClassCastException e){
            return jedis.exists(key);
        }
    }

    /**
     * ログイン済みのユーザーIDを返す
     *
     * @return ユーザーID
     */
    @Override
    public String getUserUUID(String key) {
        return jedis.get(key);
    }

    /**
     * ログインする
     *
     * @param key      Cookie
     * @param userUUID ユーザーID
     */
    @Override
    public void loginUser(String key, String userUUID) {
        jedis.set(key,userUUID);
    }
}
