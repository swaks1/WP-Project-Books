package mk.finki.wp.service;

import java.util.List;

import mk.finki.wp.model.Genre;
import mk.finki.wp.model.User;

public interface UserService {
	
	public List<User> getUsersByName(String fname);
	public User getUserById(Long id);
	public User saveOrUpdateUser(User user);
}
