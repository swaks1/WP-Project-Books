package mk.finki.wp.web;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import mk.finki.wp.model.Book;

import mk.finki.wp.service.BookService;


@RestController
@RequestMapping(value = "/api/books" ,produces = MediaType.APPLICATION_JSON_VALUE)
public class BookController {

	@Autowired
	BookService bookService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<List<Book>> getAllBooks(){
		
		List<Book> allBooks = bookService.findAllBooks();
		return new ResponseEntity<List<Book>>(allBooks,HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Book> getBookById(@PathVariable Long id){
		
		Book book = bookService.findBookById(id);
		return new ResponseEntity<Book>(book,HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/new-book", method = RequestMethod.POST)
	public ResponseEntity<?> saveNewBook(
							 @RequestParam(required = false)String title,
							 @RequestParam(required = false) String description,
							 @RequestParam(required = false) Long authorId,
							 @RequestParam(required = false) List<Long> genres,
							 @RequestParam(required = false) MultipartFile file,
							 HttpServletRequest request)
	{
		Book book =  bookService.createBook(title, description, "",authorId,genres);
        if (file!=null && !file.isEmpty()) {
            try {
                // Creating the PATH to directory to store file
               String uploadsDir = "/uploads/";
               String realPathtoUploads =  request.getServletContext().getRealPath(uploadsDir);
              
               //Creating the DIRECTORY from PATH and checking if it exists
               File directory = new File(realPathtoUploads);
               if(! directory.exists())
               {
            	   directory.mkdir();
               }
               
               //extractig the extension and adding user-id as name;
               String originalName = file.getOriginalFilename();
               String extension = originalName.substring(originalName.lastIndexOf("."), 
               											originalName.length()); 	
               String name = "book-"+book.getId()+extension;
               
               //creating full path of the file we have to save
               String filePath = realPathtoUploads + name;
                            
               //trasnfering the file to the destination (saving)
               File dest = new File(filePath);	
               file.transferTo(dest);
               
               book.setImage(name);
               book = bookService.saveOrUpdateBook(book);
               
               return new ResponseEntity<Book>(book,HttpStatus.OK);
            }
            catch (Exception e) {
                 return new ResponseEntity<String>("You FAILED to save book "+e.getMessage(),
                		 							HttpStatus.OK);
            }
        }	        
        else {
        	return new ResponseEntity<Book>(book,HttpStatus.OK); 
        }
	 }
	
	@RequestMapping(value = "/get-image/{bookId}")
	 public byte[] getImageUser(@PathVariable Long bookId, HttpServletRequest request)  {
		 
		//get image name from database
		String image = bookService.findImageById(bookId);
		
	    //get real path ja dava lokacijata od proektot...dodavame uploads za da stigneme do slikite
		String rpath = request.getServletContext().getRealPath("/uploads/"+image);
		 
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
	
	@RequestMapping(value = "/by-author/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getBooksByAuthor(@PathVariable Long id){
		
		List<Book> books = bookService.findAllBooksByAuthor(id);
		return new ResponseEntity<List<Book>>(books,HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/by-genre", method = RequestMethod.POST)
	public ResponseEntity<?> getBooksByGenre(@RequestBody helperClass HC){
		printJson(HC);
		List<Book> books = bookService.findAllBooksByGenres(HC.getGenreIds());
		return new ResponseEntity<List<Book>>(books,HttpStatus.OK);
		
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
