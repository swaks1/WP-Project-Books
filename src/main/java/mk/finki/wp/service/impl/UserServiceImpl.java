package mk.finki.wp.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;

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
	
	@Autowired
	GenreServiceImpl genreService;
	
	
	@Override
	public List<User> findUsersByName(String fname) {
		return userRepo.findByName(fname);
	}

	@Override
	public User findUserById(Long id) {
		User user;
		
		try {
			 user = userRepo.findById(User.class, id);
			
		} catch (Exception e) {
			user = null;
		}
		
		return user;
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
	public User addGenres(User user,List<Long> genres) {
		user.setGenres(new ArrayList<Genre>());
		for(Long g : genres){
			user.addGenre(genreService.findById(g));
		}
		
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
		user.setUsername(username.toLowerCase());
		user.setDateCreated(new Date());
		
		return saveOrUpdateUser(user);
	}
	@Override
	public User createUser(User user) {
		
		user.setUsername(user.getUsername().toLowerCase());
		user.setDateCreated(new Date());
		
		return saveOrUpdateUser(user);
	}

	@Override
	public Boolean checkUser(String username, String password) {
		List<User> usersByUsername = userRepo.findByUsername(username.toLowerCase());
	
		if(usersByUsername != null && usersByUsername.size()>0){
			User user = usersByUsername.get(0);
			if(user.getPassword().equals(password))
				return true;
			else 
				return false;
		}
		else
			return false;
	}

	@Override
	public User updateUser(User user) {
		User userOld = userRepo.findById(User.class, user.getId());
		user.setPassword(userOld.getPassword());
		user.setDateCreated(userOld.getDateCreated());
		return saveOrUpdateUser(user);
	}

	@Override
	public String findImageById(Long id) {
		return userRepo.findImageById(id);
	}

	@Override
	public User findUsersByUsername(String username) {
		
		return userRepo.findByUsername(username).get(0);
	}
	public List<String> findAllUsernames(){
		return userRepo.findAllUsernames();
	}

	
	public List<User> findAllUsers() {
		return userRepo.findAll();
	}

	


}
