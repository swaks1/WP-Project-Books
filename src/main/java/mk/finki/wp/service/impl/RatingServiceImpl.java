package mk.finki.wp.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mk.finki.wp.model.Book;
import mk.finki.wp.model.Rating;
import mk.finki.wp.model.User;
import mk.finki.wp.persistance.RatingRepository;
import mk.finki.wp.service.RatingService;

@Service
public class RatingServiceImpl implements RatingService{
	
	@Autowired
	RatingRepository rateRepo;
	
	@Autowired
	BookServiceImpl bookService;
	
	@Autowired
	UserServiceImpl userService;
	

	@Override
	public Rating saveOrUpdateComment(Rating entity) {
		return rateRepo.saveOrUpdateRating(entity);
	}

	@Override
	public Integer deleteRating(Long ratingId) {
		return rateRepo.deleteRating(ratingId);
	}
	
	@Override
	public Rating CreateRating(int stars, Long bookID, Long UserId) {
		Rating rating = new Rating();
		rating.setStars(stars);
		rating.setOnBook(bookService.findBookById(bookID));
		rating.setUser(userService.findUserById(UserId));
		rating.setDateCreated(new Date());
		return rateRepo.saveOrUpdateRating(rating);
	}

	@Override
	public List<Rating> findAllRatingsOfBook(Long bookId) {
		return rateRepo.findAllRatingsOfBook(bookId);
	}
	
	@Override
	public double findAverageRatingOnBook(Long bookId) {
		List<Rating> allstars = rateRepo.findAllRatingsOfBook(bookId);
		double average = 0;
		for(Rating r : allstars){
			average += r.getStars();
		}
		if(!allstars.isEmpty())
			average = average / allstars.size();
		
		return average;
	}

	@Override
	public int getTotalUserRatings(Long bookID) {
		List<Rating> byBook = rateRepo.findAllRatingsOfBook(bookID);
		
		return byBook.size();
	}

	@Override
	public int getStateOfRate(Long userId, Long bookId) {
		List<Rating> userOnBook = rateRepo.findRatingOnBookByUser(bookId, userId);
		if(!userOnBook.isEmpty())
			return userOnBook.get(0).getStars();
		return 0;
	}
	
	@Override
	public int UpdateRating(int stars, Long bookId, Long userId) {
		Rating rating;
		List<Rating> userOnBook = rateRepo.findRatingOnBookByUser(bookId, userId);
		if(!userOnBook.isEmpty()){
			rating = userOnBook.get(0);
			rating.setStars(stars);
			rateRepo.saveOrUpdateRating(rating);
		}
		else{
			CreateRating(stars, bookId, userId);
		}
		return stars;
	}


}
