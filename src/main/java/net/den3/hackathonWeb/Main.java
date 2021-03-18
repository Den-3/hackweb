package net.den3.hackathonWeb;

import io.javalin.Javalin;
import net.den3.hackathonWeb.Store.*;
import net.den3.hackathonWeb.View.*;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
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
        new UserStatus(app);
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
