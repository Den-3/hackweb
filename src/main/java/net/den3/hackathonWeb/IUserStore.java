package net.den3.hackathonWeb.Store;

import net.den3.hackathonWeb.Entity.IUser;

import java.util.List;
import java.util.Optional;

public interface IUserStore {
    /**
     * ユーザーを取得する
     * @param uuid 固有のID
     * @return ユーザー
     */
    Optional<IUser> getUser(String uuid);

    /**
     * ユーザーを取得する
     * @param mail メール
     * @return ユーザー
     */
    Optional<IUser> getUserByMail(String mail);

    /**
     * 全ユーザーを取得する
     * @return 全ユーザー
     */
    List<IUser> getUsers();

    /**
     * ユーザーを削除する
     * @param uuid 固有のID
     */
    void deleteUser(String uuid);

    /**
     * ユーザーの情報を更新する
     * @param user 更新後のユーザー
     */
    void updateUser(IUser user);

    /**
     * ユーザーを追加する
     * @param user ユーザー
     */
    void addUser(IUser user);
}
