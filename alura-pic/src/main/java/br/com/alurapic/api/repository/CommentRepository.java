package br.com.alurapic.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.alurapic.api.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
	
	Optional<Comment> findById(Long commentId);
	
	List<Comment> findByPhotoIdOrderByDateDesc(Long photoId);

}
