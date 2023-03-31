package com.rk.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rk.blog.payloads.CommentDto;
import com.rk.blog.service.CommentService;

@RestController
@RequestMapping("/apis")
public class CommentController {
	@Autowired
	private CommentService commentService;

	/**
	 * @apiNote This API is used to Create Comment on Post
	 * @author Rajkumar
	 * @since 1.0
	 * @param commentDto
	 * @param postId
	 * @param userId
	 * @return
	 */
	@PostMapping("/post/{postId}/user/{userId}/comment/")
	public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto, @PathVariable Integer postId,
			@PathVariable Integer userId) {

		CommentDto dto = commentService.createComment(commentDto, postId, userId);

		return ResponseEntity.ok(dto);

	}

	/**
	 * @apiNote This API is used to Delete Comment on Post
	 * @author Rajkumar
	 * @since 1.0
	 * @param id
	 * @return
	 */
	@DeleteMapping("/comment/{id}")
	public ResponseEntity<String> deleteComment(@PathVariable Integer id) {
		commentService.deleteComment(id);

		return new ResponseEntity<String>("Deleted SuccessFully", HttpStatus.OK);

	}

}
