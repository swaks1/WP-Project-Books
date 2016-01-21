package mk.finki.wp.service;

import java.util.List;


import mk.finki.wp.model.Book;
import mk.finki.wp.model.Comment;
import mk.finki.wp.model.User;

public interface CommentService {
	
	public Comment saveOrUpdateComment(Comment entity);
	public Comment findCommentById(Long id);
	public List<Comment> findAllComments();
	public List<Comment> findAllCommentsByBook(Book book);
	public Integer deleteComment(Comment comment);
	public Comment UpdateComment(String text,Comment comment);
	public Comment createComment(User user,Book book,String text);

}
