package mk.finki.wp.persistance;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mk.finki.wp.model.Author;
import mk.finki.wp.model.Book;
import mk.finki.wp.model.User;
import mk.finki.wp.model.Genre;


@Repository
public class UserRepository {
	
	@PersistenceContext
	EntityManager em;
	
	 @Transactional
	  public User saveOrUpdate(User entity) {
		 
	    if (entity.getId() != null && !em.contains(entity)) {
	      entity = em.merge(entity);
	    } else {
	      em.persist(entity);
	    }
	    em.flush();
	    return entity;
	  }
	 	//za test
	 @Transactional
	  public Book saveOrUpdateBook(Book entity) {
		 
	    if (entity.getId() != null && !em.contains(entity)) {
	      entity = em.merge(entity);
	    } else {
	      em.persist(entity);
	    }
	    em.flush();
	    return entity;
	  }
	 	//za test
	 @Transactional
	  public Genre saveOrUpdateZanr(Genre entity) {
		 
	    if (entity.getId() != null && !em.contains(entity)) {
	      entity = em.merge(entity);
	    } else {
	      em.persist(entity);
	    }
	    em.flush();
	    return entity;
	  }
	 //za test
	 @Transactional
	  public Author saveOrUpdateAuthor(Author entity) {
		 
	    if (entity.getId() != null && !em.contains(entity)) {
	      entity = em.merge(entity);
	    } else {
	      em.persist(entity);
	    }
	    em.flush();
	    return entity;
	  }
	 	//za test
	 public Genre findGenreById(Long id){
			TypedQuery<Genre> query = em.createQuery("SELECT b FROM Genre b WHERE b.id=?1"
									,Genre.class);
			query.setParameter(1, id);
			Genre result = query.getSingleResult();
			return result;
		}
	 
	 

}
