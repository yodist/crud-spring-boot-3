package com.yodist.crudspringboot3.controller;

import com.yodist.crudspringboot3.model.Post;
import com.yodist.crudspringboot3.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/post")
public class PostController {

    private PostService postService;

    @GetMapping
    public ResponseEntity<List<Post>> findAllPost() {
        List<Post> postList = postService.findAll();
        return new ResponseEntity<>(postList, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Post> saveNewPost(@RequestBody Post post) {
        Post postResult = postService.createPost(post);
        return new ResponseEntity<>(postResult, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<Post> updatePost(@PathVariable("id") Long postId, @RequestBody Post post) {
        post.setId(postId);
        Post updatedPost = postService.updatePost(post);
        return new ResponseEntity<>(updatedPost, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deletPostById(@PathVariable("id") Long postId) {
        postService.deletePostById(postId);
        Map<String, String> body = new HashMap<>();
        body.put("message", "Post has been successfully deleted");
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

}
