package mk.finki.wp.web;

import java.io.File;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import mk.finki.wp.model.User;

import mk.finki.wp.service.UserService;
	
	//moze da se koristi za CORS  (1.5 hours wasted for nothing )
 //@CrossOrigin(origins = "http://localhost:8000", maxAge = 3600)
@RestController
@RequestMapping(value = "/api/users" ,produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
	
	@Autowired
	UserService userService; 
	

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<User> getUserById(@PathVariable Long id){
		
		User user =  userService.findUserById(id);
		return new ResponseEntity<User>(user,HttpStatus.OK);
		
		}
	
	@RequestMapping(value = "/get-session", method = RequestMethod.GET)
	public ResponseEntity<User> getSession(HttpServletRequest request ){
		
		HttpSession session = request.getSession();
		User user =	(User)session.getAttribute("user");
		
		return new ResponseEntity<User>(user,HttpStatus.OK);
		
		}
	
	@RequestMapping(value = "/abandon-session", method = RequestMethod.GET)
	public ResponseEntity<?> abandonSession(HttpServletRequest request ){
		
		HttpSession session = request.getSession();
		session.invalidate();
		
		return new ResponseEntity<String>("Abandoned",HttpStatus.OK);
		
		}
	
	@RequestMapping(value = "/all-usernames", method = RequestMethod.GET)
	public ResponseEntity<?> findAllUsernames( ){
		
		List<String> usernames = userService.findAllUsernames();
		
		return new ResponseEntity<List<String>>(usernames,HttpStatus.OK);
		
		}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ResponseEntity<User> updateUser(@RequestBody User user){
		
		User updatedUser = userService.updateUser(user);
		return new ResponseEntity<User>(updatedUser,HttpStatus.OK);
		
		}
	
	@RequestMapping(value = "/change-password", method = RequestMethod.POST)
	public ResponseEntity<?> changePassword(@RequestParam Long id,
										@RequestParam String oldPassword,
										@RequestParam String newPassword){
		
		User user = userService.findUserById(id);
		if(oldPassword.equals(user.getPassword())){
			user.setPassword(newPassword);
			user = userService.saveOrUpdateUser(user);
			return new ResponseEntity<User>(user,HttpStatus.OK);
		}
		else
			return new ResponseEntity<Object>(null,HttpStatus.OK);
		
		}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<?> loginUser(
							@RequestParam(required = false) String username,
							@RequestParam(required = false) String password,
							HttpServletRequest request){
		
		if(!username.trim().isEmpty() && !password.trim().isEmpty())
		{
			if(userService.checkUser(username, password))
			{				 
				 User user = userService.findUsersByUsername(username);
				 HttpSession session = request.getSession();
				 session.setAttribute("user", user);
				 return new ResponseEntity<User>(user,HttpStatus.OK);
			}
			else
				return new ResponseEntity<Object>(null,HttpStatus.OK);

		}
	else
		return new ResponseEntity<Object>(null,HttpStatus.OK);
	}

	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	 public ResponseEntity<?> registerUser(
			 		 	 @RequestParam(required = false) String fname,
						 @RequestParam(required = false) String lname,
						 @RequestParam(required = false) String username,
						 @RequestParam(required = false) String password,
						 @RequestParam(required = false) String biography,
						 @RequestParam(required = false) MultipartFile file,
						 HttpServletRequest request) 
	 {
		User user = new User();
		user =  userService.createUser(fname, lname, username, password, biography, "");
       
		if (file != null && !file.isEmpty()) {
			
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
               String name = "user-"+user.getId()+extension;
               
               //creating full path of the file we have to save
               String filePath = realPathtoUploads + name;
                            
               //trasnfering the file to the destination (saving)
               File dest = new File(filePath);	
               file.transferTo(dest);
               
               user.setImage(name);
               user = userService.saveOrUpdateUser(user);
               HttpSession session = request.getSession();
               session.setAttribute("user", user);
               printJson(user);
               return new ResponseEntity<User>(user,HttpStatus.OK);
            }
            catch (Exception e) {
                 return new ResponseEntity<String>("You FAILED to register "+e.getMessage(),
                		 							HttpStatus.OK);
            }
        }	        
        else {
        	return new ResponseEntity<User>(user,HttpStatus.OK); 
        }
	 }
	
	 @RequestMapping(value = "/get-image/{userID}")
	 public byte[] getImageUser(@PathVariable Long userID, HttpServletRequest request)  {
		 
		//get image name from database
		String image = userService.findImageById(userID);
		
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
	 
	 
	 @RequestMapping(value = "/update-profile/{userId}", method = RequestMethod.POST)
	 public ResponseEntity<?> updateUserProfile(
			 			@PathVariable  Long userId,
			 			@RequestParam(required = false) MultipartFile file,
						 HttpServletRequest request) 
	 {
		 
		User user = userService.findUserById(userId);
		
        if (file != null && !file.isEmpty()) {
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
               String name = "user-"+user.getId()+extension;
               
               //creating full path of the file we have to save
               String filePath = realPathtoUploads + name;
                            
               //trasnfering the file to the destination (saving)
               File dest = new File(filePath);	
               file.transferTo(dest);
        
               
               printJson(user);
               return new ResponseEntity<User>(user,HttpStatus.OK);
            }
            catch (Exception e) {
                 return new ResponseEntity<String>("You FAILED to register "+e.getMessage(),
                		 							HttpStatus.OK);
            }
        }	        
        else {
        	return new ResponseEntity<User>(user,HttpStatus.OK); 
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
