package mk.finki.wp.service;

import java.util.List;

import mk.finki.wp.model.Genre;
import mk.finki.wp.model.User;

public interface UserService {
	
	public List<User> findUsersByName(String fname);
	public User findUsersByUsername(String username);
	public User findUserById(Long id);
	public User saveOrUpdateUser(User user);
	public User addGenre(User user,Genre genres);
	public User addGenres(User user,List<Long> genres);
	public User createUser(String fname,String lname,String username,
							String password,String biography,String image);
	public User createUser(User user);
	public User updateUser(User user);
	public Boolean checkUser(String username, String password);
	public String findImageById(Long id);
//	public List<String> findAllUsernames();
//	public List<User> findAllUsers();

}
