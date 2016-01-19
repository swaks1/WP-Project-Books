package mk.finki.wp.service.impl;

import java.util.ArrayList;
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
	public List<User> findUsersByName(String fname) {
		return userRepo.findByName(fname);
	}

	@Override
	public User findUserById(Long id) {
		return userRepo.getById(User.class, id);
	}

	@Override
	public User saveOrUpdateUser(User user) {
		
		return userRepo.saveOrUpdate(user);
	}

	@Override
	public User addGenre(User user,Genre genre) {
		if(user.getGenres() == null){
			user.setGenres(new ArrayList<Genre>());
		}
		user.addGenre(genre);
		user=saveOrUpdateUser(user);
		return user;
	}

	@Override
	public User createUser(String fname, String lname, String username, String password, String biography,
			String image) {
		User user = new User();
		user.setBiography(biography);
		user.setFname(fname);
		user.setImage(image);
		user.setLname(lname);
		user.setPassword(password);
		user.setUsername(username);
		
		return user;
	}


}
