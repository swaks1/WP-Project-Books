package mk.finki.wp.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mk.finki.wp.model.Book;
import mk.finki.wp.model.Comment;
import mk.finki.wp.model.User;
import mk.finki.wp.persistance.CommentRepository;
import mk.finki.wp.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService{
	
	@Autowired
	CommentRepository commentRepo;

	@Override
	public Comment saveOrUpdateComment(Comment entity) {
		return commentRepo.saveOrUpdateComment(entity);
	}

	@Override
	public Comment findCommentById(Long id) {
		return commentRepo.findCommentById(id);
	}

	@Override
	public List<Comment> findAllComments() {
		return commentRepo.findAllComments();
	}

	@Override
	public List<Comment> findAllCommentsByBook(Book book) {
		return commentRepo.findAllCommentsByBook(book);
	}

	@Override
	public Integer deleteComment(Comment comment) {
		return commentRepo.deleteComment(comment);
	}

	@Override
	public Comment UpdateComment(String text, Comment comment) {
			comment.setComment(text);
			comment.setDateCreated(new Date());	
			return saveOrUpdateComment(comment);
			
	}

	@Override
	public Comment createComment(User user, Book book, String text) {
		Comment comment = new Comment();
		comment.setBook(book);
		comment.setUser(user);
		comment.setComment(text);
		comment.setDateCreated(new Date());
		return saveOrUpdateComment(comment);
	}

}
