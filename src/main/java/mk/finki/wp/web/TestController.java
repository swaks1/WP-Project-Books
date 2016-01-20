package mk.finki.wp.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import mk.finki.wp.model.Author;
import mk.finki.wp.model.Book;
import mk.finki.wp.model.FavBook;
import mk.finki.wp.model.User;
import mk.finki.wp.model.Genre;
import mk.finki.wp.persistance.AuthorRepository;
import mk.finki.wp.persistance.UserRepository;
import mk.finki.wp.service.AuthorService;
import mk.finki.wp.service.BookService;
import mk.finki.wp.service.impl.AuthorServiceImpl;
import mk.finki.wp.service.impl.BookServiceImpl;
import mk.finki.wp.service.impl.FavBookServiceImpl;
import mk.finki.wp.service.impl.GenreServiceImpl;
import mk.finki.wp.service.impl.UserServiceImpl;



@Controller
@RequestMapping("/")
public class TestController {
	
	@Autowired
	UserServiceImpl userService; 
	
	@Autowired
	AuthorServiceImpl authorService;
	
	@Autowired
	BookServiceImpl bookService;
	
	@Autowired
	GenreServiceImpl genreService;
	
	@Autowired
	FavBookServiceImpl favBookService;
	
	
	Random rand = new Random();
	
		//test za Books ,Genres i Authors
	@RequestMapping("/test")
	public ResponseEntity<Object> getObj(){
		

		Author author = authorService.createAuthor(
							"Blaze"+rand.nextInt(5555), 
							"Koneski"+rand.nextInt(5555), 
							"nema"+rand.nextInt(5555), 
							"sdasdas"+rand.nextInt(5555)
							);
		author = authorService.saveOrUpdateAuthor(author);
			//vo konzola za test se printa
		printJson(author);
		
		Book book = bookService.createBook("MOJA KNIGA HEH"+rand.nextInt(5555),
								"NestoSEDesavat"+rand.nextInt(5555),
								"Nema"+rand.nextInt(5555)
								);
		book = bookService.setAuthor(book, author);
		//vo konzola za test se printa
		printJson(book);
			
		//stavanje na genre vo baza i bo nekoja  kniga
		
		Genre genre = genreService.createGenre("nekoeGenre");
		genre = genreService.saveOrUpdateGenre(genre);
		book = bookService.addGenre(book, genre);
		
		genre = genreService.createGenre("rok");
		genre = genreService.saveOrUpdateGenre(genre);
		book = bookService.addGenre(book, genre);
		
			//stavanje na genre vo baza i bo nekoja  kniga
		genre = genreService.createGenre("komedija");
		genre = genreService.saveOrUpdateGenre(genre);
		book = bookService.addGenre(book, genre);
			
		
				//site knigi koi sodzat genre komedija vo slucajov
		List<Book> knigiSoGenre = bookService.findAllBooksByGenre(genre);
		printJson(knigiSoGenre);
		
		
			//site knigi od daden avtor
		author = authorService.findAuthorById(1L);
		List<Book> knigiOdAuthor  = bookService.findAllBooksByAuthor(author);
		printJson(knigiOdAuthor);
		
			//site genrovi od baza
		List<Genre>allGenres = genreService.findAllGenres();
		printJson(allGenres);
		
			//genre so ID
		genre = genreService.findById(2L);
		printJson(genre);
			//genre po iMe
		genre = genreService.findByName("rok");
		printJson(genre);
		
		return new ResponseEntity<Object>(knigiSoGenre,HttpStatus.OK);
	}
		//test za USERS
	@RequestMapping("/test2")
	public ResponseEntity<Object> getObj2(){
		Genre genre1 = genreService.findById(1L);
		Genre genre2 = genreService.findById(2L);
		Genre genre3 = genreService.findById(3L);
		
		User userNov = userService.createUser("Riste", "Poposki", "Swaks",
				"riste2123", "biobibobio", "nema");
		userService.addGenre(userNov, genre1);
		userService.addGenre(userNov, genre2);
		userService.saveOrUpdateUser(userNov);
		
		User userNov2 = userService.createUser("Marija", "Todororva", "Mare",
				"marija283", "biobibobio", "ubavaSlika");
		userService.addGenre(userNov2, genre3);
		userService.addGenre(userNov2, genre1);
		userService.saveOrUpdateUser(userNov2);
		
		User userNov3 = userService.createUser("Riste", "P", "R",
				"R2", "sd", "ww");
		userService.addGenre(userNov3, genre3);
		userService.saveOrUpdateUser(userNov3);

		List<User> usersByName = userService.findUsersByName("Riste");
		printJson(usersByName);
		
		User userById = userService.findUserById(16L);
		printJson(userById);
		
		User user5 =userService.findUserById(16L);
		List<Genre> genresOfUser = genreService.findAllGenresOfUser(user5);
		printJson(genresOfUser);
	
		return new ResponseEntity<Object>(userById,HttpStatus.OK);
	}
	
	//test za FavBook
	@RequestMapping("/test3")
	public ResponseEntity<Object> getObj3(){
		User user1 = userService.findUserById(20L);
		Book book1 = bookService.findBookById(10L);
		Book book2 = bookService.findBookById(11L);
		Book book3 = bookService.findBookById(9L);
		
		FavBook favBook1 = favBookService.createFavBook(user1, book1);
		favBook1 = favBookService.createFavBook(user1, book2);
		favBook1 = favBookService.createFavBook(user1, book3);
			
	
		//all Books from USER
		List<Book> favBookByUser =favBookService.findAllBooksByUser(user1);
		printJson(favBookByUser);
		
		//all favBooks in BASE
		 List<FavBook> allFavBooks = favBookService.findAllFavBooks();	
		 printJson(allFavBooks);
		 
		 favBookService.deleteFavBook(user1, book1);
		return new ResponseEntity<Object>(favBookByUser,HttpStatus.OK);
	}
	
		//printanje na JAVA objekt vo JSON vo consola....
	public void printJson(Object obj){
		 ObjectMapper mapper = new ObjectMapper();
		  try {
			System.out.println(mapper.writerWithDefaultPrettyPrinter().
					writeValueAsString(obj));
			

			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

}
