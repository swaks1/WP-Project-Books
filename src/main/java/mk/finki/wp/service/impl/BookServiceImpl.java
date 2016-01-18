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

	@Override
	public Book saveOrUpdateBook(Book entity) {
		return bookRepo.saveOrUpdateBook(entity);
	}

	@Override
	public Book createBook(String title, String description, String image) {
		Book book = new Book();
		book.setTitle(title);
		book.setDescription(description);
		book.setImage(image);
		return book;
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
	public List<Book> findAllBooksByAuthor(Author author) {
		return bookRepo.findAllBooksByAuthor(author);
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
		return bookRepo.findAllBooksByGenre(genre);
	}
	
}
