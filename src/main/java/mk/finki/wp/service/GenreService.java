package mk.finki.wp.service;

import java.util.List;

import mk.finki.wp.model.Genre;


public interface GenreService {
	
	public List<Genre> findAllGenres();
	public Genre saveOrUpdateGenre(String genreName);
	public Genre findByName(String genreName);
	
}