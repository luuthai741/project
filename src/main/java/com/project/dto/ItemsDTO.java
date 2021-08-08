package com.project.dto;

import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ItemsDTO {
	private int id;
	private int itemQuantity;
	private int bookId;
	private String bookName;
	private String bookAuthor;
	private String bookImage;
	private int bookPrice;
	private int bookQuantity;
	private int cartId;
	
	@Transient
	public String getImagePath() {
		if (bookName == null) {
			return null;
		}
		return "/book-images/"+ bookImage;
	}
}
