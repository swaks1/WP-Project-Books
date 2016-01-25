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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import mk.finki.wp.model.Author;

import mk.finki.wp.service.AuthorService;


@RestController
@RequestMapping(value = "/api/authors" ,produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthorController {
	
	
	@Autowired
	AuthorService authorService;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<?> getAllAuthors(){
		
		List<Author> allAuthors = authorService.findAllAuthors();
		return new ResponseEntity<List<Author>>(allAuthors,HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getAuthorById(@PathVariable Long id){
		
		Author author = authorService.findAuthorById(id);
		return new ResponseEntity<Author>(author,HttpStatus.OK);
		
	}

	@RequestMapping(value = "/new-author", method = RequestMethod.POST)
	public ResponseEntity<?> saveNewAuthor(
							 @RequestParam(required = false)String name,
							 @RequestParam(required = false) String surname,
							 @RequestParam(required = false) String biography,
							 @RequestParam(required = false) MultipartFile file,
							 HttpServletRequest request)
	{
		Author author =  authorService.createAuthor(surname, surname, "", biography);
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
               String imageName = "author-"+author.getId()+extension;
               
               //creating full path of the file we have to save
               String filePath = realPathtoUploads + imageName;
                            
               //trasnfering the file to the destination (saving)
               File dest = new File(filePath);	
               file.transferTo(dest);
               
               author.setImage(imageName);
               author = authorService.saveOrUpdateAuthor(author);
               
               return new ResponseEntity<Author>(author,HttpStatus.OK);
            }
            catch (Exception e) {
                 return new ResponseEntity<String>("You FAILED to save Author "+e.getMessage(),
                		 							HttpStatus.OK);
            }
        }	        
        else {
        	return new ResponseEntity<Author>(author,HttpStatus.OK); 
        }
	 }
	
	
	@RequestMapping(value = "/get-image/{authorId}")
	 public byte[] getImageUser(@PathVariable Long authorId, HttpServletRequest request)  {
		 
		//get image name from database
		String image = authorService.findImageById(authorId);
		
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
}
