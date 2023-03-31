package com.rk.blog.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rk.blog.entities.Comment;
import com.rk.blog.entities.Post;
import com.rk.blog.entities.User;
import com.rk.blog.exceptions.ResourceNotFoundException;
import com.rk.blog.payloads.CommentDto;
import com.rk.blog.repositories.CommentRepo;
import com.rk.blog.repositories.PostRepo;
import com.rk.blog.repositories.UserRepo;
import com.rk.blog.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepo commentRepo;

	@Autowired
	private ModelMapper mp;

	@Autowired
	private PostRepo postRepo;
	@Autowired
	private UserRepo userRepo;

	@Override
	public CommentDto createComment(CommentDto cdto, Integer postId, Integer userId) {

		Post post = postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "post_ID", postId));

		User user = userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "user_Id", userId));

		Comment comment = mp.map(cdto, Comment.class);
		comment.setPost(post);
		comment.setUser(user);

		Comment save = commentRepo.save(comment);

		return mp.map(save, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer id) {

		commentRepo.deleteById(id);

	}

}
