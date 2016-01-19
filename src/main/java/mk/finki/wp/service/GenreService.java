package mk.finki.wp.service;

import java.util.List;

import mk.finki.wp.model.Genre;
import mk.finki.wp.model.User;


public interface GenreService {
	
	public List<Genre> findAllGenres();
	public Genre saveOrUpdateGenre(Genre genre);
	public Genre findByName(String genreName);
	public Genre findById(Long id);
	public Genre createGenre(String genreName);
	public List<Genre> findAllGenresOfUser(User user);
	
	
}