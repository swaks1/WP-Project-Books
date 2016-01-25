package mk.finki.wp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mk.finki.wp.model.Book;
import mk.finki.wp.model.FavBook;
import mk.finki.wp.model.User;
import mk.finki.wp.persistance.BookRepository;
import mk.finki.wp.persistance.FavBookRepository;
import mk.finki.wp.persistance.UserRepository;
import mk.finki.wp.service.FavBookService;

@Service
public class FavBookServiceImpl implements FavBookService {

	@Autowired
	FavBookRepository favBookRepo;
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	BookRepository bookRepo;
	
	@Override
	public FavBook saveOrUpdateFavBook(FavBook entity) {
		return favBookRepo.saveOrUpdateFavBook(entity);
	}
	
	public Boolean hasBook(User user, Book book){
		List <Book> booksFromUser = findAllBooksByUser(user.getId());
		
		for(Book b : booksFromUser){
			if(b.getId()==book.getId())
				return true;
		}
		return false;
	}
	
	@Override
	public FavBook createFavBook(User user, Book book){
			if(hasBook(user, book) == false){
				FavBook favBook = new FavBook();
				favBook.setOrigin(book);
				favBook.setUser(user);
				saveOrUpdateFavBook(favBook);
				return favBook;
			}
			return null;
					
	}

	@Override
	public FavBook findFavBookById(Long id) {
		return favBookRepo.findFavBookById(id);
	}

	@Override
	public List<FavBook> findAllFavBooks() {
		return favBookRepo.findAllFavBooks();
	}

	@Override
	public List<Book> findAllBooksByUser(Long userId) {
		return favBookRepo.findAllBooksByUser(userId);
	}

	@Override
	public Boolean deleteFavBook(User user, Book book) {
		int num = favBookRepo.deleteFavBook(user, book);
		if(num > 0)
			return true;
		return false;
	}

	@Override
	public FavBook createFavBook(Long userId, Long bookId) {
		User user = userRepo.findById(User.class, userId);
		Book book = bookRepo.findBookById(bookId);
		return createFavBook(user, book);
	}

	@Override
	public Boolean deleteFavBook(Long userId, Long bookId) {
		User user = userRepo.findById(User.class, userId);
		Book book = bookRepo.findBookById(bookId);
		return deleteFavBook(user, book);
	}

}
