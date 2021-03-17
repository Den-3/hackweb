package net.den3.hackathonWeb.View;

import io.javalin.Javalin;
import net.den3.hackathonWeb.Entity.IUser;
import net.den3.hackathonWeb.Entity.UserEditor;
import net.den3.hackathonWeb.Main;

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

            if(mail == null || pass == null || confirm == null || nick == null){
                ctx.redirect("/register");
                return;
            }
            if(mail.isEmpty() || pass.isEmpty() || confirm.isEmpty() || nick.isEmpty()){
                ctx.redirect("/register");
                return;
            }
            if(!confirm.equalsIgnoreCase(pass)){
                ctx.redirect("/register");
                return;
            }

            IUser user = new UserEditor()
                             .setMail(mail)
                             .setSecurePass(pass)
                             .setNick(nick)
                             .setIcon("https://www.w3schools.com/howto/img_avatar.png")
                             .build();

            Main.userStore.addUser(user);

            ctx.redirect("/login");

        });
    }
}
