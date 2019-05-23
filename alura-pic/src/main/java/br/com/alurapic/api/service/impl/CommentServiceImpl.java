package br.com.alurapic.api.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.alurapic.api.model.Comment;
import br.com.alurapic.api.repository.CommentRepository;
import br.com.alurapic.api.service.CommentService;

@Component
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepository commentRepository;

	@Override
	public Comment create(Comment comment) {
		return commentRepository.save(comment);
	}

	@Override
	public Optional<Comment> findById(Long commentId) {
		return commentRepository.findById(commentId);
	}

	@Override
	public List<Comment> listAllFromPhoto(Long photoId) {
		return commentRepository.findByPhotoIdOrderByDateDesc(photoId);
	}
}
