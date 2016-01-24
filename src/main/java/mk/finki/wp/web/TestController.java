package mk.finki.wp.web;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import mk.finki.wp.model.Author;
import mk.finki.wp.model.Book;
import mk.finki.wp.model.Comment;
import mk.finki.wp.model.FavBook;
import mk.finki.wp.model.User;
import mk.finki.wp.model.Genre;
import mk.finki.wp.persistance.AuthorRepository;
import mk.finki.wp.persistance.UserRepository;
import mk.finki.wp.service.AuthorService;
import mk.finki.wp.service.BookService;
import mk.finki.wp.service.impl.AuthorServiceImpl;
import mk.finki.wp.service.impl.BookServiceImpl;
import mk.finki.wp.service.impl.CommentServiceImpl;
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
	
	@Autowired
	CommentServiceImpl commentService;
	
	
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
								"Nema"+rand.nextInt(5555),
								null,
								null
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
		List<Book> knigiOdAuthor  = bookService.findAllBooksByAuthor(author.getId());
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
	
	@RequestMapping("/test5")
	public ResponseEntity<Object> getObj5(){
		String username = "i";
		String password = "n";
		
		if(userService.checkUser(username, password))
			System.out.println("TRUEEEEEEEEEEEEEEEEEEEe");
		else System.out.println("FALSEEEE");
		return new ResponseEntity<Object>(null,HttpStatus.OK);
	}
	
	//test za Comment
	@RequestMapping("/test4")
	public ResponseEntity<Object> getObj4(){
		User user1 = userService.findUserById(20L);
		Book book1 = bookService.findBookById(10L);
		
		User user2 = userService.createUser("Igor", "nikolov", "i", "n", "21", "12");
		Book book2 = bookService.findBookById(11L);
		
		Comment comment = commentService.createComment(user1, book1, "Ova e dobra kniga.");
		Comment comment2 = commentService.createComment(user2, book2, "Ova e 13131 .");
			
		List<Comment> komentariOdKniga = commentService.findAllCommentsByBook(book2);
		printJson(komentariOdKniga);
		
		comment = commentService.findCommentById(2L);
		commentService.UpdateComment("NOV KOMENTAR", comment);
		
		return new ResponseEntity<Object>(comment,HttpStatus.OK);
	}
	
	@RequestMapping("/test/file-upload")
	public String uploadJsp(){
		return "upload";
	}
	
			//upload FILE 
	 @RequestMapping(value = "/test/file-upload", method = RequestMethod.POST)
	 public String uploadFileHandler(@RequestParam("file") MultipartFile file,
			 							HttpServletRequest request) 
	 {
	 
	        if (!file.isEmpty()) {
	            try {
	                // Creating the PATH to directory to store file
	                String uploadsDir = "/testPictures/";
                    String realPathtoUploads =  request.getServletContext().getRealPath(uploadsDir);
                   
                    //Creating the DIRECTORY from PATH and checking if it exists
                    File directory = new File(realPathtoUploads);
                    if(! directory.exists())
                    {
                    	directory.mkdir();
                    }
                    
                    //creating full path of the file we have to save
                    String originalName = file.getOriginalFilename();
                    String filePath = realPathtoUploads + originalName;
                    
                    //trasnfering the file to the destination (saving)
                    File dest = new File(filePath);	
                    file.transferTo(dest);
	 
	                return "You successfully uploaded file" +originalName ;
	            }
	            catch (Exception e) {
	                return "You failed to upload  => " + e.getMessage();
	            }
	        }	        
	        else 
	        {
	            return "You failed to upload  because the file was empty.";
	        }
	    }
	 
	 
	 
	 	//zemanje na slika jpg
	 @RequestMapping(value = "/test/get-image/{imageName}")
	 @ResponseBody
	 public byte[] getImage(@PathVariable String imageName, HttpServletRequest request)  {
		 
		 System.out.println(imageName);
	    //get real path ja dava lokacijata od proektot...dodavame uploads za da stigneme do slikite
		String rpath = request.getServletContext().getRealPath("/testPictures/"+imageName+".jpg");
		 
	 	//dobivanje na PATH od String;
		Path path = Paths.get(rpath);
		System.out.println(rpath);
		
		try {
				//transfer na slikata vo bajti 
			 byte[] data  = Files.readAllBytes(path);
			 return data;
			 
		} 
		catch (IOException e) {	
			e.printStackTrace();
			return null;
		} 

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
