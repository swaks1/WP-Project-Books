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
import mk.finki.wp.model.User;
import mk.finki.wp.model.Genre;
import mk.finki.wp.persistance.AuthorRepository;
import mk.finki.wp.persistance.UserRepository;
import mk.finki.wp.service.AuthorService;
import mk.finki.wp.service.BookService;
import mk.finki.wp.service.impl.UserServiceImpl;



@Controller
@RequestMapping("/")
public class TestController {
	
	@Autowired
	UserRepository userRepo; //staveno za test
	
	@Autowired
	AuthorService authorService;
	
	@Autowired
	BookService bookService;
	
	
	Random rand = new Random();
	
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
		Genre genre = new Genre();
		genre.setGenreName("Drama");
		//genre = userRepo.saveOrUpdateZanr(genre);
		List<Genre>gList = new ArrayList<Genre>();
		gList.add(genre);
		
		List<Book>bList = new ArrayList<Book>();
		bList.add(book);

		genre.setGenreName("rok");
	//	genre=userRepo.saveOrUpdateZanr(genre);
		book = bookService.addGenre(book, genre);
			//stavanje na genre vo baza i bo nekoja  kniga
		genre = new Genre();
		genre.setGenreName("komedija");
	//	genre=userRepo.saveOrUpdateZanr(genre);
		book = bookService.addGenre(book, genre);
			
		
				//site knigi koi sodzat genre komedija vo slucajov
		List<Book> knigiSoGenre = bookService.findAllBooksByGenre(genre);
		
		printJson(knigiSoGenre);
		
	
		return new ResponseEntity<Object>(book,HttpStatus.OK);
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
