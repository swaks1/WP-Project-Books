package mk.finki.wp.persistance;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mk.finki.wp.model.BookComment;
import mk.finki.wp.model.Comment;

@Repository
public class BookCommentRepository {

	@PersistenceContext
	EntityManager em;
	
	@Transactional
	public BookComment saveOrUpdateComment(BookComment entity){
		if (entity.getId() != null && !em.contains(entity)) {
		      entity = em.merge(entity);
		    } else {
		      em.persist(entity);
		    }
		    em.flush();
		    return entity;
		
	}
	
	public BookComment findCommentById(Long id){
		TypedQuery<BookComment> query = em.createQuery("SELECT c FROM BookComment c WHERE c.id=?1"
								,BookComment.class);
		query.setParameter(1, id);
		BookComment result = query.getSingleResult();
		return result;
	}
	
	public List<BookComment> findAllComments(){
		TypedQuery<BookComment> query = em.createQuery("SELECT c FROM BookComment c",BookComment.class);
		List <BookComment> results = query.getResultList();
		return results;
		
	}
	
	
	
	@Transactional
	public Integer deleteComment(Long commentId) {
		Query query = em.createQuery("DELETE FROM BookComment c WHERE c.id=?1");
		query.setParameter(1,commentId);
		Integer num = query.executeUpdate();
		return num;
	}

	public List<BookComment> findAllCommentsOfBook(Long userId) {
			TypedQuery<BookComment> query = em.createQuery("SELECT c FROM BookComment c WHERE c.book.id=?1"
									,BookComment.class);
			query.setParameter(1, userId);
			List <BookComment> results = query.getResultList();
			return results;
		
	}
	

	

	
}
