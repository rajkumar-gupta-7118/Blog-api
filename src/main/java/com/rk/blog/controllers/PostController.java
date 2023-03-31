package com.rk.blog.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rk.blog.payloads.PostDto;
import com.rk.blog.payloads.ResponsePost;
import com.rk.blog.service.FileService;
import com.rk.blog.service.PostService;

@RestController
@RequestMapping("/apis")
public class PostController {
	@Autowired
	private PostService postService;

	@Autowired
	private FileService fileService;

	@Value("${project.image}")
	private String location;

	/**
	 * @apiNote This API is used to Create Post 
	 * @author Rajkumar
	 * @since 1.0
	 * @param postdto
	 * @param userId
	 * @param catId
	 * @return
	 */
	@PostMapping(value = "/user/{userId}/category/{catId}")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postdto, @PathVariable Integer userId,
			@PathVariable Integer catId) {

		PostDto pdto = postService.createPost(postdto, userId, catId);

		return new ResponseEntity<PostDto>(pdto, HttpStatus.CREATED);

	}

	/**
	 * @apiNote This API is used to Update Post
	 * @author Rajkumar
	 * @since 1.0
	 * @param postdto
	 * @param id
	 * @return
	 */
	@PutMapping(value = "/post/update/{id}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postdto, @PathVariable Integer id) {

		PostDto pdto = postService.updatePost(postdto, id);

		return new ResponseEntity<PostDto>(pdto, HttpStatus.CREATED);

	}

	/**
	 * @apiNote This API is used to Delete Post by Id
	 * @author Rajkumar
	 * @since 1.0
	 * @param id
	 * @return
	 */
	@DeleteMapping("/posts/delete/{id}")
	public ResponseEntity<String> deletePost(@PathVariable Integer id) {

		postService.deletePost(id);

		return new ResponseEntity<String>("Post deleted Successfull", HttpStatus.OK);

	}

	/**
	 * @apiNote This API is used to get Post
	 * @author Rajkumar
	 * @since 1.0
	 * @param id
	 * @return
	 */
	@GetMapping("/post/{id}")
	public ResponseEntity<PostDto> getPost(@PathVariable Integer id) {

		PostDto post = postService.getPost(id);

		return ResponseEntity.ok(post);

	}

	/**
	 * @apiNote This API is used to get all Post
	 * @author Rajkumar
	 * @since 1.0
	 * @param pagenumber
	 * @param pagesize
	 * @param sort
	 * @param dir
	 * @return
	 */
	@GetMapping("/posts")
	public ResponseEntity<ResponsePost> getAllPost(
			@RequestParam(defaultValue = "0", required = false) Integer pagenumber,
			@RequestParam(defaultValue = "5", required = false) Integer pagesize,
			@RequestParam(defaultValue = "postId", required = false) String sort,
			@RequestParam(defaultValue = "asc", required = false) String dir) {

		ResponsePost getallPost = postService.getallPost(pagenumber, pagesize, sort, dir);

		return ResponseEntity.ok(getallPost);

	}

	/**
	 * @apiNote This API is used to get all Post by User
	 * @author Rajkumar
	 * @since 1.0
	 * @param userId
	 * @return
	 */
	@GetMapping("/user/{userId}/post")
	public ResponseEntity<List<PostDto>> getAllPostByUser(@PathVariable Integer userId) {

		List<PostDto> allPostByUser = postService.getAllPostByUser(userId);

		return new ResponseEntity<List<PostDto>>(allPostByUser, HttpStatus.OK);
	}

	/**
	 * @apiNote This API is used to get all Post By Category
	 * @author Rajkumar
	 * @since 1.0
	 * @param catId
	 * @return
	 */
	@GetMapping("/category/{catId}/post")
	public ResponseEntity<List<PostDto>> getAllPostByCategory(@PathVariable Integer catId) {

		List<PostDto> allPostByCat = postService.getAllPostByCat(catId);

		return new ResponseEntity<List<PostDto>>(allPostByCat, HttpStatus.OK);
	}

	/**
	 * @apiNote This API is used to Search Post
	 * @author Rajkumar
	 * @since 1.0
	 * @param title
	 * @return
	 */
	@GetMapping("/search/{title}")
	public ResponseEntity<List<PostDto>> searchPost(@PathVariable String title) {

		List<PostDto> post = postService.SearchPost(title);

		return new ResponseEntity<List<PostDto>>(post, HttpStatus.OK);

	}

	/**
	 * @apiNote This API is used to upload Image on Post
	 * @author Rajkumar
	 * @since 1.0
	 * @param file
	 * @param postId
	 * @return
	 * @throws IOException
	 */
	@PostMapping("/post/image/{postId}")
	public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image") MultipartFile file,
			@PathVariable Integer postId) throws IOException {

		PostDto post = postService.getPost(postId);

		String imagename = fileService.uploadImage(location, file);
		post.setImageName(imagename);
		PostDto updatePost = postService.updatePost(post, postId);

		return ResponseEntity.ok(updatePost);

	}

	/**
	 * @apiNote This API is used to Download Image from Post
	 * @author Rajkumar
	 * @since 1.0
	 * @param name
	 * @param response
	 * @throws IOException
	 */
	@GetMapping(value = "/post/image/download/{name}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadPostImage(@PathVariable String name, HttpServletResponse response) throws IOException {

		InputStream resource = fileService.getResource(location, name);

		response.setContentType(MediaType.IMAGE_JPEG_VALUE);

		StreamUtils.copy(resource, response.getOutputStream());

	}

}
