package br.com.alurapic.api.service;

import org.springframework.stereotype.Component;

import br.com.alurapic.api.model.Likes;

@Component
public interface LikesService {

	Likes createOrUpdate(Likes like);

	Likes findById(Long likeId);
	
	boolean photoLiked(Long photoId, Long userId);
}
