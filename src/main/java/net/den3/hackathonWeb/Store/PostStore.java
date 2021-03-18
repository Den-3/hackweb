package net.den3.hackathonWeb.Store;

import net.den3.hackathonWeb.Entity.Post;
import net.den3.hackathonWeb.Main;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

public class PostStore implements IPostStore{
    final Jedis jedis = Main.getJedis();
    final List<String> removeArray = new ArrayList<>();
    List<Post> posts;

    public PostStore(){
        posts = _getPosts();
    }

    private synchronized void removePose(Post post){
        String postID = jedis.get("user-id."+post.getUserID());
        if(postID == null){
            return;
        }

        Long size = jedis.llen("posts");

        if(size == null || size == 0){
            return;
        }

        for (long i = 0; i < size; i++) {
            try{
                String key = jedis.lindex("posts",i);
                if(!key.equalsIgnoreCase(postID)){
                    removeArray.add(key);
                }
            }catch (Exception ignore){}
        }

        jedis.rpush("posts",removeArray.toArray(new String[removeArray.size()]));

        jedis.del("facility."+postID,"time."+postID,"user."+postID,"date."+postID,"floor."+postID,"user-id."+post.getUserID());

        removeArray.clear();

        for (int i = 0; i < posts.size(); i++) {
            if(posts.get(i).getUserID().equalsIgnoreCase(post.getUserID())){
                posts.remove(i);
            }
        }

    }

    @Override
    public void addPost(Post post) {

        removePose(post);

        jedis.rpush("posts", post.getUUID());
        String key = post.getUUID();
        jedis.expire(key,60*60);

        jedis.rpush(key,post.getFacility());
        jedis.expire(key,60*60);

        key = "time."+post.getUUID();
        jedis.set(key,post.getTime());
        jedis.expire(key,60*60);

        key = "user."+post.getUUID();
        jedis.set(key,post.getUserID());
        jedis.expire(key,60*60);

        key = "user-id."+post.getUserID();
        jedis.set(key,post.getUUID());
        jedis.expire(key,60*60);

        key = "date."+ post.getUUID();
        jedis.set(key,String.valueOf(post.getDate()));
        jedis.expire(key,60*60);

        key = "facility."+post.getUUID();
        jedis.set(key,String.valueOf(post.getFacility()));
        jedis.expire(key,60*60);

        key = "floor."+post.getUUID();
        jedis.set(key,String.valueOf(post.getFloor()));
        jedis.expire(key,60*60);

        this.posts.add(post);
    }

    private List<Post> _getPosts(){
        List<Post> posts = new ArrayList<>();
        Long size = jedis.llen("posts");

        if(size == null || size == 0){
            return posts;
        }

        for (long i = 0; i < size; i++) {
            try{
                String key = jedis.lindex("posts",i);
                String user = jedis.get("user."+key);
                String time = jedis.get("time."+key);
                String facility = jedis.get("facility."+key);
                String floor = jedis.get("floor."+key);
                Long date = Long.parseLong(jedis.get("date."+key));
                if(user == null || time == null || facility == null || floor == null){
                    continue;
                }
                posts.add(new Post(user,facility,floor,time,date));
            }catch (Exception ignore){}
        }
        return posts;
    }

    @Override
    public List<Post> getPosts() {
        return this.posts;
    }
}
