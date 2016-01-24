 package mk.finki.wp.service;

import java.util.List;

import mk.finki.wp.model.Author;
import mk.finki.wp.model.Book;

public interface AuthorService {
	public Author saveOrUpdateAuthor(Author entity);
	public Author createAuthor(String name,String surname,String image,String biography);
	public Author findAuthorById(Long id);
	public List<Author> findAllAuthors();
	public Author findAuthorOfBook(Book entity);

}
