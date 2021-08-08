package com.project.mail;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;

import com.project.entities.security.User;

public interface SendMail {
	void verify(User user ,String to, String token) throws UnsupportedEncodingException, MessagingException;
	void resetPassword(User user,String to,String token) throws UnsupportedEncodingException, MessagingException;
	void checkMail(User user, String to, String token) throws UnsupportedEncodingException, MessagingException;
}
