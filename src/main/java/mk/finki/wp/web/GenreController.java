package mk.finki.wp.web;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mk.finki.wp.model.Genre;

import mk.finki.wp.service.GenreService;


@RestController
@RequestMapping(value = "/api/genres" ,produces = MediaType.APPLICATION_JSON_VALUE)
public class GenreController {

	@Autowired
	GenreService genreService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<?> getAllGenres(){
		
		List<Genre> allGenres = genreService.findAllGenres();
		return new ResponseEntity<List<Genre>>(allGenres,HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getGenreById(@PathVariable Long id){
		
		Genre genre =  genreService.findById(id);
		return new ResponseEntity<Genre>(genre,HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/new-genre", method = RequestMethod.POST)
	public ResponseEntity<?> saveNewGenre(@RequestParam String genreName)
	{
		Genre genre = genreService.createGenre(genreName);
		return new ResponseEntity<Genre>(genre,HttpStatus.OK);
	 }

}
