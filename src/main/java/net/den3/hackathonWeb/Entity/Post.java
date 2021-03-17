package net.den3.hackathonWeb.Entity;

import net.den3.hackathonWeb.Main;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

public class Post {

    private final static SimpleDateFormat sdf = new SimpleDateFormat("d日 hh時 mm分");

    Long date;
    Facility facility;
    String time;
    String userid;
    String uuid = UUID.randomUUID().toString();

    public Facility getFacility() {
        return facility;
    }

    public String getTime() {
        return sdf.format(time);
    }

    public Long getDate(){
        return this.date;
    }

    public String getUserID() {
        return userid;
    }

    public Optional<IUser> getUser() {
        return Main.userStore.getUser(this.userid);
    }

    public String getUUID(){
        return this.uuid;
    }

    public Post(String userUUID, Facility facility){
        this.userid = userUUID;
        this.facility = facility;
        Date d = new Date();
        this.time = sdf.format(d);
        this.date = d.getTime();

    }

    public Post(String userUUID, Facility facility,String time,Long date){
        this.userid = userUUID;
        this.facility = facility;
        this.time = time;
        this.date = date;
    }
}
