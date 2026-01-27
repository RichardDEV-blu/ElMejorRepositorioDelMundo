package domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="inventory")
@Getter
@Setter
@NoArgsConstructor
public class Inventory {

    @Id
    @Column(name = "book_isbn")
    private String bookIsbn;


    @OneToOne
    @MapsId
    @JoinColumn(name = "book_isbn")
    private Book book;

    private Integer sold;

    private Integer supplied;

    @Version
    private Integer version;



}
