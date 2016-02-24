package mk.finki.wp.service;

import java.util.List;

import mk.finki.wp.model.Rating;
import mk.finki.wp.model.User;

public interface RatingService {
	public Rating saveOrUpdateComment(Rating entity);
	public Integer deleteRating(Long ratingId);
	public int UpdateRating(int stars, Long bookId, Long userId);
	Rating CreateRating(int stars, Long bookId, Long UserId);
	List<Rating> findAllRatingsOfBook(Long bookId);
	double findAverageRatingOnBook(Long bookId);
	int getTotalUserRatings(Long bookID);
	int getStateOfRate(Long userId, Long bookId);

}
