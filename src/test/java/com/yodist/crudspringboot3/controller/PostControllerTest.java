package com.yodist.crudspringboot3.controller;

import com.yodist.crudspringboot3.model.Post;
import com.yodist.crudspringboot3.service.PostService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
@WebMvcTest(PostController.class)
public class PostControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PostService postService;

    @Test
    public void findAllPost_success() throws Exception {
        Date postDate = new Date();
        List<Post> posts = new ArrayList<>();
        Post post = new Post();
        post.setId(1L);
        post.setText("test");
        post.setCreatedDate(postDate);
        posts.add(post);
        Mockito.when(postService.findAll()).thenReturn(posts);

        mvc.perform(MockMvcRequestBuilders
                .get("/api/post")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id")
                        .value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].text")
                        .value("test"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].createdDate")
                        .isNotEmpty());
    }

    @Test
    public void createPost_success() throws Exception {
        String requestPost = "{\"text\":\"test create\"}";

        Date postDate = new Date();
        Post post = new Post();
        post.setId(1L);
        post.setText("test create");
        post.setCreatedDate(postDate);
        Mockito.when(postService.createPost(any())).thenReturn(post);

        mvc.perform(MockMvcRequestBuilders
                        .post("/api/post")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestPost))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id")
                        .value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.text")
                        .value("test create"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdDate")
                        .isNotEmpty());
    }

    @Test
    public void updatePost_success() throws Exception {
        String requestPost = "{\"text\":\"test update\"}";

        Date postDate = new Date();
        long postId = 1L;
        Post post = new Post();
        post.setId(postId);
        post.setText("test update");
        post.setCreatedDate(postDate);
        Mockito.when(postService.updatePost(any())).thenReturn(post);

        mvc.perform(MockMvcRequestBuilders
                        .put("/api/post/{id}",postId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestPost))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id")
                        .value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.text")
                        .value("test update"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdDate")
                        .isNotEmpty());
    }

    @Test
    public void deletePostById_success() throws Exception {
        long postId = 1L;
        Mockito.doNothing().when(postService).deletePostById(any());

        mvc.perform(MockMvcRequestBuilders
                        .delete("/api/post/{id}",postId)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
