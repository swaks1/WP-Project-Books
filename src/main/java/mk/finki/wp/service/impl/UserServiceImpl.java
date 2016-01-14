package mk.finki.wp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import mk.finki.wp.model.User;
import mk.finki.wp.persistance.UserRepository;
import mk.finki.wp.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepo;
	
	@Override
	public List<User> getUsersByName(String fname) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getUserById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int saveOrUpdateUser(User user) {
		userRepo.saveOrUpdate(user);
		return 1;
	}

	


}
