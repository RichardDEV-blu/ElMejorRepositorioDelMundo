package domain;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LineItemId implements Serializable {
    @Column(name = "idx")
    private Integer idx;
    @Column(name = "order_id")
    private Long orderId;

}
