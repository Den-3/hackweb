package net.den3.hackathonWeb;

import io.javalin.Javalin;
import net.den3.hackathonWeb.Entity.IUser;
import net.den3.hackathonWeb.Store.*;
import net.den3.hackathonWeb.View.*;
import redis.clients.jedis.Jedis;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.net.URI;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static Jedis getJedis(){
        try{
            jedis.ping("Hello!");
        }catch (Exception e){
            jedis = new Jedis(System.getenv("REDIS_URL"));
        }
        return jedis;
    }

    private static int getHerokuAssignedPort() {
        String herokuPort = System.getenv("PORT");
        if (herokuPort != null) {
            return Integer.parseInt(herokuPort);
        }
        return 7000;
    }

    private static Jedis jedis = new Jedis(System.getenv("REDIS_URL"));

    public final static ILoginStore loginStore = new LoginStore();
    public final static IUserStore userStore = new UserStore();
    public final static IPostStore logStore = new PostStore();
    public final static IUserStatusStore statusStore = new UserStatusStore();

    public static void main(String[] args) {
        Javalin app = Javalin.create().start(getHerokuAssignedPort());

        new LoginPage(app);
        new TopPage(app);
        new RegisterPage(app);
        new SelfProfile(app);
        new PlaceUser(app);
        new RegistryPlace(app);

        app.get("/debug",ctx->{
            Map<String, Object> json = new HashMap<>();
            json.put("users",userStore.getUsers());
            json.put("posts",logStore.getPosts());
            ctx.json(json);
        });

        app.after(ctx->{ctx.res.setCharacterEncoding("UTF-8");});
    }
}
