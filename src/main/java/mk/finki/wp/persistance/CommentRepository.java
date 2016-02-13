package mk.finki.wp.persistance;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mk.finki.wp.model.Book;
import mk.finki.wp.model.Comment;
import mk.finki.wp.model.FavBook;
import mk.finki.wp.model.User;

@Repository
public class CommentRepository {

	@PersistenceContext
	EntityManager em;
	
	@Transactional
	public Comment saveOrUpdateComment(Comment entity){
		if (entity.getId() != null && !em.contains(entity)) {
		      entity = em.merge(entity);
		    } else {
		      em.persist(entity);
		    }
		    em.flush();
		    return entity;
		
	}
	
	public Comment findCommentById(Long id){
		TypedQuery<Comment> query = em.createQuery("SELECT c FROM Comment c WHERE c.id=?1"
								,Comment.class);
		query.setParameter(1, id);
		Comment result = query.getSingleResult();
		return result;
	}
	
	public List<Comment> findAllComments(){
		TypedQuery<Comment> query = em.createQuery("SELECT c FROM Comment c",Comment.class);
		List <Comment> results = query.getResultList();
		return results;
		
	}
	
	
	
	@Transactional
	public Integer deleteComment(Long commentId) {
		Query query = em.createQuery("DELETE FROM Comment c WHERE c.id=?1");
		query.setParameter(1,commentId);
		Integer num = query.executeUpdate();
		return num;
	}

	public List<Comment> findAllCommentsOfUser(Long userId) {
			TypedQuery<Comment> query = em.createQuery("SELECT c FROM Comment c WHERE c.userTo.id=?1"
									,Comment.class);
			query.setParameter(1, userId);
			List <Comment> results = query.getResultList();
			return results;
		
	}
	

	

	
}
