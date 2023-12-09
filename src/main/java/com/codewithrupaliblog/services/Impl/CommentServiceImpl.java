package com.codewithrupaliblog.services.Impl;

//import javax.xml.stream.events.Comment;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codewithrupaliblog.entities.Comments;
import com.codewithrupaliblog.entities.Post;
import com.codewithrupaliblog.expections.ResourceNotFoundException;
import com.codewithrupaliblog.payload.CommentDto;
import com.codewithrupaliblog.repositories.CommentRepo;
import com.codewithrupaliblog.repositories.PostRepo;
import com.codewithrupaliblog.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService
{
	
	
	
	@Autowired
	private PostRepo postRepo;

	@Autowired
	private CommentRepo commentRepo;

	@Autowired
	private ModelMapper modelMapper;
	
	
	
	
	//CREATE COMMENT
	@Override
	//postId ka help se  CommentDto ko insert karana he database me 
	public CommentDto createComment(CommentDto commentDto, Integer postId) {

		// is postId wala post Featch karte he pahile  post nahi mila us id ka to exception milegi
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "post id ", postId));
		
//commentDto ko comment ke object me convert karnge with the help of model mapper
		Comments comment = this.modelMapper.map(commentDto, Comments.class);

//comment mil gaya to comment ke nadhar post ko set karte he
		comment.setPost(post);

		
		Comments savedComment = this.commentRepo.save(comment);

		//comment ko return karnge   comment ko dto class me convert karnge
		return this.modelMapper.map(savedComment, CommentDto.class);
	}
	
	
	
	//DELETE COMMNET

	@Override
	public void deleteComment(Integer commentId) {

		//comment ko featch karo
		Comments com = this.commentRepo.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comments", "CommentId", commentId));
		
		//delete karo
		this.commentRepo.delete(com);
	}

}
