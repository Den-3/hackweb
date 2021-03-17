package net.den3.hackathonWeb.View;

import io.javalin.Javalin;

public class RegistryPlace {
    public RegistryPlace(Javalin javalin){
        javalin.get("/place-registry",ctx->{
            ctx.render("/WEB-INF/templates/xxx.html");
        });

        javalin.post("/place-registry",ctx->{

        });
    }
}
