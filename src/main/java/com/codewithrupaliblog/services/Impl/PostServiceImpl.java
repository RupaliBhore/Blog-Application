package com.codewithrupaliblog.services.Impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import com.codewithrupaliblog.entities.Category;
import com.codewithrupaliblog.entities.Post;
import com.codewithrupaliblog.entities.User;
import com.codewithrupaliblog.expections.ResourceNotFoundException;
import com.codewithrupaliblog.payload.PostDto;
import com.codewithrupaliblog.payload.PostResponse;
import com.codewithrupaliblog.repositories.CategoryRepo;
import com.codewithrupaliblog.repositories.PostRepo;
import com.codewithrupaliblog.repositories.UserRepo;
import com.codewithrupaliblog.services.PostService;

@Service
public class PostServiceImpl implements PostService {
	
	
	    @Autowired
	    private PostRepo postRepo;
	
	    @Autowired
	    private ModelMapper modelMapper;

	    @Autowired
	    private UserRepo userRepo;

	    @Autowired
	    private CategoryRepo categoryRepo;
	    
	    

	
	                                 //CREATE POST
	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId)
	{
		//user ko featch karo user nahi milata to execption throw hogi
		 User user = this.userRepo.findById(userId)
	                .orElseThrow(() -> new ResourceNotFoundException("User ", "User id", userId));
		 
		 
            //category ko featch karo category nahi mitali to execption throgh hogai
	        Category category = this.categoryRepo.findById(categoryId)
	                .orElseThrow(() -> new ResourceNotFoundException("Category", "category id ", categoryId));
	        
	        
               
	        //post nikalo postDto me convert karo
	        Post post = this.modelMapper.map(postDto, Post.class);
	        
	        
	        //post ki proprtis set karo
	        // koi nahi ayagi to defoult aayegi update method se baad me update karenge image ko
	        post.setImageName("default.png");
	        post.setAddedDate(new Date());
	        
	        //kis user aur kis category ka ye post he 
	        post.setUser(user);
	        post.setCategory(category);

	        //post ko save karo
	        Post newPost = this.postRepo.save(post);

	          //upadted post return hoga
	        return this.modelMapper.map(newPost, PostDto.class);
	}
	
	
	
                              //UPDATE POST
	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		
		 Post post = this.postRepo.findById(postId)
	                .orElseThrow(() -> new ResourceNotFoundException("Post ", "post id", postId));
		 

	        //Category category = this.categoryRepo.findById(postDto.getCategory().getCategoryId()).get();

	        
	        post.setTitle(postDto.getTitle());
	        post.setContent(postDto.getContent());
	        post.setImageName(postDto.getImageName());
	       // post.setCategory(category);


	        Post updatedPost = this.postRepo.save(post);
	        
	        //dtos me use convert kareke modlempper se use send karenge
	        return this.modelMapper.map(updatedPost, PostDto.class);
	}
	
	
              //DELETE POST
	@Override
	public void deletePost(Integer postId) {
		
		  Post post = this.postRepo.findById(postId)
	                .orElseThrow(() -> new ResourceNotFoundException("Post ", "post id", postId));

	        this.postRepo.delete(post);

	}
	
	
                //GET ALL POST
	   //simple way
	//public List<PostDto> getAllPost(Integer pageNumber,Integer pageSize) {
	//stream api se hum ek ek post ko nikalenge
			//this is a simple way to get all paosts
			//List<Post> allPosts=this.postRepo.findAll();
			//List<PostDto> postDtos=allPosts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
					//.collect(Collectors.toList());
	
	    //with pagaination
	@Override
	 public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
	
		
		
		//tenernry operator agar hamara direction asc ke barabar ho jata he to ascending order me data ayega agar yesa nahi he to decending order
		//hoga
	    Sort sort = (sortDir.equalsIgnoreCase("asc")) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();


		//kis pagenumber ka data nikalana he aur kitana size he us page ka ye saari info pagable ke pass hoti he
		                                                   //yaha se dyanamic pass karenge
          Pageable p = PageRequest.of(pageNumber, pageSize,Sort.by(sortBy).descending() );
          

		//page pe kitane post he ye nikalenge
        Page<Post> pagePost = this.postRepo.findAll(p);
        
        
		//sare post mil jayege
        List<Post> allPosts = pagePost.getContent();
        

        List<PostDto> postDtos = allPosts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
               .collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();

        //pagepost ke pass page ki saari information hogi
        postResponse.setContent(postDtos);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalElements(pagePost.getTotalElements());

        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());

        return postResponse;
	}
	
	
                  //GET SINGLE POST BY ID
	@Override
	public PostDto getPostById(Integer postId) {
		
		 Post post = this.postRepo.findById(postId)
	                .orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));
		 
	        return this.modelMapper.map(post, PostDto.class);
	}

	
	
	
	
	//GET POST BY CATEGORY
	@Override
	public List<PostDto> getPostsByCategory(Integer categoryId) 
	{
		//category ki jo id pass kari he use nikala agar o nhi he to error aayegi
		Category cat = this.categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "category id", categoryId));
		
		//postRepo use kiya findbycategory se us catagory ke sare post nikale
        List<Post> posts = this.postRepo.findByCategory(cat);

        //map ka use karake ek ke post nikale aur sare post ko convert kiya postdto me aur use return kiya
        List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());

        return postDtos;
	}
	
	
	
	
	
	
	
           //GET POST BY USER
	@Override
	public List<PostDto> getPostsByUser(Integer userId) {
		 User user = this.userRepo.findById(userId)
	                .orElseThrow(() -> new ResourceNotFoundException("User ", "userId ", userId));
		 
	        List<Post> posts = this.postRepo.findByUser(user);

	        //humae reutn karana he list of postDto to usake liye Stream api ka use karenge
	        //map ka use karenge post ko convert karene ke liiye postDto class me
	        List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
	                .collect(Collectors.toList());

	        return postDtos;
	}
	
	
	
	
	
	
           //SEARCH POSTS
	@Override
	public List<PostDto> searchPosts(String keyword) {
		 List<Post> posts = this.postRepo.searchByTitle("%" + keyword + "%");
		 List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
	        return postDtos;
	}
	
	
	
	
	
	
	
	
	
	

}
