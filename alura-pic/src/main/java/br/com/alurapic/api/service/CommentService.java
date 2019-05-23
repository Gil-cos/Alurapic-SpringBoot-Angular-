package br.com.alurapic.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import br.com.alurapic.api.model.Comment;

@Component
public interface CommentService {
		
	Comment create(Comment comment);
	
	Optional<Comment> findById(Long commentId);
	
	List<Comment> listAllFromPhoto(Long photoId);

}
