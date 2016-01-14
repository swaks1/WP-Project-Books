package mk.finki.wp.persistance;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mk.finki.wp.model.User;


@Repository
public class UserRepository {
	
	@PersistenceContext
	EntityManager em;
	
	 @Transactional
	  public long saveOrUpdate(User entity) {
		 
	    if (entity.getId() != null && !em.contains(entity)) {
	      entity = em.merge(entity);
	    } else {
	      em.persist(entity);
	    }
	    em.flush();
	    return entity.getId();
	  }

}
