package com.rk.blog.service;

import java.util.List;

import com.rk.blog.payloads.PostDto;
import com.rk.blog.payloads.ResponsePost;

public interface PostService {

	public PostDto createPost(PostDto pdto, Integer userId, Integer catID);

	public PostDto updatePost(PostDto pdto, Integer id);

	public void deletePost(Integer id);

	PostDto getPost(Integer id);

	ResponsePost getallPost(Integer pagenumber, Integer pagesize, String sort, String dir);

	List<PostDto> getAllPostByUser(Integer userId);

	List<PostDto> getAllPostByCat(Integer catId);

	List<PostDto> SearchPost(String title);

}
