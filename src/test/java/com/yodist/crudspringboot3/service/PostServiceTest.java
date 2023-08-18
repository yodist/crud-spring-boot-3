package com.yodist.crudspringboot3.service;

import com.yodist.crudspringboot3.model.Post;
import com.yodist.crudspringboot3.repository.PostRepository;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@RunWith(SpringRunner.class)
@WebMvcTest(PostService.class)
public class PostServiceTest {

    @Autowired
    PostService postService;

    @MockBean
    PostRepository postRepository;

    @BeforeEach
    public void setup() {
        Mockito.doNothing().when(postRepository).deleteById(anyLong());
    }

    @Test
    public void findAll_success() {
        Mockito.when(postRepository.findAll()).thenReturn(dummyPostList());
        List<Post> postList = postService.findAll();
        assertThat(postList, Matchers.notNullValue());
        assertThat(postList.get(0).getId(), Matchers.equalTo(1L));
    }

    @Test
    public void createPost_success() {
        Post postRequest = new Post();
        postRequest.setText("abc");
        Mockito.when(postRepository.save(postRequest))
                .thenReturn(dummyPost(1L, "abc"));
        Post savedPost = postService.createPost(postRequest);
        assertThat(savedPost, Matchers.notNullValue());
        assertThat(savedPost.getId(), Matchers.notNullValue());
        assertThat(savedPost.getText(), Matchers.equalTo("abc"));
        assertThat(savedPost.getCreatedDate(), Matchers.notNullValue());
        assertThat(savedPost.getModifiedDate(), Matchers.nullValue());
    }

    @Test
    public void updatePost_success() {
        Post postRequest = new Post();
        postRequest.setId(1L);
        postRequest.setText("cde");
        Mockito.when(postRepository.findById(any()))
                .thenReturn(Optional.of(dummyPost(1L, "abc")));
        Mockito.when(postRepository.save(any()))
                .thenReturn(dummyPostUpdated(1L, "cde"));
        Post updatedPost = postService.updatePost(postRequest);
        assertThat(updatedPost, Matchers.notNullValue());
        assertThat(updatedPost.getId(), Matchers.equalTo(1L));
        assertThat(updatedPost.getText(), Matchers.equalTo("cde"));
        assertThat(updatedPost.getCreatedDate(), Matchers.notNullValue());
        assertThat(updatedPost.getModifiedDate(), Matchers.notNullValue());
    }

    @Test
    public void deletePostById_success() {
        postService.deletePostById(1L);
        Mockito.verify(postRepository).deleteById(1L);
    }

    private List<Post> dummyPostList() {
        List<Post> postList = new ArrayList<>();
        postList.add(dummyPost(1L, "test"));
        postList.add(dummyPost(2L, "test2"));
        postList.add(dummyPost(3L, "test3"));
        return postList;
    }

    private Post dummyPost(Long id, String text) {
        Post post = new Post();
        post.setId(id);
        post.setText(text);
        post.setCreatedDate(new Date());
        return post;
    }

    private Post dummyPostUpdated(Long id, String text) {
        Post post = new Post();
        post.setId(id);
        post.setText(text);
        post.setCreatedDate(new Date());
        post.setModifiedDate(new Date());
        return post;
    }

}
