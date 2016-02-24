package mk.finki.wp.web;

import java.util.List;


public class helperClass {
	// for favGenres, list books by genre
	private List<Long> genreIds;
	private Long userId;
	
	//for ratings
	private double averageRate;
	private int totalUserRatings;

	
	public double getAverageRate() {
		return averageRate;
	}

	public void setAverageRate(double averageRate) {
		this.averageRate = averageRate;
	}

	public int getTotalUserRatings() {
		return totalUserRatings;
	}

	public void setTotalUserRatings(int totalUserRatings) {
		this.totalUserRatings = totalUserRatings;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public List<Long> getGenreIds() {
		return genreIds;
	}

	public void setGenreIds(List<Long> genresList) {
		this.genreIds = genresList;
	}
	
	

}
