package net.den3.hackathonWeb.Entity;

import net.den3.hackathonWeb.Main;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

public class Post {

    private final static SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/HH:mm");

    Long date;
    String facility;
    String time;
    String userid;
    String floor;
    String uuid = UUID.randomUUID().toString();

    public String getFloor(){
        return this.floor;
    }

    public String getFacility() {
        return facility;
    }

    public String getTime() {
        return sdf.format(new Date(date));
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

    public Post(String userUUID,String facility,String floor){
        this.userid = userUUID;
        this.facility = facility;
        Date d = new Date();
        this.time = sdf.format(d);
        this.date = d.getTime();
        this.floor = floor;

    }

    public Post(String userUUID, String facility,String floor,String time,Long date){
        this.userid = userUUID;
        this.facility = facility;
        this.floor = floor;
        this.time = time;
        this.date = date;
    }
}
