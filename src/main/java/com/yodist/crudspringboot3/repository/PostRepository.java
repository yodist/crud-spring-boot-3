package com.yodist.crudspringboot3.repository;

import com.yodist.crudspringboot3.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
