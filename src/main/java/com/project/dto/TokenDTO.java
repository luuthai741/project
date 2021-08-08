package com.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenDTO {
	private int id;
	private String verifyToken;
	private String resetToken;
	private int userId;
}
