package mk.finki.wp.service;

import java.util.List;


import mk.finki.wp.model.Book;
import mk.finki.wp.model.Comment;
import mk.finki.wp.model.User;

public interface CommentService {
	
	public Comment saveOrUpdateComment(Comment entity);
	public Comment findCommentById(Long id);
	public List<Comment> findAllComments();
	public Integer deleteComment(Long commentId);
	public Comment UpdateComment(String text,Comment comment);
	public Comment createComment(User userFrom,User userTo,String text);
	public Comment createComment(Long userFrom,Long userTo, String text);
	List<Comment> findAllCommentsOfUser(Long userId);

}
