package net.den3.hackathonWeb.Entity;

import net.den3.hackathonWeb.Util.HashGenerator;

public class UserEditor {
    private User user = new User();
    
    public UserEditor(User user){
        this.user = user;
    }

    public UserEditor(){};

    public UserEditor setUUID(String uuid){
        this.user.uuid = uuid;
        return this;
    }

    public UserEditor setMail(String mail) {
        this.user.mail = mail;
        return this;
    }

    public UserEditor setPass(String pass) {
        this.user.pass = pass;
        return this;
    }

    public UserEditor setSecurePass(String pass) {
        this.user.pass = HashGenerator.getSafetyPassword(pass,user.getUUID());
        return this;
    }

    public UserEditor setNick(String nick) {
        this.user.nick = nick;
        return this;
    }

    public UserEditor setIcon(String icon) {
        this.user.icon = icon;
        return this;
    }

    public IUser build(){
        return this.user;
    }
}
