package mk.finki.wp.service;

import java.util.List;


import mk.finki.wp.model.Book;
import mk.finki.wp.model.BookComment;
import mk.finki.wp.model.Comment;
import mk.finki.wp.model.User;

public interface BookCommentService {
	
	public BookComment saveOrUpdateComment(BookComment entity);
	public BookComment findCommentById(Long id);
	public List<BookComment> findAllComments();
	public Integer deleteComment(Long commentId);
	public BookComment UpdateComment(String text,BookComment comment);
	public BookComment createComment(User userFrom,Book book,String text);
	public BookComment createComment(Long userFrom,Long book, String text);
	List<BookComment> findAllCommentsOfBook(Long userId);

}
