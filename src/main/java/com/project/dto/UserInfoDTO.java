package com.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserInfoDTO {
	private String username;
	private String email;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String address;
}
