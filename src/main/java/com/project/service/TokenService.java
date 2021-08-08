package com.project.service;

import com.project.dto.TokenDTO;
import com.project.entities.Token;
import com.project.entities.security.User;

public interface TokenService {
	Token  findByID(int id);
	void deleteByID(int id);
	Token findByVerifyToken(String token);
	Token findByResetToken(String token);
	void saveVerifyTokenByUser(User user);
	void saveResetTokenByUser(User user);
	void save(Token token);
	TokenDTO joinUser(int tokenId);
}
