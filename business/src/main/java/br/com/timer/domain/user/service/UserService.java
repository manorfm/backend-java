package br.com.timer.domain.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.timer.domain.user.User;
import br.com.timer.domain.user.repository.UserRepository;
import br.com.timer.exception.UserAlreadyExistException;
import br.com.timer.security.KeyUtil;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Transactional
	public void save(User user) {
		if (get(user.getPis()) == null) {
			user.setPassword(KeyUtil.encodeBase64(user.getPassword()));
			userRepository.save(user);
		} else {
			throw new UserAlreadyExistException(user.getPis());
		}
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
	    return userRepository.findOne(id);
	}

	@Transactional
	public List<User> findAll() {
		return userRepository.findAll();
	}
}