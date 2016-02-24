package mk.finki.wp.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mk.finki.wp.service.impl.RatingServiceImpl;

@RestController
@RequestMapping(value = "/api/rate",produces = MediaType.APPLICATION_JSON_VALUE)
public class RatingController {
	
	@Autowired
	RatingServiceImpl ratingService;
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getRatingsForBook (@PathVariable Long id){
		helperClass hc = new helperClass();
		hc.setAverageRate(ratingService.findAverageRatingOnBook(id));
		hc.setTotalUserRatings(ratingService.getTotalUserRatings(id));
		return new ResponseEntity<helperClass>(hc, HttpStatus.OK);
	}
	
	@RequestMapping(value="/rate", method = RequestMethod.POST)
	public ResponseEntity<?> rateBook (@RequestParam Long bookId,
			@RequestParam Long userId, @RequestParam int stars){
		ratingService.UpdateRating(stars, bookId, userId);
		int rate = ratingService.getStateOfRate(userId, bookId); // useless 
		return new ResponseEntity<Integer>(rate, HttpStatus.OK);
	}
	
	@RequestMapping(value="/get-rate-state", method = RequestMethod.GET)
	public ResponseEntity<?> getState (@RequestParam Long bookId,
			@RequestParam Long userId){
		int rate = ratingService.getStateOfRate(userId, bookId);
		return new ResponseEntity<Integer>(rate, HttpStatus.OK);
	}

}
