package com.yodist.crudspringboot3.service;

import com.yodist.crudspringboot3.model.Post;
import com.yodist.crudspringboot3.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;

    @Override
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Override
    public Post createPost(Post post) {
        post.setCreatedDate(new Date());
        return postRepository.save(post);
    }

    @Override
    public Post updatePost(Post post) {
        Post existingPost = postRepository.findById(post.getId()).get();
        existingPost.setText(post.getText());
        existingPost.setModifiedDate(new Date());
        return postRepository.save(existingPost);
    }

    @Override
    public void deletePostById(Long id) {
        postRepository.deleteById(id);
    }
}
