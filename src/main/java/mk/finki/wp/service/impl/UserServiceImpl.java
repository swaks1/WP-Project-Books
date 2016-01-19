package mk.finki.wp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mk.finki.wp.model.Genre;
import mk.finki.wp.model.User;
import mk.finki.wp.persistance.UserRepository;
import mk.finki.wp.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepo;
	
	
	@Override
	public List<User> getUsersByName(String fname) {
		return userRepo.findByName(fname);
	}

	@Override
	public User getUserById(Long id) {
		return userRepo.getById(User.class, id);
	}

	@Override
	public User saveOrUpdateUser(User user) {
		
		return userRepo.saveOrUpdate(user);
	}


}
