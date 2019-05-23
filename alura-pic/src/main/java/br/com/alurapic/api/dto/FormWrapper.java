package br.com.alurapic.api.dto;

import org.springframework.web.multipart.MultipartFile;

public class FormWrapper {

	private MultipartFile imageFile;
	private boolean allowComments;
	private String description;

	
	
	public MultipartFile getImageFile() {
		return imageFile;
	}

	public void setImageFile(MultipartFile imageFile) {
		this.imageFile = imageFile;
	}

	public boolean isAllowComments() {
		return allowComments;
	}

	public void setAllowComments(boolean allowComments) {
		this.allowComments = allowComments;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
