package net.den3.hackathonWeb.View;

import io.javalin.Javalin;
import net.den3.hackathonWeb.Entity.Post;
import net.den3.hackathonWeb.Main;

import java.util.*;
import java.util.stream.Collectors;

public class PlaceUser {
    class PlaceData{
        PlaceData(String nick,String uuid){
            this.nick = nick;
            this.uuid = uuid;
        }
        String nick;
        String uuid;
        String getNick(){
            return nick;
        }
        String getUUID(){
            return uuid;
        }
    }
    public PlaceUser(Javalin javalin){
        javalin.get("/place/:place",ctx->{
            String place = ctx.pathParam("place");
            Map<String,Object> data = new HashMap<>();
            List<PlaceData> userdata = new ArrayList<>();
            Main.logStore.getPosts().stream().filter(l->l.getFacility().equalsIgnoreCase(place)).forEach(p->{
                Main.userStore.getUser(p.getUserID()).ifPresent(u->{
                    userdata.add(new PlaceData(u.getNick(), u.getUUID()));
                });
            });
            data.put("users",userdata);
            data.put("searchWord",place);
            ctx.render("/WEB-INF/templates/place.html",data);
        });
    }
}
