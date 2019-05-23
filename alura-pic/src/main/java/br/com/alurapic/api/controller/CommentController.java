package br.com.alurapic.api.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alurapic.api.model.Comment;
import br.com.alurapic.api.model.Photo;
import br.com.alurapic.api.model.User;
import br.com.alurapic.api.response.Response;
import br.com.alurapic.api.service.CommentService;
import br.com.alurapic.api.service.PhotoService;
import br.com.alurapic.api.service.UserService;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

	@Autowired
	private CommentService commentService;

	@Autowired
	private PhotoService photoService;

	@Autowired
	private UserService userService;

	@PostMapping(value = "{photoId}/{userId}")
	@PreAuthorize("hasAnyRole('CUSTOMER')")
	public ResponseEntity<Response<Comment>> add(@RequestBody Comment comment, @PathVariable Long photoId,
			@PathVariable Long userId, BindingResult result) {

		Response<Comment> response = new Response<Comment>();

		try {

			if (result.hasErrors()) {
				result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
				return ResponseEntity.badRequest().body(response);
			}

			Photo photo = photoService.findById(photoId).get();
			User user = userService.findById(userId).get();
			
			photo.setComments(photo.getComments() + 1);
			photoService.createOrUpdate(photo);
			
			comment.setDate(new Date());
			comment.setPhoto(photo);
			comment.setUser(user);
			
			Comment commentPersisted = (Comment) commentService.create(comment);

			response.setData(commentPersisted);

		} catch (Exception e) {
			response.getErrors().add(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}

		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "{photoId}")
	@PreAuthorize("hasAnyRole('CUSTOMER')")
	public List<Comment> findByUserId(@PathVariable Long photoId) {

		List<Comment> comments = null;
		comments = commentService.listAllFromPhoto(photoId);

		return comments;
	}
}
