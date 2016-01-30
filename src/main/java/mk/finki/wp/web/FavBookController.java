package mk.finki.wp.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mk.finki.wp.model.Book;
import mk.finki.wp.model.FavBook;
import mk.finki.wp.service.impl.FavBookServiceImpl;

@RestController
@RequestMapping(value = "/api/fav-books" ,produces = MediaType.APPLICATION_JSON_VALUE)
public class FavBookController {

	@Autowired
	FavBookServiceImpl favBookService;
	
		// all fav BOOKS of USER
	@RequestMapping(value = "/of-user/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> findBooksOfUser(@PathVariable Long id){
		
		List <Book> books = favBookService.findAllBooksByUser(id);
		return new ResponseEntity<List<Book>>(books, HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/add-to-user", method = RequestMethod.POST)
	public ResponseEntity<?> addFavBookToUser( @RequestParam Long userId,
											@RequestParam Long bookId){
		
		FavBook favBook = favBookService.createFavBook(userId, bookId);
		return new ResponseEntity<FavBook>(favBook, HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/delete-from-user", method = RequestMethod.POST)
	public ResponseEntity<?> deleteFavBookFromUser( @RequestParam Long userId,
											@RequestParam Long bookId){
		
		Boolean delete = favBookService.deleteFavBook(userId, bookId);
		if(delete)
			return new ResponseEntity<String>("DELETED", HttpStatus.OK);
		else
			return new ResponseEntity<String>("FAILED TO DELETE", HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/toggle-to-user", method = RequestMethod.POST)
	public ResponseEntity<?> toggleFavBookToUser( @RequestParam Long userId,
											@RequestParam Long bookId){
		
		boolean favBookbool = favBookService.toggleFavBook(userId, bookId);
		return new ResponseEntity<Boolean>(favBookbool, HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/get-favbook-state", method = RequestMethod.GET)
	public ResponseEntity<?> getToggleStateFavBookToUser( @RequestParam Long userId,
											@RequestParam Long bookId){
		
		boolean favBookbool = favBookService.toggleStateFavBook(userId, bookId);
		return new ResponseEntity<Boolean>(favBookbool, HttpStatus.OK);
		
	}
}
