package br.com.alurapic.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.alurapic.api.model.Likes;
import br.com.alurapic.api.repository.LikesRepository;
import br.com.alurapic.api.service.LikesService;

@Component
public class LikesServiceImpl implements LikesService {

	@Autowired
	private LikesRepository likeRepository;
	
	@Override
	public Likes createOrUpdate(Likes like) {
		return likeRepository.save(like);
	}

	@Override
	public Likes findById(Long likeId) {
		return likeRepository.findById(likeId).get();
	}

	@Override
	public boolean photoLiked(Long photoId, Long userId) {
		Likes like = likeRepository.findByUserIdAndPhotoId(userId, photoId);
		if (like != null) {
			return true;
		}
		return false;
	}

}
