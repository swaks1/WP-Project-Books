package mk.finki.wp.web;

import java.util.List;


public class helperClass {
	private List<Long> genreIds;
	private Long userId;


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
