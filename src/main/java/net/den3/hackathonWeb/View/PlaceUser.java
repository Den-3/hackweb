package net.den3.hackathonWeb.View;

import io.javalin.Javalin;
import net.den3.hackathonWeb.Entity.Post;
import net.den3.hackathonWeb.Main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PlaceUser {
    public PlaceUser(Javalin javalin){
        javalin.get("/place/:place",ctx->{
            String place = ctx.pathParam("place");
            Map<String,Object> data = new HashMap<>();
            List<Post> posts = Main.logStore.getPosts().stream().filter(l->l.getFacility().equalsIgnoreCase(place)).collect(Collectors.toList());
            data.put("users",posts);
            data.put("searchWord",place);
            ctx.render("/WEB-INF/templates/place.html",data);
        });
    }
}
