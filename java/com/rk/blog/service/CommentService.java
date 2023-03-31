package com.rk.blog.service;

import com.rk.blog.payloads.CommentDto;

public interface CommentService {

	CommentDto createComment(CommentDto cdto, Integer postId, Integer userId);

	void deleteComment(Integer id);

}
