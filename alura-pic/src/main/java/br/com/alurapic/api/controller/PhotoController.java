package br.com.alurapic.api.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.alurapic.api.dto.FormWrapper;
import br.com.alurapic.api.model.Likes;
import br.com.alurapic.api.model.Photo;
import br.com.alurapic.api.model.User;
import br.com.alurapic.api.response.Response;
import br.com.alurapic.api.service.LikesService;
import br.com.alurapic.api.service.PhotoService;
import br.com.alurapic.api.service.StorageService;
import br.com.alurapic.api.service.UserService;

@RestController
@RequestMapping("/api/photo")
public class PhotoController {

	@Autowired
	private PhotoService photoService;

	@Autowired
	private UserService userService;

	@Autowired
	private StorageService storageService;

	@Autowired
	private LikesService likeService;

	@Autowired
	protected br.com.alurapic.api.security.jwt.JwtTokenUtil jwtTokenUtil;

	@PostMapping()
	@PreAuthorize("hasAnyRole('CUSTOMER')")
	public ResponseEntity<Response<Photo>> add(@ModelAttribute FormWrapper form, BindingResult result,
			HttpServletRequest request) {

		Response<Photo> response = new Response<Photo>();
		Photo photo = new Photo();

		try {

			storageService.store(form.getImageFile());

			photo.setUrl(form.getImageFile().getOriginalFilename().trim());
			photo.setAllowComments(form.isAllowComments());
			photo.setDescription(form.getDescription());
			photo.setPostDate(new Date());
			photo.setComments(0);
			photo.setLikes(0);
			photo.setUser(userFromRequest(request));

			Photo photoPersisted = (Photo) photoService.createOrUpdate(photo);

			response.setData(photoPersisted);

		} catch (Exception e) {
			response.getErrors().add(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}

		return ResponseEntity.ok(response);
	}

	@GetMapping()
	@PreAuthorize("hasAnyRole('CUSTOMER')")
	public ResponseEntity<Response<List<Photo>>> findAll() {

		Response<List<Photo>> response = new Response<List<Photo>>();
		List<Photo> photos = null;

		photos = photoService.findAll();

		response.setData(photos);

		return ResponseEntity.ok(response);
	}

	@GetMapping(value = "list/{userName}")
	@PreAuthorize("hasAnyRole('CUSTOMER')")
	public ResponseEntity<Response<List<Photo>>> findByUserId(@PathVariable String userName) {

		Response<List<Photo>> response = new Response<List<Photo>>();
		List<Photo> photos = null;

		User user = userService.findByUserName(userName);
		photos = photoService.findByUser(user.getId());

		response.setData(photos);

		return ResponseEntity.ok(response);
	}

	@GetMapping(value = "{photoId}")
	@PreAuthorize("hasAnyRole('CUSTOMER')")
	public ResponseEntity<Photo> findByPhotoId(@PathVariable Long photoId) {

		Optional<Photo> optional = photoService.findById(photoId);
		Photo photo = optional.get();

		return ResponseEntity.ok(photo);
	}

	@DeleteMapping(value = "{photoId}")
	@PreAuthorize("hasAnyRole('CUSTOMER')")
	public ResponseEntity<Response<String>> delete(@PathVariable Long photoId) throws IOException {

		Response<String> response = new Response<String>();
		Optional<Photo> optional = photoService.findById(photoId);
		Photo photo = optional.get();

		if (photo == null) {
			response.getErrors().add("Register not found id:" + photoId);
			return ResponseEntity.badRequest().body(response);
		}

		photoService.delete(photoId);
		storageService.delete(photo.getUrl());

		return ResponseEntity.ok(new Response<String>());
	}

	@PostMapping("/{photoId}/like")
	@PreAuthorize("hasAnyRole('CUSTOMER')")
	public ResponseEntity<Response<Likes>> likePhoto(@RequestBody Likes like, @PathVariable Long photoId,
			HttpServletRequest request) {

		Response<Likes> response = new Response<>();
		Photo photo = photoService.findById(photoId).get();
		User user = userFromRequest(request);

		if (likeService.photoLiked(photoId, user.getId())) {
			response.getErrors().add("Photo already liked by user " + user.getUserName());
			return ResponseEntity.badRequest().body(response);

		} else {
			like.setPhoto(photo);
			like.setUser(userService.findById(user.getId()).get());
			photo.setLikes(photo.getLikes() + 1);
		}

		Likes persistedLike = likeService.createOrUpdate(like);
		response.setData(persistedLike);

		return ResponseEntity.ok(response);
	}

	@GetMapping("/image/{filename}")
	@ResponseBody
	public ResponseEntity<Resource> getFile(@PathVariable String filename) {
		Resource file = storageService.loadFile(filename);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}

	public User userFromRequest(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String userName = jwtTokenUtil.getUsernameFromToken(token);
		return userService.findByUserName(userName);
	}
}
