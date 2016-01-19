package mk.finki.wp.persistance;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mk.finki.wp.model.Author;
import mk.finki.wp.model.Book;
import mk.finki.wp.model.Genre;

@Repository
public class BookRepository {
	
	@PersistenceContext
	EntityManager em;
	
	@Transactional
	public Book saveOrUpdateBook(Book entity){
		if (entity.getId() != null && !em.contains(entity)) {
		      entity = em.merge(entity);
		    } else {
		      em.persist(entity);
		    }
		    em.flush();
		    return entity;
		
	}
	public Book createBook(String title,String description,String image){
		Book book = new Book();
		book.setTitle(title);
		book.setDescription(description);
		book.setImage(image);
		return book;
		
	}

	public Book findBookById(Long id){
		TypedQuery<Book> query = em.createQuery("SELECT b FROM Book b WHERE b.id=?1"
								,Book.class);
		query.setParameter(1, id);
		Book result = query.getSingleResult();
		return result;
	}
	
	public List<Book> findAllBooks(){
		TypedQuery<Book> query = em.createQuery("SELECT b FROM Book b",Book.class);
		List <Book> results = query.getResultList();
		return results;
		
	}
	
	public List<Book> findAllBooksByAuthor(Author author){
		TypedQuery<Book> query = em.createQuery("SELECT b FROM Book b WHERE b.author = author"
								,Book.class);
		List <Book> results = query.getResultList();
		return results;
	}
	public List<Book> findAllBooksByGenre(Genre genre){
		List<Book> allBooks = findAllBooks();
		List<Book> results = new ArrayList<Book>();
		
		for(Book book : allBooks){
			if(book.getGenres()!=null && book.hasGenre(genre.getGenreName()))
				results.add(book);
		}
		return results;
	}
	
	
	
	
}
