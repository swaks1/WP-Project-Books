package mk.finki.wp.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import mk.finki.wp.model.User;
import mk.finki.wp.persistance.UserRepository;
import mk.finki.wp.service.impl.UserServiceImpl;



@Controller
@RequestMapping("/")
public class TestController {
	
	@Autowired
	UserRepository	userRepo;
	
	@RequestMapping("/test")
	public ResponseEntity<Object> getObj(){
		User usr = new User();
		usr.setFname("Riste");
		usr.setLname("Poposki");
		userRepo.saveOrUpdate(usr);
		return new ResponseEntity<Object>(usr,HttpStatus.OK);
	}
	
	
	

}
