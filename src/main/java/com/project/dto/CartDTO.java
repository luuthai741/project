package com.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartDTO {
	private int id;
	private long total;
	private boolean enabled;
	private int userId;
	private String username;
	private String email;
	
}
