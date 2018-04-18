package br.com.timer.domain.user.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.timer.domain.user.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    
	List<User> findAll();
}