package br.com.alurapic.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.alurapic.api.model.Photo;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {

	Optional<Photo> findById(Long id);

	List<Photo> findByUserIdOrderByPostDateDesc(Long userId);	

}
