package com.yodist.crudspringboot3.service;

import com.yodist.crudspringboot3.model.Post;

import java.util.List;

public interface PostService {
    List<Post> findAll();
    Post createPost(Post post);
    Post updatePost(Post post);
    void deletePostById(Long id);
}
