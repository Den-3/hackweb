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

public class UserStore implements IUserStore{

    final Map<String,IUser> memStore = new HashMap<>();
    final Queue<IUser> addQueue = new LinkedList<>();

    private final String GET_ALL_SQL = "SELECT * FROM users";
    private final String DELETE_SQL = "DELETE FROM users WHERE uuid = ?;";
    private final String INSERT_SQL = "INSERT INTO users (uuid,mail,password,nick,icon) VALUES (?,?,?,?,?);";
    private final String UPDATE_SQL = "UPDATE users SET mail = ?,password = ?,nick = ?,icon = ? WHERE uuid = ?";


    private HikariDataSource hikari;

    public UserStore(){
        setupDB();
    }

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

            setupTable();

            //キャッシュする
            _getUsers().forEach(u-> this.memStore.put(u.getUUID(),u));

            //定期的にキューに溜まったユーザーを登録していく
            new Thread(()->{
                TimerTask task = new TimerTask() {
                    public void run() {
                        System.out.println("User add task...");
                        Optional.ofNullable(addQueue.poll()).ifPresent(u->{
                            System.out.println("User added!");
                            _addUser(u);
                        });
                    }
                };

                Timer timer = new Timer();
                timer.schedule(task, 0,1000*10);
            }).start();

        }catch (URISyntaxException e){
            e.printStackTrace();
        }
    }

    private void setupTable(){
        try{

            final String createTable = "create table users (" +
                    " uuid varchar(30) unique," +
                    " mail varchar(255)," +
                    " password varchar(255)," +
                    " nick varchar(255)," +
                    " icon varchar(255)" +
                    ");";

            PreparedStatement ps = hikari.getConnection().prepareStatement(createTable);
            ps.executeQuery();

        }catch (SQLException e){
            e.printStackTrace();
        }
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
        return Optional.ofNullable(this.memStore.get(uuid));
    }

    /**
     * ユーザーを取得する
     * @param mail メール
     * @return ユーザー
     */
    @Override
    public Optional<IUser> getUserByMail(String mail) {
        return this.memStore.values().stream().filter(u->u.getMail().equalsIgnoreCase(mail)).findAny();
    }

    /**
     * 全ユーザーを取得する
     *
     * @return 全ユーザー
     */
    @Override
    public List<IUser> getUsers() {
        return new ArrayList<>(this.memStore.values());
    }

    private List<IUser> _getUsers(){
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

            this.memStore.remove(uuid);
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

            this.memStore.put(user.getUUID(),user);
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
        this.memStore.put(user.getUUID(),user);
        this.addQueue.offer(user);
    }

    private boolean _addUser(IUser user){
        try{
            PreparedStatement ps = hikari.getConnection().prepareStatement(this.INSERT_SQL);
            ps.setString(1,user.getUUID());
            ps.setString(2,user.getMail());
            ps.setString(3,user.getPass());
            ps.setString(4,user.getNick());
            ps.setString(5,user.getIcon());
            ps.executeQuery();
            return true;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
}
