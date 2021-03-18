package net.den3.hackathonWeb.View;

import io.javalin.Javalin;
import net.den3.hackathonWeb.Entity.IUser;
import net.den3.hackathonWeb.Entity.Post;
import net.den3.hackathonWeb.Main;

import java.util.Optional;

public class RegistryPlace {
    public RegistryPlace(Javalin javalin){
        javalin.get("/place",ctx->{
            ctx.render("/WEB-INF/templates/place_registry.html");
        });

        javalin.post("/place-registry",ctx->{

            String facility = ctx.formParam("name");
            String floor = ctx.formParam("floor");

            if(facility == null ||  floor == null){
                ctx.render("/place");
                return;
            }

            String session = ctx.cookie("session");
            if(session != null && Main.loginStore.isLoggedIn(session)){
                Optional<IUser> u = Main.userStore.getUser(Main.loginStore.getUserUUID(session));
                if(!u.isPresent()){
                    ctx.render("/place");
                    return;
                }

                Post post = new Post(u.get().getUUID(),facility,floor);

                Main.logStore.addPost(post);

                ctx.redirect("/");

            }else{
                ctx.render("/place");
            }

        });
    }
}
