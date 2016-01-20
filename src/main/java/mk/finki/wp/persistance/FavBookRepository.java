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
import mk.finki.wp.model.FavBook;
import mk.finki.wp.model.Genre;
import mk.finki.wp.model.User;

@Repository
public class FavBookRepository {
	
	@PersistenceContext
	EntityManager em;
	
	@Transactional
	public FavBook saveOrUpdateFavBook(FavBook entity){
		if (entity.getId() != null && !em.contains(entity)) {
		      entity = em.merge(entity);
		    } else {
		      em.persist(entity);
		    }
		    em.flush();
		    return entity;
		
	}
	
	public FavBook findFavBookById(Long id){
		TypedQuery<FavBook> query = em.createQuery("SELECT f FROM FavBook f WHERE f.id=?1"
								,FavBook.class);
		query.setParameter(1, id);
		FavBook result = query.getSingleResult();
		return result;
	}
	
	public List<FavBook> findAllFavBooks(){
		TypedQuery<FavBook> query = em.createQuery("SELECT f FROM FavBook f",FavBook.class);
		List <FavBook> results = query.getResultList();
		return results;
		
	}
	
	public List<Book> findAllBooksByUser(User user){
		TypedQuery<Book> query = em.createQuery("SELECT b.origin FROM FavBook b WHERE b.user.id=?1"
								,Book.class);
		query.setParameter(1, user.getId());
		List <Book> results = query.getResultList();
		return results;
	}
	
	@Transactional
	public Integer deleteFavBook(User user, Book book) {
		Query query = em.createQuery("DELETE FROM FavBook b WHERE b.origin.id=?1");
		query.setParameter(1, book.getId());
		Integer num = query.executeUpdate();
		return num;
	}
	

	
	
}
