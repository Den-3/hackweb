package net.den3.hackathonWeb.View;

import io.javalin.Javalin;
import net.den3.hackathonWeb.Entity.IUser;
import net.den3.hackathonWeb.Main;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UserStatus {
    public UserStatus(Javalin javalin){
        javalin.get("/user/:uuid",ctx->{
            String uuid = ctx.pathParam("uuid");
            Optional<IUser> user = Main.userStore.getUser(uuid);

            String session = ctx.cookie("session");
            if(session != null && Main.loginStore.isLoggedIn(session) && user.isPresent()){
                Optional<IUser> u = Main.userStore.getUser(Main.loginStore.getUserUUID(session));
                if(u.isPresent() && u.get().getUUID().equalsIgnoreCase(user.get().getUUID())){
                    ctx.redirect("/profile");
                    return;
                }
            }

            if(!user.isPresent()){
                return;
            }
            Map<String,String> profile = new HashMap<>();
            profile.put("nick",user.get().getNick());
            profile.put("status",Main.statusStore.isBusy(user.get().getUUID()) ? "授業中" : "暇");
            ctx.render("/WEB-INF/templates/profile.html",profile);
        });
    }
}
