package net.den3.hackathonWeb.View;

import io.javalin.Javalin;
import net.den3.hackathonWeb.Entity.Post;
import net.den3.hackathonWeb.Main;

import java.util.*;
import java.util.stream.Collectors;

public class PlaceUser {
    class PlaceData{
        PlaceData(String nick,String uuid,String floor){
            this.nick = nick;
            this.uuid = uuid;
            this.floor = floor;
        }
        String nick;
        String uuid;
        String floor;
        public String getNick(){
            return nick;
        }
        public String getUUID(){
            return uuid;
        }
        public String getFloor(){
            return floor;
        }
    }
    public PlaceUser(Javalin javalin){
        javalin.get("/place/:place",ctx->{
            String place = ctx.pathParam("place");
            Map<String,Object> data = new HashMap<>();
            List<PlaceData> userdata = new ArrayList<>();
            Main.logStore.getPosts().stream().filter(l->l.getFacility().equalsIgnoreCase(place)).forEach(p->{
                Main.userStore.getUser(p.getUserID()).ifPresent(u->{
                    userdata.add(new PlaceData(u.getNick(), u.getUUID(),p.getFloor()));
                });
            });
            data.put("users",userdata);
            data.put("searchWord",place);
            ctx.render("/WEB-INF/templates/place.html",data);
        });
    }
}
