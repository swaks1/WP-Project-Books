package mk.finki.wp.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mk.finki.wp.model.Author;
import mk.finki.wp.model.Book;
import mk.finki.wp.model.Genre;
import mk.finki.wp.persistance.AuthorRepository;
import mk.finki.wp.persistance.BookRepository;
import mk.finki.wp.service.BookService;

@Service
public class BookServiceImpl implements BookService{
	@Autowired
	BookRepository bookRepo;
	
	@Autowired
	AuthorServiceImpl authorService;
	
	@Autowired
	GenreServiceImpl genreService;

	@Override
	public Book saveOrUpdateBook(Book entity) {
		return bookRepo.saveOrUpdateBook(entity);
	}

	@Override
	public Book createBook(String title,String description,String image,
			Long authorId, List<Long>genres) {
		Book book = new Book();
		book.setTitle(title);
		book.setDescription(description);
		book.setImage(image);
		book = saveOrUpdateBook(book);
		
		if(authorId != null){
			Author author = authorService.findAuthorById(authorId);
			book.setAuthor(author);
		}
		
		if(genres != null){
			book.setGenres(new ArrayList<Genre>());
			for(Long id : genres){
				Genre g = genreService.findById(id);
				book.addGenre(g);		
			}
		}
		return saveOrUpdateBook(book);
	}

	@Override
	public Book findBookById(Long id) {
		return bookRepo.findBookById(id);
	}

	@Override
	public List<Book> findAllBooks() {
		return bookRepo.findAllBooks();
	}

	@Override
	public List<Book> findAllBooksByAuthor(Long authorId) {
		return bookRepo.findAllBooksByAuthor(authorId);
	}

	@Override
	public Book setAuthor(Book book, Author author) {
		book.setAuthor(author);
		return bookRepo.saveOrUpdateBook(book);
	}

	@Override
	public Book addGenre(Book book, Genre genre){
		if(book.getGenres() == null){
			book.setGenres(new ArrayList<Genre>());
		}
		book.addGenre(genre);
		book=saveOrUpdateBook(book);
		return book;
	}

	@Override
	public List<Book> findAllBooksByGenre(Genre genre) {
		List<Genre> genres = new ArrayList<Genre>();
		genres.add(genre);
		return bookRepo.findAllBooksByGenres(genres);
	}

	@Override
	public List<Book> findAllBooksByGenres(List<Long> genreIds) {
		List<Genre> genresArray = new ArrayList<Genre>();
		if(genreIds != null){
			for(Long id : genreIds){
				Genre g = genreService.findById(id);
				genresArray.add(g);
				
			}
			List<Book> books = bookRepo.findAllBooksByGenres(genresArray);
			return books;
		}
		return null;
	}
	
}
