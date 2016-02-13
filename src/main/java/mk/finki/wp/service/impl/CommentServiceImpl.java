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
	
	@Autowired
	UserServiceImpl userService;

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
	public Integer deleteComment(Long commentId) {
		return commentRepo.deleteComment(commentId);
	}

	@Override
	public Comment UpdateComment(String text, Comment comment) {
			comment.setComment(text);
			comment.setDateCreated(new Date());	
			return saveOrUpdateComment(comment);
			
	}

	@Override
	public Comment createComment(User userFrom, User userTo, String text) {
		Comment comment = new Comment();
		comment.setUserFrom(userFrom);
		comment.setUserTo(userTo);
		comment.setComment(text);
		comment.setDateCreated(new Date());
		return saveOrUpdateComment(comment);
	}

	@Override
	public List<Comment> findAllCommentsOfUser(Long userId) {
		return commentRepo.findAllCommentsOfUser(userId);
	}

	@Override
	public Comment createComment(Long userFrom, Long userTo, String text) {
		User userF = userService.findUserById(userFrom);
		User userT = userService.findUserById(userTo);
		return createComment(userF, userT, text);
	}

}
