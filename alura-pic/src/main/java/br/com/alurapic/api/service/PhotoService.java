package br.com.alurapic.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import br.com.alurapic.api.model.Photo;

@Component
public interface PhotoService {

	Photo createOrUpdate(Photo photo);

	void delete(Long id);

	List<Photo> findAll();

	Optional<Photo> findById(Long id);

	List<Photo> findByUser(Long userId);
	

}
