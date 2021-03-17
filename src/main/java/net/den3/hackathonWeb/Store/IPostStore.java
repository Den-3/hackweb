package net.den3.hackathonWeb.Store;

import net.den3.hackathonWeb.Entity.Post;

import java.util.List;

public interface IPostStore {
    void addPost(Post post);

    List<Post> getPosts();
}
