package net.den3.hackathonWeb.Store;

public interface ILoginStore {
    /**
     * ログイン済みかどうか
     * @param key Cookie
     * @return true->ログイン済み
     */
    boolean isLoggedIn(String key);

    /**
     * ログイン済みのユーザーIDを返す
     * @param key Cookie
     * @return ユーザーID
     */
    String getUserUUID(String key);

    /**
     * ログインする
     * @param key Cookie
     * @param userUUID ユーザーID
     */
    void loginUser(String key,String userUUID);
}
