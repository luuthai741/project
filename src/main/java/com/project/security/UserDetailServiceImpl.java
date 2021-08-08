package com.project.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.project.entities.security.User;
import com.project.repository.UserRepository;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
 		User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Không thể tìm thấy người dùng với tên " + username)); 		
		return new UserPrinciple(user);
	}

}
