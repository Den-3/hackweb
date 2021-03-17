package net.den3.hackathonWeb.View;

import io.javalin.Javalin;
import net.den3.hackathonWeb.Entity.IUser;
import net.den3.hackathonWeb.Main;
import net.den3.hackathonWeb.Util.HashGenerator;

import java.util.Optional;
import java.util.UUID;

public class LoginPage {
    public LoginPage(Javalin javalin){

        javalin.get("/login",ctx->{
            String session = ctx.cookie("session");
            if(session != null && Main.loginStore.isLoggedIn(session)){
                ctx.redirect("/");
            }else{
                ctx.render("/WEB-INF/templates/login.html");
            }
        });

        javalin.post("/login",ctx->{
            String mail = ctx.formParam("mail");
            String pass = ctx.formParam("pass");

            if(mail == null || pass == null){
                ctx.redirect("/login");
                return;
            }

            Optional<IUser> user = Main.userStore.getUserByMail(mail);

            if(!user.isPresent()){
                ctx.redirect("/login");
                return;
            }

            String hash = HashGenerator.getSafetyPassword(pass,user.get().getUUID());

            System.out.println(user.get().getUUID());
            System.out.println(user.get().getPass());
            System.out.println("hash: "+hash);

            if(!hash.equalsIgnoreCase(pass)){
                ctx.redirect("/login");
                return;
            }

            System.out.println("Login PHASE 3");

            String session = UUID.randomUUID().toString();

            Main.loginStore.loginUser(session,user.get().getUUID());

            ctx.cookie("session",session);

            ctx.redirect("/");
        });
    }
}
