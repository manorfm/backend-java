package br.com.timer.domain.user.service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.timer.domain.user.User;
import br.com.timer.domain.user.repository.UserRepository;
import br.com.timer.exception.UserAlreadyExistException;
import br.com.timer.exception.UserNotFoundException;
import br.com.timer.security.KeySecurity;
import br.com.timer.security.KeyUtil;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Transactional
	public void save(User user) {
		try {
			if (findOne(user.getPis()) == null) {
				user.setPassword(KeyUtil.doHash(user.getPassword(), KeySecurity.KEY));
				userRepository.save(user);
			} else {
				throw new UserAlreadyExistException(user.getPis());
			}
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	private User findOne(Long pis) {
		return userRepository.findOne(pis);
	}

	@Transactional
    public void remove(User user) {
	    userRepository.delete(user);
    }

	@Transactional
    public void remove(Long id) {
		User user = get(id);
	    userRepository.delete(user);
    }
	
	@Transactional
	public User get(Long id) {
		User user = userRepository.findOne(id);
		if (user == null) {
			throw new UserNotFoundException(id);
		}
	    return user;
	}

	@Transactional
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Transactional
	public User login(String name, String password) {
		try {
			return userRepository.findOneByNameAndPassword(name, KeyUtil.doHash(password, KeySecurity.KEY));
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}
}