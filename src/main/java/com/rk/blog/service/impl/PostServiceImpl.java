package com.rk.blog.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.rk.blog.entities.Category;
import com.rk.blog.entities.Post;
import com.rk.blog.entities.User;
import com.rk.blog.exceptions.ResourceNotFoundException;
import com.rk.blog.payloads.PostDto;
import com.rk.blog.payloads.ResponsePost;
import com.rk.blog.repositories.CategoryRepo;
import com.rk.blog.repositories.PostRepo;
import com.rk.blog.repositories.UserRepo;
import com.rk.blog.service.PostService;

@Service
public class PostServiceImpl implements PostService {
	@Autowired
	private PostRepo postRepo;

	@Autowired
	private ModelMapper mp;

	@Autowired
	private CategoryRepo cr;
	@Autowired
	private UserRepo ur;

	@Override
	public PostDto createPost(PostDto pdto, Integer userId, Integer catID) {

		User user = ur.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "User_Id", userId));

		Category category = cr.findById(catID)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Category_Id", catID));

		Post post = mp.map(pdto, Post.class);
		post.setAddedDate(new Date());
		post.setCategory(category);
		post.setImageName("default.png");
		post.setUser(user);

		Post po = postRepo.save(post);

		return mp.map(po, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto pdto, Integer id) {

		Post post = postRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "Id", id));

		post.setTitle(pdto.getTitle());
		post.setContent(pdto.getContent());
		post.setImageName(pdto.getImageName());

		Post save = postRepo.save(post);

		return mp.map(save, PostDto.class);
	}

	@Override
	public void deletePost(Integer id) {

		postRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "Id", id));

		postRepo.deleteById(id);

	}

	@Override
	public PostDto getPost(Integer id) {

		Post post = postRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "Id", id));

		return mp.map(post, PostDto.class);
	}

	@Override
	public ResponsePost getallPost(Integer pagenumber, Integer pagesize, String sort, String dir) {

		PageRequest p = null;
		if (dir.equals("desc")) {

			p = PageRequest.of(pagenumber, pagesize, Sort.by(sort).descending());
		} else {

			p = PageRequest.of(pagenumber, pagesize, Sort.by(sort).ascending());

		}

		Page<Post> page = postRepo.findAll(p);
		List<Post> list = page.getContent();

		List<PostDto> listdto = list.stream().map((post) -> mp.map(post, PostDto.class)).collect(Collectors.toList());

		ResponsePost rp = new ResponsePost();
		rp.setContent(listdto);
		rp.setPagenumber(pagenumber);
		rp.setTotalelements(page.getTotalElements());
		rp.setTotalpage(page.getTotalPages());
		rp.setLastpage(page.isLast());

		return rp;
	}

	@Override
	public List<PostDto> getAllPostByUser(Integer userId) {

		User user = ur.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "user_Id", userId));

		List<Post> list = postRepo.findAllByUser(user);

		List<PostDto> listdto = list.stream().map((post) -> mp.map(post, PostDto.class)).collect(Collectors.toList());

		return listdto;
	}

	@Override
	public List<PostDto> getAllPostByCat(Integer catId) {

		Category category = cr.findById(catId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Category_Id", catId));

		List<Post> list = postRepo.findAllByCategory(category);

		List<PostDto> postdto = list.stream().map((post) -> mp.map(post, PostDto.class)).collect(Collectors.toList());

		return postdto;
	}

	@Override
	public List<PostDto> SearchPost(String title) {

		List<Post> list = postRepo.findByTitleContaining(title);

		List<PostDto> postdto = list.stream().map((post) -> mp.map(post, PostDto.class)).collect(Collectors.toList());

		return postdto;
	}

}
