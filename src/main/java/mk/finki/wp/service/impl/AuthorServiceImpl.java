package mk.finki.wp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mk.finki.wp.model.Author;
import mk.finki.wp.model.Book;
import mk.finki.wp.persistance.AuthorRepository;
import mk.finki.wp.service.AuthorService;

@Service
public class AuthorServiceImpl implements AuthorService {

	@Autowired
	AuthorRepository authorRepo;
	
	@Override
	public Author saveOrUpdateAuthor(Author entity) {
		return authorRepo.saveOrUpdateAuthor(entity);
	}

	@Override
	public Author createAuthor(String name, String surname, String image, String biography) {
		Author author = new Author();
		author.setName(name);
		author.setSurname(surname);
		author.setImage(image);
		author.setBiography(biography);
		return saveOrUpdateAuthor(author);
	}

	@Override
	public Author findAuthorById(Long id) {
		return authorRepo.findAuthorById(id);
	}

	@Override
	public List<Author> findAllAuthors() {
		return authorRepo.findAllAuthors();
	}

	@Override
	public Author findAuthorOfBook(Book entity) {
		return entity.getAuthor();
	}

	@Override
	public String findImageById(Long authorId) {
		return authorRepo.findImageById(authorId);
	}

}
