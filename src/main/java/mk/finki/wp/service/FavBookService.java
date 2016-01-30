package mk.finki.wp.service;

import java.util.List;
import mk.finki.wp.model.Book;
import mk.finki.wp.model.FavBook;
import mk.finki.wp.model.User;

public interface FavBookService {
	
	public FavBook saveOrUpdateFavBook(FavBook entity);
	public FavBook createFavBook(User user, Book book);
	public FavBook createFavBook(Long userId, Long bookId);
	public FavBook findFavBookById(Long id);		
	public List<FavBook> findAllFavBooks();	
	public List<Book> findAllBooksByUser(Long userId);
	public Boolean deleteFavBook(User user, Book book);
	public Boolean deleteFavBook(Long userId, Long bookId);
	public Boolean toggleFavBook(Long userId, Long bookId);
	public Boolean toggleStateFavBook(Long userId, Long bookId);
}
