package net.den3.hackathonWeb.View;

import io.javalin.Javalin;
import net.den3.hackathonWeb.Entity.Facility;
import net.den3.hackathonWeb.Main;

import java.util.HashMap;
import java.util.Map;

public class TopPage {
    public TopPage(Javalin javalin){
        javalin.get("/",ctx->{
            Map<String, Object> logs = new HashMap<>();
            logs.put("logs",Main.logStore.getPosts());
            logs.put("facilities", Facility.facilitiesString);
            ctx.render("/WEB-INF/templates/index.html",logs);
        });
    }
}
