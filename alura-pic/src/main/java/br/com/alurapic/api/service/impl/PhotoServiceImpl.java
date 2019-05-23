package br.com.alurapic.api.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.alurapic.api.model.Photo;
import br.com.alurapic.api.repository.PhotoRepository;
import br.com.alurapic.api.service.PhotoService;

@Component
public class PhotoServiceImpl implements PhotoService {
	
	@Autowired
	private PhotoRepository photoRepository;

	@Override
	public Photo createOrUpdate(Photo photo) {
		return photoRepository.save(photo);
	}

	@Override
	public void delete(Long id) {
		photoRepository.deleteById(id);
	}

	@Override
	public List<Photo> findAll() {
		return photoRepository.findAll();
	}

	@Override
	public Optional<Photo> findById(Long id) {
		return photoRepository.findById(id);
	}

	@Override
	public List<Photo> findByUser(Long userId) {
		return photoRepository.findByUserIdOrderByPostDateDesc(userId);
	}

}
