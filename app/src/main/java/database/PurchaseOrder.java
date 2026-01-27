package database;

import java.time.LocalDateTime;

import org.hibernate.annotations.ManyToAny;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name= "purchaseorder")
@Getter
@Setter
@NoArgsConstructor


public class PurchaseOrder {

    private Long id;

    @Column(name = "deliveredon")
    private LocalDateTime delivered_on;

    @Column(name = "placedon")
    private LocalDateTime placed_on;

    private Integer status;

    private Integer total;


    @ManyToAny(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    



}
