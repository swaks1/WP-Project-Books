package mk.finki.wp.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import mk.finki.wp.model.Author;
import mk.finki.wp.model.Book;
import mk.finki.wp.model.User;
import mk.finki.wp.model.Genre;
import mk.finki.wp.persistance.UserRepository;
import mk.finki.wp.service.impl.UserServiceImpl;



@Controller
@RequestMapping("/")
public class TestController {
	
	@Autowired
	UserRepository	userRepo;
	
	@RequestMapping("/test")
	public ResponseEntity<Object> getObj(){
		
		Author author = new Author();
		author.setName("Agata");
		author.setSurname("Kristi");
		author.setBiography("Si ziveela nekade");
		author=userRepo.saveOrUpdateAuthor(author);
		
		Genre genre = new Genre();
		genre.setGenreName("Drama");
		genre = userRepo.saveOrUpdateZanr(genre);
		List<Genre>gList = new ArrayList<Genre>();
		gList.add(genre);
		
		Book book = new Book();
		book.setTopic("MojaKniga");
		book.setDesciption("Neso se desavat");
		book.setAuthor(author);
		book.setGenres(gList);
		book = userRepo.saveOrUpdateBook(book);
		List<Book>bList = new ArrayList<Book>();
		bList.add(book);

		
		return new ResponseEntity<Object>(book,HttpStatus.OK);
	}
	
	
	

}
