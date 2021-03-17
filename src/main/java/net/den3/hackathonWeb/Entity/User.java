package net.den3.hackathonWeb.Entity;

import java.util.UUID;

class User implements IUser{
    String uuid = UUID.randomUUID().toString();
    String mail;
    String pass;
    String nick;
    String icon;

    @Override
    public String getUUID(){
        return uuid;
    }

    @Override
    public String getMail() {
        return mail;
    }

    @Override
    public String getPass() {
        return pass;
    }

    @Override
    public String getNick() {
        return nick;
    }

    @Override
    public String getIcon() {
        return icon;
    }
}
