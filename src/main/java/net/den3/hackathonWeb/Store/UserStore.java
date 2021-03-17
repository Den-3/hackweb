package net.den3.hackathonWeb.Store;

import com.zaxxer.hikari.HikariDataSource;
import net.den3.hackathonWeb.Entity.IUser;
import net.den3.hackathonWeb.Entity.UserEditor;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

class UserStore implements IUserStore{

    Map<String,IUser> memStore = new HashMap<>();

    private final String GET_ALL_SQL = "SELECT * FROM users";
    private final String GET_ID_SQL = "SELECT * FROM users WHERE uuid = ?";
    private final String DELETE_SQL = "DELETE FROM users WHERE uuid = ?;";
    private final String INSERT_SQL = "INSERT INTO users (uuid,mail,password,nick,icon) VALUES (?,?,?,?,?);";
    private final String UPDATE_SQL = "UPDATE users SET mail = ?,password = ?,nick = ?,icon = ? WHERE uuid = ?";


    private HikariDataSource hikari;

    private void setupDB(){
        try{
            HikariDataSource ds = new HikariDataSource();

            URI dbUri = new URI(System.getenv("DATABASE_URL"));

            String username = dbUri.getUserInfo().split(":")[0];
            String password = dbUri.getUserInfo().split(":")[1];
            String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + dbUri.getPath();

            ds.setJdbcUrl(dbUrl);
            ds.setUsername(username);
            ds.setPassword(password);
            hikari = ds;
        }catch (URISyntaxException e){
            e.printStackTrace();
        }
    }

    private void setupTable(){
        try{

            final String createTable = "create table users (" +
                    " uuid varchar(30) unique," +
                    " mail varchar(255)" +
                    " password varchar(255)" +
                    " nick varchar(255)" +
                    " icon varchar(255)" +
                    ");";

            PreparedStatement ps = hikari.getConnection().prepareStatement(createTable);
            ps.executeQuery();

        }catch (SQLException ignore){}
    }

    public void closeDB(){
        if(hikari != null){
            hikari.close();
        }
    }

    /**
     * ユーザーを取得する
     *
     * @param uuid 固有のID
     * @return ユーザー
     */
    @Override
    public Optional<IUser> getUser(String uuid) {
        try {
            PreparedStatement ps = hikari.getConnection().prepareStatement(this.GET_ID_SQL);
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();

            return Optional.of(
                    new UserEditor()
                    .setUUID(rs.getString(1))
                    .setMail(rs.getString(2))
                    .setPass(rs.getString(3))
                    .setNick(rs.getString(4))
                    .setIcon(rs.getString(5))
                    .build());

        }catch (SQLException e){
            return Optional.empty();
        }
    }

    /**
     * 全ユーザーを取得する
     *
     * @return 全ユーザー
     */
    @Override
    public List<IUser> getUsers() {
        List<IUser> list = new ArrayList<>();
        try {
            PreparedStatement ps = hikari.getConnection().prepareStatement(this.GET_ALL_SQL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new UserEditor()
                        .setUUID(rs.getString(1))
                        .setMail(rs.getString(2))
                        .setPass(rs.getString(3))
                        .setNick(rs.getString(4))
                        .setIcon(rs.getString(5))
                        .build());
            }
        }catch (SQLException ignore){}

        return list;
    }

    /**
     * ユーザーを削除する
     *
     * @param uuid 固有のID
     */
    @Override
    public void deleteUser(String uuid) {
        try{
            PreparedStatement ps = hikari.getConnection().prepareStatement(this.DELETE_SQL);
            ps.setString(1,uuid);
            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * ユーザーの情報を更新する
     *
     * @param user 更新後のユーザー
     */
    @Override
    public void updateUser(IUser user) {
        try{
            PreparedStatement ps = hikari.getConnection().prepareStatement(this.UPDATE_SQL);
            ps.setString(1,user.getMail());
            ps.setString(2,user.getPass());
            ps.setString(3,user.getNick());
            ps.setString(4,user.getIcon());
            ps.setString(5,user.getUUID());

            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * ユーザーを追加する
     *
     * @param user ユーザー
     */
    @Override
    public void addUser(IUser user) {
        try{
            PreparedStatement ps = hikari.getConnection().prepareStatement(this.INSERT_SQL);
            ps.setString(1,user.getUUID());
            ps.setString(2,user.getMail());
            ps.setString(3,user.getPass());
            ps.setString(4,user.getNick());
            ps.setString(5,user.getIcon());
            ps.executeQuery();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
