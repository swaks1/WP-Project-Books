package mk.finki.wp.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import mk.finki.wp.model.User;
import mk.finki.wp.service.AuthorService;
import mk.finki.wp.service.BookService;
import mk.finki.wp.service.CommentService;
import mk.finki.wp.service.FavBookService;
import mk.finki.wp.service.GenreService;
import mk.finki.wp.service.UserService;


@RestController
@RequestMapping(value = "/api" ,produces = MediaType.APPLICATION_JSON_VALUE)
public class RestfulController {
	
	@Autowired
	UserService userService; 
	
	@Autowired
	AuthorService authorService;
	
	@Autowired
	BookService bookService;
	
	@Autowired
	GenreService genreService;
	
	@Autowired
	FavBookService favBookService;
	
	@Autowired
	CommentService commentService;
	
	@RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
	public ResponseEntity<User> getAllUsers(@PathVariable Long id){
		
		User user =  userService.findUserById(id);
		return new ResponseEntity<User>(user,HttpStatus.OK);
		
		}
	@RequestMapping(value = "/users/register", method = RequestMethod.POST)
	public ResponseEntity<User> registerUser(@RequestBody User user){
		
		User savedUser =  userService.createUser(user);
		
		return new ResponseEntity<User>(savedUser,HttpStatus.OK);
		
		}
	
	@RequestMapping(value = "/users/update", method = RequestMethod.POST)
	public ResponseEntity<User> updateUser(@RequestBody User user){
		
		User updatedUser = userService.updateUser(user);
		return new ResponseEntity<User>(updatedUser,HttpStatus.OK);
		
		}

	

}
