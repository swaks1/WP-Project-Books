package mk.finki.wp.persistance;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mk.finki.wp.model.Book;
import mk.finki.wp.model.Rating;


@Repository
public class RatingRepository {

	@PersistenceContext
	EntityManager em;
	
	@Transactional
	public Rating saveOrUpdateRating(Rating entity){
		if (entity.getId() != null && !em.contains(entity)) {
		      entity = em.merge(entity);
		    } else {
		      em.persist(entity);
		    }
		    em.flush();
		    return entity;
		
	}
	
	@Transactional
	public Integer deleteRating(Long ratingId) {
		Query query = em.createQuery("DELETE FROM Rating r WHERE r.id=?1");
		query.setParameter(1,ratingId);
		Integer num = query.executeUpdate();
		return num;
	}

	public List<Rating> findAllRatingsOfBook(Long bookId) {
			TypedQuery<Rating> query = em.createQuery("SELECT r FROM Rating r WHERE r.onBook.id=?1"
									,Rating.class);
			query.setParameter(1, bookId);
			List <Rating> results = query.getResultList();
			return results;
		
	}
	
	public List<Rating> findRatingOnBookByUser(Long bookId, Long userId) {
		TypedQuery<Rating> query = em.createQuery("SELECT r FROM Rating r WHERE r.onBook.id=?1 "
				+ "AND r.user.id=?2", Rating.class);
		query.setParameter(1, bookId);
		query.setParameter(2, userId);
		List <Rating> results = query.getResultList();
		return results;
	
}
}
