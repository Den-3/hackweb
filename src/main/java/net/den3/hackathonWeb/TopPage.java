package net.den3.hackathonWeb.View;

import io.javalin.Javalin;
import net.den3.hackathonWeb.Entity.Post;
import net.den3.hackathonWeb.Main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TopPage {
    public TopPage(Javalin javalin){
        javalin.get("/",ctx->{
            Map<String, List<Post>> logs = new HashMap<>();
            logs.put("logs",Main.logStore.getPosts());
            ctx.render("/WEB-INF/templates/index.html",logs);
        });
    }
}
