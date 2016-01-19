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
import mk.finki.wp.model.User;


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
	 
	 public <T> T getById(Class<T> type, Long id) {
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
		
		public List<User> findAll() {
			
			 CriteriaBuilder cb = em.getCriteriaBuilder();		 
			  CriteriaQuery<User> cq = cb.createQuery(User.class);
			  Root<User> root = cq.from(User.class);
			  cq.select(root);
			  
			  TypedQuery<User> query = em.createQuery(cq);
			  List<User> results = query.getResultList();
			  return results;
			  
		}
		
		public List<User> findByName(String fname) {
		    CriteriaBuilder cb = em.getCriteriaBuilder();
		    CriteriaQuery<User> cq = cb.createQuery(User.class);
		    final Root<User> root = cq.from(User.class);

		      Predicate byName=cb.equal(root.get("fname"), fname);
		      cq.where(byName);

		    TypedQuery<User> query = em.createQuery(cq);

		    return query.getResultList();
		  }

}
