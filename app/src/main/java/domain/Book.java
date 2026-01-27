package domain;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="book")
@Getter
@Setter
@NoArgsConstructor
public class Book {

@Id
private String isbn;

private BigDecimal price;

private String title;

@Version
private Integer version;


@ManyToMany 
@JoinTable(
name = "book_author",
joinColumns = @JoinColumn(name = "books_isbn"),
inverseJoinColumns = @JoinColumn(name = "authors_id")
)
private Set<Author> authors = new HashSet<>();


@OneToOne(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
private Inventory inventory;




}
