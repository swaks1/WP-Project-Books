package mk.finki.wp.service;

import java.util.List;

import mk.finki.wp.model.Genre;
import mk.finki.wp.model.User;

public interface UserService {
	
	public List<User> findUsersByName(String fname);
	public User findUserById(Long id);
	public User saveOrUpdateUser(User user);
	public User addGenre(User user,Genre genre);
	public User createUser(String fname,String lname,String username,
							String password,String biography,String image);
	public Boolean checkUser(String username, String password);

}
