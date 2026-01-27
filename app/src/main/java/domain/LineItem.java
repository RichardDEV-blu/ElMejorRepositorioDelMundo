package domain;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="lineitem")
@Getter
@Setter
@NoArgsConstructor
public class LineItem {

    @EmbeddedId
    private LineItemId id;

    private Integer quantity;

    // FK normal, NO parte de la PK
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="book_isbn")
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("orderId") //El valor de order.id se copia automáticamente dentro de LineItemId.orderId
    @JoinColumn(name="order_id")
    private PurchaseOrder order;



}
