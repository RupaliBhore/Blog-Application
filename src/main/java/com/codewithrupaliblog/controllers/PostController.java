package com.codewithrupaliblog.controllers;

import org.springframework.http.MediaType;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.hibernate.engine.jdbc.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import com.codewithrupaliblog.config.AppConstants;
import com.codewithrupaliblog.payload.ApiResponse;
import com.codewithrupaliblog.payload.PostDto;
import com.codewithrupaliblog.payload.PostResponse;
import com.codewithrupaliblog.services.FileService;
import com.codewithrupaliblog.services.PostService;



@RestController
@RequestMapping("/api/")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	
	
	@Autowired
	private FileService fileService;
	
	
	//path ko get karo value me expression pass karo jo properti file me diya he
	@Value("${project.image}")
	private String path;
	
//	create
//post kis user ka he kis category ka he
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable Integer userId,
			@PathVariable Integer categoryId)
	{
		PostDto createPost = this.postService.createPost(postDto, userId, categoryId);
		return new ResponseEntity<PostDto>(createPost, HttpStatus.CREATED);
	}
	
	
	// get by user
           //jo id yaha pass kiya he us user ke sare post nikalne he
		@GetMapping("/user/{userId}/posts")
		public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Integer userId) 
		{
			//sare post anayege
			List<PostDto> posts = this.postService.getPostsByUser(userId);
			
			return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);

		}

		
		
		// get by category
		//jo categoryid diyi he usaki sari post milegi
		@GetMapping("/category/{categoryId}/posts")
		public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable Integer categoryId) 
		{

			List<PostDto> posts = this.postService.getPostsByCategory(categoryId);
			
			return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);

		}
		
		
		//GET ALL POST
		@GetMapping("/posts")                                    //bydefoult pagenumber 10 lete he
		public ResponseEntity<PostResponse> getAllPost(     //page start form 0
				//agar apane pageNumber aur pageSize define nahi kiya to by defoutl pageNumber 0 aur pagesize 5 hogi
		@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
		@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
		@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
		@RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir)
			
		
		
		{
		     PostResponse postResponse = this.postService.getAllPost(pageNumber,pageSize,sortBy,sortDir);
			return new ResponseEntity<PostResponse>(postResponse,HttpStatus.OK);
		}
		
		
		
		
		//GET SINGLE POST
		@GetMapping("/posts/{postId}")   //single chaiye to list ko hatao
		public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId)
		{
			PostDto postDto = this.postService.getPostById(postId);
			return new ResponseEntity<PostDto>(postDto,HttpStatus.OK);
		}
		
		
		
		// delete post
		@DeleteMapping("/posts/{postId}")
		public ApiResponse deletePost(@PathVariable Integer postId) {
			this.postService.deletePost(postId);
			return new ApiResponse("Post is successfully deleted !!", true);
		}
		
		
		
		// update post
		@PutMapping("/posts/{postId}")
		public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable Integer postId) {

			PostDto updatePost = this.postService.updatePost(postDto, postId);
			return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK);

		}
		
		// search
		//url me o keywords lelo ye keywords aap parameter me featch karo with the help of path verible
		//jo bhi keywords pass karoge o keywords jayega postService ke searchPosts method me use handle karega search postrepo se call karega 
		//findByTitleContaining keywords jo bhi keyword apane banaya usame like ki query banegi
		@GetMapping("/posts/search/{keywords}")
		public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable("keywords") String keywords) 
		{
			List<PostDto> result = this.postService.searchPosts(keywords);
			
			return new ResponseEntity<List<PostDto>>(result, HttpStatus.OK);
		}
		
		
		
		
		// post image upload

		@PostMapping("/post/image/upload/{postId}")  //kis post ki ye imge he ye patha lagega id se
		public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image") MultipartFile image,
				@PathVariable Integer postId) throws IOException {
//throw exception agar exception aayi to use globaly handale karenege
			//postId aur image ko use karake image ko upload karenge
			

			
//is postDto me saari purani details he images ki file ki agar pane yesa koi postid pass kiya ki jo he hi nahi he to exception 
//ecetipn throw hogi resource not found			
			PostDto postDto = this.postService.getPostById(postId);
			
			
			
			//postId ko use karake post ke andhar us database ke andhar us feild ko update karenge
			//jo file database me upload ho rahi he o mil jayegi images uploded to the folder
			String fileName = this.fileService.uploadImage(path, image);
			
			
			
			
			//file ka naam set karenge
			postDto.setImageName(fileName);
			
		
			
//database me file ko update karane ke liye postService ka use karenge postId se us post ka sara detail mil jayega
			//upadteed post return hoga name of the image will be updated in databse
			PostDto updatePost = this.postService.updatePost(postDto, postId);
			
			
			return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK);

		}
		
		
	
		  //method to serve files  
		
		                                         //imgae id
		//http://localhost:9191/api/post/image/126249e9-36b7-42b7-8a77-ff6635acb2d2.png forintend me jab aap imge tag use
//karenge to is path ko waha dena he aur image id ko dyanamically change karana he jo post he usaki image yaha hogi
	    @GetMapping(value = "/post/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
	    public void downloadImage(
	            @PathVariable("imageName") String imageName,
	            HttpServletResponse response
	    ) throws IOException {

	        InputStream resource = this.fileService.getResource(path, imageName);
	        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
	        StreamUtils.copy(resource,response.getOutputStream())   ;

	    }
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		

}
