package mk.finki.wp.persistance;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mk.finki.wp.model.Genre;

@Repository
public class GenreRepository {
	
	@PersistenceContext
	EntityManager em;
	
	@Transactional
	  public Genre saveOrUpdate(Genre entity) {
		 
	    if (entity.getId() != null && !em.contains(entity)) {
	      entity = em.merge(entity);
	    } else {
	      em.persist(entity);
	    }
	    em.flush();
	    return entity;
	  }
	
	public <T> T findByID(Class<T> type, Long id) {
	    CriteriaBuilder cb = em.getCriteriaBuilder();
	    CriteriaQuery<T> cq = cb.createQuery(type);
	    final Root<T> root = cq.from(type);

	    Predicate byId = cb.equal(root.get("id"), id);

	    cq.where(byId);

	    TypedQuery<T> query = em.createQuery(cq);

	    return query.getSingleResult();
	  }
	
	@Transactional
	public <T> int delete(Class <T> type, long id){
		CriteriaBuilder cb = em.getCriteriaBuilder();
	    CriteriaDelete<T> cd = cb.createCriteriaDelete(type);
	    final Root<T> root = cd.from(type);

	    Predicate byId = cb.equal(root.get("id"), id);
	    cd.where(byId);
	    int changes = em.createQuery(cd).executeUpdate();
	    em.flush();
	    return changes;
	}
	
	public List<Genre> findAll() {
		
		 CriteriaBuilder cb = em.getCriteriaBuilder();		 
		  CriteriaQuery<Genre> cq = cb.createQuery(Genre.class);
		  Root<Genre> root = cq.from(Genre.class);
		  cq.select(root);
		  
		  TypedQuery<Genre> query = em.createQuery(cq);
		  List<Genre> results = query.getResultList();
		  return results;
		  
	}
	
	public List<Genre> findByName(String type) {
	    CriteriaBuilder cb = em.getCriteriaBuilder();
	    CriteriaQuery<Genre> cq = cb.createQuery(Genre.class);
	    final Root<Genre> root = cq.from(Genre.class);

	      Predicate byName=cb.equal(root.get("genreName"), type);
	      cq.where(byName);

	    TypedQuery<Genre> query = em.createQuery(cq);

	    return query.getResultList();
	  }

}
