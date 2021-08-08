package com.project.service.impl;

import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.project.dto.TokenDTO;
import com.project.entities.Token;
import com.project.entities.security.User;
import com.project.repository.TokenRepository;
import com.project.service.TokenService;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class TokenServiceImpl implements TokenService{
	private TokenRepository tokenRepo;

	@Override
	public Token findByVerifyToken(String token) {
		return tokenRepo.findByVerifyToken(token);
	}

	@Override
	public Token findByResetToken(String token) {
		return tokenRepo.findByResetToken(token);
	}

	@Override
	public void saveVerifyTokenByUser(User user) {
		Token token = new Token();
		token.setVerifyToken(UUID.randomUUID().toString());
		token.setUser(user);	
		tokenRepo.save(token);
	}

	@Override
	public void saveResetTokenByUser(User user) {
		Token token = user.getToken();
		if(token == null) {
			token = new Token();
			token.setResetToken(UUID.randomUUID().toString());
			token.setUser(user);
			tokenRepo.save(token);
		}
		token.setResetToken(UUID.randomUUID().toString());
		tokenRepo.save(token);
	}

	@Override
	public Token findByID(int id) {
		// TODO Auto-generated method stub
		return tokenRepo.findById(id).orElse(null);
	}

	@Override
	public void deleteByID(int id) {
		tokenRepo.deleteById(id);
	}

	@Override
	public TokenDTO joinUser(int tokenId) {
		return tokenRepo.joinUser(tokenId);
	}

	@Override
	public void save(Token token) {
		tokenRepo.save(token);	
	}
	
}
