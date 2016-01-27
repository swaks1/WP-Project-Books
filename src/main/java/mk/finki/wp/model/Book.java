package mk.finki.wp.model;

import java.util.List;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "books")
public class Book {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String title;
	@Column(length=10000)
	private String description;
	private String image;
	
	@ManyToOne()
	private Author author;

	@ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name="Book_Genre",
    		  joinColumns=@JoinColumn(name="book_id"),
    		  inverseJoinColumns=@JoinColumn(name="genre_id")) 
	
	private List<Genre> genres;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	public List<Genre> getGenres() {
		return genres;
	}

	public void setGenres(List<Genre> genres) {
		this.genres = genres;
	}
	public void addGenre(Genre genre){
		this.genres.add(genre);
	}

	public boolean hasGenre(String genreName) {
		if(genres != null){
			for(Genre genre : genres){
				if(genre.getGenreName().equals(genreName))
					return true;
			}
		}
		return false;
	}
	

}
