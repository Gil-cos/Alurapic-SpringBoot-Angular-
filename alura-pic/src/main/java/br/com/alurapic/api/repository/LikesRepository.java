package br.com.alurapic.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.alurapic.api.model.Likes;

public interface LikesRepository extends JpaRepository<Likes, Long>{

	Likes findByUserIdAndPhotoId(Long userId, Long PhotoId);

}
