package mk.finki.wp.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mk.finki.wp.model.Book;
import mk.finki.wp.model.BookComment;
import mk.finki.wp.model.Comment;
import mk.finki.wp.service.impl.BookCommentServiceImpl;
import mk.finki.wp.service.impl.CommentServiceImpl;


@RestController
@RequestMapping(value = "/api/book-comments" ,produces = MediaType.APPLICATION_JSON_VALUE)
public class BookCommentController {
	@Autowired
	BookCommentServiceImpl commentService;
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> findCommentById(@PathVariable Long id){
		
		BookComment comment = commentService.findCommentById(id);
		return new ResponseEntity<BookComment>(comment, HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/of-book/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> findCommentsOfBook(@PathVariable Long id){
		
		List <BookComment> comments = commentService.findAllCommentsOfBook(id);
		return new ResponseEntity<List<BookComment>>(comments, HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/add-comment", method = RequestMethod.POST)
	public ResponseEntity<?> addComment(@RequestParam Long userFrom,
			@RequestParam Long bookId, @RequestParam String text){
		
		BookComment comment = commentService.createComment(userFrom, bookId, text);
		return new ResponseEntity<BookComment>(comment, HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/delete-comment/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> addComment(@PathVariable Long id){
		
		Integer num = commentService.deleteComment(id);
		return new ResponseEntity<Integer>(num, HttpStatus.OK);
		
	}
}
