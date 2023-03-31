package com.rk.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rk.blog.entities.Category;
import com.rk.blog.entities.Post;
import com.rk.blog.entities.User;

public interface PostRepo extends JpaRepository<Post, Integer> {

	List<Post> findAllByUser(User user);

	List<Post> findAllByCategory(Category cat);

	List<Post> findByTitleContaining(String title);

}
