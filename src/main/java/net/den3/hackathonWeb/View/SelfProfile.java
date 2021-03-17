package net.den3.hackathonWeb.View;

import io.javalin.Javalin;
import net.den3.hackathonWeb.Entity.IUser;
import net.den3.hackathonWeb.Main;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class SelfProfile {
    public SelfProfile(Javalin javalin){
        javalin.get("/profile",ctx->{
            String session = ctx.cookie("session");
            if(session != null && Main.loginStore.isLoggedIn(session)){
                Optional<IUser> u = Main.userStore.getUser(Main.loginStore.getUserUUID(session));
                if(u.isPresent()){
                    Map<String,String> self = new HashMap<>();
                    System.out.println("Nickname debug: "+u.get().getNick());
                    self.put("userName",u.get().getNick());
                    ctx.render("/WEB-INF/templates/self_profile.html",self);
                    return;
                }
            }
            ctx.redirect("/login");
        });
    }
}
