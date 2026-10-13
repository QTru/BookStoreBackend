package iuh.fit.bookstorebackend.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
@Table(name = "order_items")
@IdClass(OrderItem.OrderItemId.class)

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"book", "order"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class OrderItem {
    @Column(name = "quantity", nullable = false)
    private int quantity;
    @Column(name = "unit_price", nullable = false, precision = 19, scale = 0)
    private BigDecimal unitPrice;
    @Column(name = "discount_percentage", nullable = false, precision = 19, scale = 2)
    private BigDecimal discountPercentage;
    @Setter(AccessLevel.NONE)
    @Column(name = "amount", nullable = false, precision = 19, scale = 0)
    private BigDecimal amount;
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @EqualsAndHashCode.Include
    @JoinColumn(name = "book_id", nullable = false)
    @JsonIgnoreProperties({"orderItems", "items"})
    private Book book;
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @EqualsAndHashCode.Include
    @JoinColumn(name = "order_id", nullable = false)
    @JsonIgnoreProperties({"items", "orders"})
    private Order order;

    void calculateAmount() {
        this.amount = unitPrice
                .multiply(BigDecimal.valueOf(quantity))
                .setScale(0, RoundingMode.HALF_UP);
    }

    @Embeddable

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class OrderItemId implements Serializable {
        private String book;
        private String order;
    }
}