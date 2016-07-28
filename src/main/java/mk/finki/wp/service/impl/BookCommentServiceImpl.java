package mk.finki.wp.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mk.finki.wp.model.Book;
import mk.finki.wp.model.BookComment;
import mk.finki.wp.model.Comment;
import mk.finki.wp.model.User;
import mk.finki.wp.persistance.BookCommentRepository;
import mk.finki.wp.persistance.CommentRepository;
import mk.finki.wp.service.BookCommentService;
import mk.finki.wp.service.CommentService;

@Service
public class BookCommentServiceImpl implements BookCommentService{
	
	@Autowired
	BookCommentRepository commentRepo;
	
	@Autowired
	UserServiceImpl userService;
	
	@Autowired
	BookServiceImpl bookService;

	@Override
	public BookComment saveOrUpdateComment(BookComment entity) {
		return commentRepo.saveOrUpdateComment(entity);
	}

	@Override
	public BookComment findCommentById(Long id) {
		return commentRepo.findCommentById(id);
	}

	@Override
	public List<BookComment> findAllComments() {
		return commentRepo.findAllComments();
	}


	@Override
	public Integer deleteComment(Long commentId) {
		return commentRepo.deleteComment(commentId);
	}

	@Override
	public BookComment UpdateComment(String text, BookComment comment) {
			comment.setComment(text);
			comment.setDateCreated(new Date());	
			return saveOrUpdateComment(comment);
			
	}

	@Override
	public BookComment createComment(User userFrom, Book book, String text) {
		BookComment comment = new BookComment();
		comment.setUserFrom(userFrom);
		comment.setBook(book);
		comment.setComment(text);
		comment.setDateCreated(new Date());
		return saveOrUpdateComment(comment);
	}

	@Override
	public List<BookComment> findAllCommentsOfBook(Long bookId) {
		return commentRepo.findAllCommentsOfBook(bookId);
	}

	@Override
	public BookComment createComment(Long userFrom, Long bookId, String text) {
		User userF = userService.findUserById(userFrom);
		Book book = bookService.findBookById(bookId);
		return createComment(userF, book, text);
	}

}
