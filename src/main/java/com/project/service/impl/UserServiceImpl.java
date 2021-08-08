package com.project.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.transaction.Transactional;

import org.springframework.mail.MailAuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.dto.TokenDTO;
import com.project.entities.Token;
import com.project.entities.security.Role;
import com.project.entities.security.RoleType;
import com.project.entities.security.User;
import com.project.mail.SendMail;
import com.project.repository.RoleRepository;
import com.project.repository.TokenRepository;
import com.project.repository.UserRepository;
import com.project.service.TokenService;
import com.project.service.UserService;
import com.project.ultil.Ultil;

import javassist.NotFoundException;
import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class UserServiceImpl implements UserService {
	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;
	private RoleRepository roleRepository;
	private SendMail sendMail;
	private TokenService tokenService;
	
	@Override
	public List<User> findAll() {
		// TODO Auto-generated method stub
		return userRepository.findAll();
	}

	@Override
	public User findById(int id) {
		return userRepository.findById(id).orElse(null);
	}

	@Override
	public void save(User user) {
		if(userRepository.existsByUsername(user.getUsername())) {
			throw new IllegalStateException("Tên tài khoản đã được sử dụng !");
		}
		if(userRepository.existsByEmail(user.getEmail())) {
			throw new IllegalStateException("Email đã được sử dụng !");
		}
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		Set<Role> roles = new HashSet<Role>();
		Role role = roleRepository.findByName(RoleType.ROLE_USER);
		roles.add(role);
		user.setRoles(roles);
		user.setEnabled(false);
		
		userRepository.save(user);
	}

	@Override
	public void deleteByID(int id) {
		userRepository.deleteById(id);
	}

	@Override
	public User findByUsername(String username) {
		// TODO Auto-generated method stub
		return userRepository.findByUsername(username).orElse(null);
	}

	@Override
	public void sigin(String email, String link) {
		User user = this.findByEmail(email);
		tokenService.saveVerifyTokenByUser(user);
		TokenDTO tokenDTO = userRepository.joinToken(user.getId());
		try {
			sendMail.verify(user, link, tokenDTO.getVerifyToken());
		} catch (Exception e ) {
			e.getMessage();
		}
		
	}
	
	@Override
	public boolean verify(String verifyToken) {
		Token token = tokenService.findByVerifyToken(verifyToken);
		User user = token.getUser();
		if(user == null || user.isEnabled()) {
			return false;
		}
		else {
			userRepository.enable(user.getId());
			token.setVerifyToken(null);
			tokenService.save(token);
			return true;
		}
	}

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email).orElse(null);
	}

	@Override
	public void forgot(String email, String link) {
		try {
			User user = userRepository.findByEmail(email).orElse(null);
			if(user != null) {
				tokenService.saveResetTokenByUser(user);
				TokenDTO tokenDTO = userRepository.joinToken(user.getId());
				Token token = tokenService.findByID(tokenDTO.getId());
				if(token != null) {
					user.setToken(token);
				}
				sendMail.resetPassword(user, link, tokenDTO.getResetToken());
			}			
		} catch (Exception e) {
			System.out.println(e.getCause());
		}
	}

	@Override
	public boolean checkResetToken(String resetToken) {	
		Token token = tokenService.findByResetToken(resetToken);
		if(token != null) {
			return true;
		}
		return false;
	}

	@Override
	public void reset(String resetToken, String password) {
		Token token = tokenService.findByResetToken(resetToken);
		TokenDTO tokenDTO = tokenService.joinUser(token.getId());
		User user;
		try {
			user = userRepository.findById(tokenDTO.getId()).orElseThrow(() -> new NotFoundException("Not found this user"));
			if(user != null) {
				this.updatePassword(user, password);
			}
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void updatePassword(User user, String password) {
			user.setPassword(passwordEncoder.encode(password));
			userRepository.save(user);
			
			Token token = user.getToken();
			token.setResetToken(null);
			tokenService.save(token);	
	}

	

}
