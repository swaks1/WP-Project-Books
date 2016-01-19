package mk.finki.wp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mk.finki.wp.model.Genre;
import mk.finki.wp.model.User;
import mk.finki.wp.persistance.GenreRepository;
import mk.finki.wp.persistance.UserRepository;
import mk.finki.wp.service.GenreService;


@Service
public class GenreServiceImpl implements GenreService {
	
	@Autowired
	GenreRepository genreRepo;

	@Override
	public List<Genre> findAllGenres() {
		return genreRepo.findAll();
	}

	@Override
	public Genre saveOrUpdateGenre(Genre genre) {
		return genreRepo.saveOrUpdate(genre);
		
	}

	@Override
	public Genre findByName(String genreName) {
		List<Genre> lista= genreRepo.findByName(genreName);
		
		if(lista.size()!=0)
		{
			return lista.get(0);
		}
		
		return null;
	}

	@Override
	public Genre findById(Long id) {
		return genreRepo.findByID(Genre.class, id);
	}

	@Override
	public Genre createGenre(String genreName) {
		Genre genre = new Genre();
		genre.setGenreName(genreName);
		return genre;
	}

	@Override
	public List<Genre> findAllGenresOfUser(User user) {
		return user.getGenres();
	}



}
