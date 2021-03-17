package net.den3.hackathonWeb.View;

import io.javalin.Javalin;

public class RegisterPage {
    public RegisterPage(Javalin javalin){
        javalin.get("/register",ctx->{
            ctx.render("/WEB-INF/templates/register.html");
        });

        javalin.post("/register",ctx->{
            String mail =  ctx.formParam("mail");
            String pass =ctx.formParam("pass");
            String confirm =ctx.formParam("confirm");
            String nick =ctx.formParam("nick");
            ctx.result(mail+" "+pass+" "+confirm+" "+nick);
        });
    }
}
