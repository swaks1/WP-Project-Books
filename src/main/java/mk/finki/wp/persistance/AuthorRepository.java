package mk.finki.wp.persistance;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mk.finki.wp.model.Author;

@Repository
public class AuthorRepository {

	@PersistenceContext
	EntityManager em;
	
	@Transactional
	public Author saveOrUpdateAuthor(Author entity){
		if (entity.getId() != null && !em.contains(entity)) {
		      entity = em.merge(entity);
		    } else {
		      em.persist(entity);
		    }
		    em.flush();
		    return entity;	
	}

	public List<Author> findAllAuthors(){
		TypedQuery<Author> query = em.createQuery("SELECT a FROM Author a",Author.class);
		List <Author> results = query.getResultList();
		return results;
	}

	public Author findAuthorById(Long id){
		TypedQuery<Author> query = em.createQuery("SELECT a FROM Author a WHERE a.id=?1"
								,Author.class);
		query.setParameter(1, id);
		Author result = query.getSingleResult();
		return result;
	}

	public String findImageById(Long id) {
		TypedQuery<String> query = em.createQuery("SELECT a.image FROM Author a WHERE a.id=?1",String.class);
		query.setParameter(1, id);
		String result = query.getSingleResult();
		return result;
	}
}
